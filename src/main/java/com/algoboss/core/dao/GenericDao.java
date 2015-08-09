/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.dao;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;

import com.algoboss.core.entity.JpaUtil;

/**
 *
 * @author Agnaldo
 */
public class GenericDao {
    
    public static EntityManager entityManager;
    public static EntityTransaction transacao;

    public static Object save(Object obj) throws Throwable {
        Throwable t = null;
        try {
            entityManager = new JpaUtil().getEm();
            transacao = entityManager.getTransaction();
            transacao.begin();
            obj = entityManager.merge(obj);
            entityManager.flush();
            transacao.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
            //transacao.rollback();
            obj = null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return obj;
        }
    }

    public static List<Object> findAll(String named) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            objectList = entityManager.createNamedQuery(named).getResultList();

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }
    }

    public static List<Object> findAll(String named, Class resultClass) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            objectList = entityManager.createNamedQuery(named, resultClass).getResultList();

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }

    }

    public static List<Object> findAll(Class resultClass, List<Long> siteIdList) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery();
            Root from = criteriaQuery.from(resultClass);

            CriteriaQuery<Object> select = criteriaQuery.select(from);
            boolean execute = false;
            if (siteIdList != null) {
                if (!siteIdList.isEmpty()) {
                    Path<Object> path = from.join("instantiatesSite").get("instantiatesSiteId");
                    //select.where(criteriaBuilder.equal(path, siteId));
                    select.where(path.in(siteIdList));
                    select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
                    execute = true;
                } else {
                    objectList = new ArrayList();
                }
            } else {
                execute = true;
            }
            if (execute) {
                TypedQuery<Object> typedQuery = entityManager.createQuery(select);
                objectList = typedQuery.getResultList();
            }



            /*
             * CriteriaQuery cq =
             * entityManager.getCriteriaBuilder().createQuery();
             * cq.select(cq.from(resultClass)); if (siteId != null) {
             * cq.where(entityManager.getCriteriaBuilder().equal(cq.from(resultClass).join("instantiatesSite").get("instantiatesSiteId"),
             * siteId)); } Query q = entityManager.createQuery(cq); objectList =
             * q.getResultList();
             */
            //transacao.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }

    }

    public static Long findSeqnum(Class resultClass, Long siteId) {
        Throwable t = null;
        long seqnum = 0L;
        try {
            if (siteId != null) {
                System.out.println(resultClass.getName());
            //entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object ob = entityManager.createQuery("select t from PurSolicitation t").getSingleResult();
            //seqnum = (Long)entityManager.createQuery("select max(t.seqnum) from "+resultClass.getSimpleName()+" t where t.instantiatesSite.instantiatesSiteId = ?1").setParameter(1, siteId).getSingleResult();
                
            }

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            //e.printStackTrace();
            seqnum = 0L;
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            return seqnum+1;
        }

    }

    public static Object remove(Object obj) throws Throwable {
        Throwable t = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            transacao = entityManager.getTransaction();
            transacao.begin();
            entityManager.flush();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            transacao.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
            obj = null;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return obj;
        }
    }

    public static <T> T clone(T object) {
        T clone = null;

        try {
            /*
             * instanciando o objeto clone de acordo com o objeto passado por
             * parâmetro
             */
            clone = (T) object.getClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Obter o tipo de classe atual, quando acabar, passar para a super
        // classe, até chegar em Object.
        for (Class obj = object.getClass(); !obj.equals(Object.class); obj = obj.getSuperclass()) {
            // Percorrer campo por campo da classe...
            for (Field field : obj.getDeclaredFields()) {
                field.setAccessible(true);
                try {
                    // Copiar campo atual
                    field.set(clone, field.get(object));
                } catch (Throwable t) {
                }
            }

        }
        return clone;
    }
}
