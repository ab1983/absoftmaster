/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.algoboss.core.entity.AdmRepresentative;

/**
 *
 * @author Agnaldo
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class RepresentativeDao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Resource
    private UserTransaction userTransaction;
    @PersistenceContext(unitName = "ERPPU")
    public EntityManager entityManager;
    public EntityTransaction transacao;
    private boolean manualTransaction = false;

    public UserTransaction getUserTransaction() {
        manualTransaction = true;
        return userTransaction;
    }

    private void beginTransaction() {
        try {
            if (userTransaction.getStatus() != Status.STATUS_ACTIVE) {
                userTransaction.begin();
            }
        } catch (Exception e) {
        }
    }

    private void commitTransaction() {
        if (!manualTransaction) {
            try {
                userTransaction.commit();
            } catch (Exception ex) {
                Logger.getLogger(RepresentativeDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
   public Object save(Object obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = new JpaUtil().getEm();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
            userTransaction.begin();
            if (id == null) {
                entityManager.persist(obj);
                //entityManager.flush();
                //obj = entityManager.merge(obj);
            } else {
                obj = entityManager.merge(obj);
            }
            userTransaction.commit();
            //entityManager.flush();
            //transacao.commit();
        } catch (Exception e) {
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

    public Object save(Object... obj) throws Throwable {
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
        } catch (Exception e) {
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

    public List<Object> findAll(String named) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            //entityManager = JpaUtil.getEntityManager();
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
                //entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }
    }

    public List<Object> findAll(String named, Class resultClass) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            //entityManager = JpaUtil.getEntityManager();
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
                //entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }

    }

    public List<Object> findAll(Class resultClass, List<Long> siteIdList) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        try {
            //entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            //entityManager.flush();
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
                typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
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
                //entityManager.close();
            }
            if (t != null) {
                throw t;
            }
            return objectList;
        }

    }

    public Long findSeqnum(Class resultClass, Long siteId) {
        Throwable t = null;
        long seqnum = 0L;
        try {
            if (siteId != null) {
                System.out.println(resultClass.getName());
                //entityManager = JpaUtil.getEntityManager();
                //transacao = entityManager.getTransaction();
                //transacao.begin();
                //Object ob = entityManager.createQuery("select t from PurSolicitation t").getSingleResult();
                seqnum = (Long) entityManager.createQuery("select max(t.seqnum) from " + resultClass.getSimpleName() + " t where t.instantiatesSite.instantiatesSiteId = ?1").setParameter(1, siteId).getSingleResult();

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
            return seqnum + 1;
        }

    }

    public Object remove(Object obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = JpaUtil.getEntityManager();
            userTransaction.begin();
            entityManager.flush();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            userTransaction.commit();
        } catch (Exception e) {
            t = e;
            e.printStackTrace();
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

    public <T> T clone(T object) {
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
    public List<AdmRepresentative> findBySupervisorId(Long supervisorId) {
        List<AdmRepresentative> list = null;
        try {
            list = entityManager.createQuery("select u from AdmRepresentative u where u.supervisor.representativeId = ?1 ").setParameter(1, supervisorId).getResultList();
        } catch (NoResultException e) {
            list = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            return list;
        }
    }    
}
