package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Join;

import br.com.enade.model.Tbprova;
import br.com.enade.model.Tbquestao;

public class ProvaDao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    EntityManager em;

    private DAO<Tbprova> dao;

    @PostConstruct
    void init() {
        this.dao = new DAO<>(this.em, Tbprova.class);
    }

    public Tbprova buscarPorId(Long provaId) {
        return this.dao.buscaPorId(provaId);
    }

    public void adicionar(Tbprova prova) {
        this.dao.adiciona(prova);
    }

    public void atualizar(Tbprova prova) {
        this.dao.atualiza(prova);
    }

    public void remover(Tbprova prova) {
        this.dao.remove(prova);
    }

    public List<Tbprova> listarTodos() {
        return this.dao.listaTodos();
    }

    public List<Tbprova> listarProvasComQuestoes() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tbprova> cq = cb.createQuery(Tbprova.class);
        Root<Tbprova> prova = cq.from(Tbprova.class);
        Join<Tbprova, Tbquestao> questoes = prova.join("questoes"); // Assumindo que 'questoes' é uma coleção em Tbprova
        cq.select(prova).distinct(true);

        TypedQuery<Tbprova> query = em.createQuery(cq);
        return query.getResultList();
    }
}
