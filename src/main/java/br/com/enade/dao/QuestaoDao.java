package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import br.com.enade.model.Tbquestao;

/**
 * DAO para operações de banco de dados relacionadas a questões.
 */
public class QuestaoDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EntityManager em;

    private DAO<Tbquestao> dao;

    @PostConstruct
    private void init() {
        this.dao = new DAO<>(em, Tbquestao.class);
    }

    /**
     * Busca uma questão pelo ID.
     * @param questaoId ID da questão a ser buscada.
     * @return A questão encontrada ou null se não existir.
     */
    public Tbquestao buscarPorId(Long questaoId) {
        return dao.buscaPorId(questaoId);
    }

    /**
     * Adiciona uma nova questão ao banco de dados.
     * @param questao A questão a ser adicionada.
     */
    public void adiciona(Tbquestao questao) {
        dao.adiciona(questao);
    }

    /**
     * Remove uma questão do banco de dados.
     * @param questao A questão a ser removida.
     */
    public void remove(Tbquestao questao) {
        dao.remove(questao);
    }

    /**
     * Lista todas as questões disponíveis no banco de dados.
     * @return Uma lista de questões.
     */
    public List<Tbquestao> listarTodos() {
        return dao.listaTodos();
    }

    /**
     * Atualiza uma questão existente no banco de dados.
     * @param questao A questão a ser atualizada.
     */
    public void atualiza(Tbquestao questao) {
        dao.atualiza(questao);
    }
}
