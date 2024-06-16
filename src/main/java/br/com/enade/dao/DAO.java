package br.com.enade.dao;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

public class DAO<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private final Class<T> classe;
    private EntityManager em;

    public DAO(EntityManager em, Class<T> classe) {
        this.classe = classe;
        this.em = em;
    }

    public void adiciona(T t) {
        try {
            em.persist(t);
        } catch (Exception e) {
            // Log and handle exception
            throw new RuntimeException(e);
        }
    }

    public void remove(T t) {
        try {
            em.remove(em.merge(t));
        } catch (Exception e) {
            // Log and handle exception
            throw new RuntimeException(e);
        }
    }

    public void atualiza(T t) {
        try {
            em.merge(t);
        } catch (Exception e) {
            // Log and handle exception
            throw new RuntimeException(e);
        }
    }

    public List<T> listaTodos() {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        return em.createQuery(query).getResultList();
    }

    public T buscaPorId(Long id) {
        return em.find(classe, id);
    }

    public int contaTodos() {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        query.select(em.getCriteriaBuilder().count(query.from(classe)));
        return em.createQuery(query).getSingleResult().intValue();
    }

    public List<T> listaTodosPaginada(int firstResult, int maxResults) {
        CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(classe);
        query.select(query.from(classe));
        TypedQuery<T> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(firstResult);
        typedQuery.setMaxResults(maxResults);
        return typedQuery.getResultList();
    }
}
