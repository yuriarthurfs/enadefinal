package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.enade.model.Tbtipousuario;
import br.com.enade.model.Tbusuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(UsuarioDao.class);

	@Inject
	EntityManager em;

	private DAO<Tbusuario> dao;

	@PostConstruct
	void init() {
		this.dao = new DAO<Tbusuario>(this.em, Tbusuario.class);
	}

	public Optional<Tbusuario> buscarPorId(Long usuarioId) {
		return Optional.ofNullable(this.dao.buscaPorId(usuarioId));
	}

	public void adiciona(Tbusuario usuario) {
		this.dao.adiciona(usuario);
	}

	public void atualiza(Tbusuario usuario) {
		this.dao.atualiza(usuario);
	}

	public void remove(Tbusuario usuario) {
		this.dao.remove(usuario);
	}

	public List<Tbusuario> listaTodos() {
		return this.dao.listaTodos();
	}
	
	public Tbtipousuario buscarTipo() {
		return em.find(Tbtipousuario.class, 2L);
	}

	public boolean existe(Tbusuario usuario) {
		Long count = em.createQuery(
				"SELECT COUNT(u) FROM Tbusuario u WHERE u.emailUsuario = :pEmail AND u.senhaUsuario = :pSenha",
				Long.class)
			.setParameter("pEmail", usuario.getEmailUsuario())
			.setParameter("pSenha", usuario.getSenhaUsuario())
			.getSingleResult();
		return count > 0;
	}

	public Optional<Tbusuario> recuperarUsuario(Tbusuario usuario) {
		try {
			Tbusuario result = em.createQuery(
					"SELECT u FROM Tbusuario u WHERE u.emailUsuario = :pEmail AND u.senhaUsuario = :pSenha",
					Tbusuario.class)
				.setParameter("pEmail", usuario.getEmailUsuario())
				.setParameter("pSenha", usuario.getSenhaUsuario())
				.getSingleResult();
			return Optional.of(result);
		} catch (NoResultException e) {
			logger.error("Usuário não encontrado: {}", e.getMessage());
			return Optional.empty();
		}
	}
}
