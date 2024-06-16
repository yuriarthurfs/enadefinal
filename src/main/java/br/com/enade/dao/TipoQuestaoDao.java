package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.enade.model.Tbtipoquestao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipoQuestaoDao implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(TipoQuestaoDao.class);

    @Inject
    private EntityManager em;

    private DAO<Tbtipoquestao> dao;

    @PostConstruct
    public void init() {
        this.dao = new DAO<>(em, Tbtipoquestao.class);
    }

    /**
     * List all question types.
     * @return a list of all question types.
     */
    public List<Tbtipoquestao> listarTodos() {
        return this.dao.listaTodos();
    }

    /**
     * Find a question type by its ID.
     * @param tipoQuestaoId the ID of the question type.
     * @return an Optional containing the found question type or an empty Optional if none found.
     */
    public Optional<Tbtipoquestao> buscarPorId(Long tipoQuestaoId) {
        Tbtipoquestao tipoQuestao = this.dao.buscaPorId(tipoQuestaoId);
        return Optional.ofNullable(tipoQuestao);
    }
}
