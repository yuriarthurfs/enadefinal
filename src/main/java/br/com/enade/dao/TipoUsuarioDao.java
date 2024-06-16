package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.com.enade.model.Tbtipousuario;

public class TipoUsuarioDao implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(TipoUsuarioDao.class);

    @Inject
    private EntityManager em;

    private DAO<Tbtipousuario> dao;

    @PostConstruct
    public void init() {
        this.dao = new DAO<>(em, Tbtipousuario.class);
        logger.debug("TipoUsuarioDao inicializado com EntityManager.");
    }

    /**
     * Busca um tipo de usuário pelo ID.
     * @param tipoUsuarioId O ID do tipo de usuário.
     * @return Um Optional contendo o tipo de usuário encontrado ou um Optional vazio se nenhum for encontrado.
     */
    public Optional<Tbtipousuario> buscarPorId(Long tipoUsuarioId) {
        logger.debug("Buscando TipoUsuario com ID: {}", tipoUsuarioId);
        Tbtipousuario tipoUsuario = this.dao.buscaPorId(tipoUsuarioId);
        return Optional.ofNullable(tipoUsuario);
    }

    /**
     * Lista todos os tipos de usuário.
     * @return Uma lista de todos os tipos de usuário.
     */
    public List<Tbtipousuario> listarTodos() {
        logger.debug("Listando todas as entidades TipoUsuario.");
        return this.dao.listaTodos();
    }
}
