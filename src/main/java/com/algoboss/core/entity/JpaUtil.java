/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author agnaldo
 */
//@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER)
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaUtil  implements Serializable {
    private static final long serialVersionUID = 1L;

    //private static final SessionFactory sessionFactory;
    private static EntityManager entityManager;
    //@PersistenceContext(unitName="ERPPU")
    private EntityManager em;
    static {
        try {
            // Create the SessionFactory from standard (hibernate.cfg.xml) 
            // config file.
            //sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Log the exception. 
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


    public static EntityManager getEntityManager() {
        if (entityManager == null || !entityManager.isOpen()) {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("ERPPU");
            entityManager = emf.createEntityManager();
        }
        return entityManager;
    }

    public static EntityTransaction getSession() {
        return getEntityManager().getTransaction();
    }

    public EntityManager getEm() {
        return em;
    }
    
}
