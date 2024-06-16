package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.enade.model.Tbresultado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResultadoDao implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(ResultadoDao.class);

    @Inject
    private EntityManager em;

    private DAO<Tbresultado> dao;

    @PostConstruct
    public void init() {
        this.dao = new DAO<>(em, Tbresultado.class);
    }

    public Tbresultado buscarPorId(Long resultadoId) {
        logger.debug("Buscando resultado com ID: {}", resultadoId);
        return dao.buscaPorId(resultadoId);
    }

    public void adiciona(Tbresultado resultado) {
        if (resultado != null) {
            logger.debug("Adicionando resultado: {}", resultado);
            dao.adiciona(resultado);
        } else {
            logger.error("Tentativa de adicionar um resultado nulo.");
            throw new IllegalArgumentException("Resultado não pode ser nulo.");
        }
    }

    public void remove(Tbresultado resultado) {
        if (resultado != null) {
            logger.debug("Removendo resultado: {}", resultado);
            dao.remove(resultado);
        } else {
            logger.error("Tentativa de remover um resultado nulo.");
            throw new IllegalArgumentException("Resultado não pode ser nulo.");
        }
    }

    public List<Tbresultado> listarTodos() {
        logger.debug("Listando todos os resultados.");
        return dao.listaTodos();
    }

    public void atualiza(Tbresultado resultado) {
        if (resultado != null) {
            logger.debug("Atualizando resultado: {}", resultado);
            dao.atualiza(resultado);
        } else {
            logger.error("Tentativa de atualizar um resultado nulo.");
            throw new IllegalArgumentException("Resultado não pode ser nulo.");
        }
    }
}
