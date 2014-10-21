/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.small.dao;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Agnaldo
 */
public class SmallDaoImpl {

    public static EntityManager CreateEm() {
        try {
            Map properties = new HashMap();
            properties.put("javax.persistence.jdbc.driver", "org.firebirdsql.jdbc.FBDriver");
            properties.put("javax.persistence.jdbc.url", "jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB");
            properties.put("javax.persistence.jdbc.user", "SYSDBA");
            properties.put("javax.persistence.jdbc.password","masterkey");
            EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("SMALLPU",properties);
            //properties.put("javax.persistence.jdbc.url",  "jdbc:firebirdsql://localhost:3050/" + DBpath + "?roleName=myrole");
            EntityManager entityManager = emf.createEntityManager();
            return entityManager;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Object saveImpl(EntityTransaction userTransaction, EntityManager entityManager, Object... obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = new JpaUtil().getEm();
            //transacao = entityManager.getTransaction();
            userTransaction.begin();
            //beginTransaction();
            for (int i = 0; i < obj.length; i++) {
                Object object = obj[i];
                Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(object);
                if (id == null) {
                    entityManager.persist(object);
                    entityManager.flush();
                    //entityManager.refresh(obj);
                    //obj = entityManager.merge(obj);
                } else {
                    obj[i] = entityManager.merge(object);
                }
            }
            userTransaction.commit();
            //commitTransaction();
            //entityManager.flush();
        } catch (Throwable e) {
            t = e;
            e.printStackTrace();
            userTransaction.rollback();
            obj = null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return obj;
        }
    }
}
