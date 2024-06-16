package br.com.enade.dao;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Utility class to handle EntityManager lifecycle using CDI.
 */
@ApplicationScoped
public class JPAUtil {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("EnadePU");

    /**
     * Produces a request-scoped EntityManager.
     * @return a new EntityManager instance for each request.
     */
    @Produces
    @RequestScoped
    public EntityManager getEntityManager() {
        try {
            return emf.createEntityManager();
        } catch (Exception e) {
            // Handle and log exception
            throw new RuntimeException("Error creating EntityManager.", e);
        }
    }

    /**
     * Closes the provided EntityManager.
     * @param em the EntityManager to close.
     */
    public void close(@Disposes EntityManager em) {
        if (em.isOpen()) {
            em.close();
        }
    }

    /**
     * Cleans up the EntityManagerFactory when the application is undeployed.
     */
    @PreDestroy
    public void closeFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
