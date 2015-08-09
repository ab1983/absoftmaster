/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.small.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import com.algoboss.app.entity.DevEntityClass;
import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.core.entity.AdmContract;

/**
 *
 * @author Agnaldo
 */
//@Stateless
//@TransactionManagement(TransactionManagementType.BEAN)
public class SmallDaoOld implements Serializable {

    private static final long serialVersionUID = 1L;
    //@Resource
    private UserTransaction userTransaction;
    //@PersistenceContext(properties={@PersistenceProperty(name="javax.persistence.sharedCache.mode", value="ENABLE_SELECTIVE")},unitName = "SMALLPU")
    public EntityManager entityManager;
    public EntityTransaction transacao;
    private boolean manualTransaction = false;

    public UserTransaction getUserTransaction() {
        manualTransaction = true;
        return userTransaction;
    }

    public EntityManager getEntityManager() {
        return entityManager;
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
                Logger.getLogger(SmallDao.class.getName()).log(Level.SEVERE, null, ex);
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
            if (id == null) {
                userTransaction.begin();
                entityManager.persist(obj);
                entityManager.flush();
                userTransaction.commit();
                //obj = entityManager.merge(obj);
            } else {
                userTransaction.begin();
                obj = entityManager.merge(obj);
                userTransaction.commit();
            }
            //entityManager.flush();
            //transacao.commit();
        } catch (Throwable e) {
            t = e;
            if (!(e instanceof Error)) {
                e.printStackTrace();
            }
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

    public Object findByObj(Object obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = new JpaUtil().getEm();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
            if (id != null) {
                //entityManager.getEntityManagerFactory().getCache().evict(obj.getClass(), id);
                obj = entityManager.find(obj.getClass(), id);
            }
            //transacao.commit();
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
    public Object save(Object... obj) throws Throwable {
        return saveImpl(null, entityManager, obj);
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
        return findAll(resultClass, siteIdList, null, null);
    }

    public List<Object> findAll(Class resultClass, List<Long> siteIdList, Date startDate, Date endDate) throws Throwable {
        Throwable t = null;
        List<Object> objectList = null;
        Date ini = new Date();
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
            if (siteIdList != null || (startDate != null && endDate != null)) {
                if (siteIdList != null && !siteIdList.isEmpty()) {
                    Path<Object> path = from.join("instantiatesSite").get("instantiatesSiteId");
                    //select.where(criteriaBuilder.equal(path, siteId));
                    select.where(path.in(siteIdList));
                    select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
                    execute = true;
                } else {
                    objectList = new ArrayList();
                }
                if (startDate != null && endDate != null) {
                    //Path<Object> path = from.join("instantiatesSite").get("instantiatesSiteId");
                    //ParameterExpression<Date> d = criteriaBuilder.parameter(Date.class);
                    //criteriaBuilder.between(d, from.<Date>get("from"), from.<Date>get("to")); 
                    select.where(criteriaBuilder.between(from.get("registrationDate").as(Date.class), startDate, endDate));//equal(path, siteId));
                    //select.where(path.in(siteIdList));
                    //select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
                    execute = true;
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
            Date fim = new Date();
            System.err.println("FindAll Time"+resultClass+" :"+(fim.getTime() - ini.getTime()));
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
            //e.printStackTrace();
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

    public void resetCurrentLicense() {
        List<AdmContract> list = null;
        try {
            userTransaction.begin();
            entityManager.createQuery("update AdmServiceModuleContract u set u.currentAmount = 0").executeUpdate();
            userTransaction.commit();
        } catch (NoResultException e) {
            list = null;
        } catch (Exception e) {
            e.printStackTrace();
            //userTransaction.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
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

    //DEV
    public List<DevEntityClass> findAllEntityClass() {
        List<DevEntityClass> list = null;
        try {
            list = entityManager.createQuery("select u from DevEntityClass u").getResultList();

            //transacao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return list;
        }
    }

    public List<DevEntityObject> findEntityObjectByClass(String className, List<Long> siteIdList, Date startDate, Date endDate) {
        Throwable t = null;
        List<DevEntityObject> objectList = null;
        Date ini = new Date();
        try {
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            CriteriaQuery<DevEntityObject> criteriaQuery = criteriaBuilder.createQuery(DevEntityObject.class);
            Root from = criteriaQuery.from(DevEntityObject.class);

            Path<Object> path2 = from.join("entityClass").get("name");
            CriteriaQuery<DevEntityObject> select = criteriaQuery.select(from);
            Predicate where1 = criteriaBuilder.equal(path2, className);
            boolean execute = false;
            //select.where(criteriaBuilder.equal(path, siteId));
            List<Predicate> whereList = new ArrayList<Predicate>();
            whereList.add(where1);
            //select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
            if (siteIdList != null || (startDate != null && endDate != null)) {
                if (siteIdList != null && !siteIdList.isEmpty()) {
                    Path<Object> path = from.join("instantiatesSite").get("instantiatesSiteId");
                    //select.where(criteriaBuilder.equal(path, siteId));
                    //select.where(path.in(siteIdList));
                    whereList.add(path.in(siteIdList));
                    execute = true;
                } else {
                    objectList = new ArrayList();
                }
                if (startDate != null && endDate != null) {
                    //Path<Object> path = from.join("instantiatesSite").get("instantiatesSiteId");
                    //ParameterExpression<Date> d = criteriaBuilder.parameter(Date.class);
                    //criteriaBuilder.between(d, from.<Date>get("from"), from.<Date>get("to")); 
                    //select.where(path.in(siteIdList));
                    //select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
                    whereList.add(criteriaBuilder.between(from.get("registrationDate").as(Date.class), startDate, endDate));//equal(path, siteId));
                    execute = true;
                }
                if (!whereList.isEmpty()) {
                    criteriaQuery.select(from).where(whereList.toArray(new Predicate[whereList.size()]));
                    select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
                }
            } else {
                execute = true;
            }
            if (execute) {
                TypedQuery<DevEntityObject> typedQuery = entityManager.createQuery(select);
                //typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
                objectList = typedQuery.getResultList();
                String x = "";
            }

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            //e.printStackTrace();
            objectList = null;
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            Date fim = new Date();
            System.err.println("Time Dao "+className+": "+(fim.getTime()-ini.getTime()));
            return objectList;
        }

    }

}
