/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.dao;

import com.algoboss.erp.entity.AdmContract;
import com.algoboss.erp.entity.AdmInstantiatesSite;
import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevEntityPropertyDescriptor;
import com.algoboss.erp.entity.DevEntityPropertyDescriptorConfig;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.*;

/**
 *
 * @author Agnaldo
 */
@Stateless
@TransactionManagement(TransactionManagementType.BEAN)
public class BaseDao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Resource/*(shareable=true)*/
    private UserTransaction userTransaction;
    @PersistenceContext(/*type=PersistenceContextType.TRANSACTION,*/properties={@PersistenceProperty(name="javax.persistence.sharedCache.mode", value="ENABLE_SELECTIVE")},unitName = "ERPPU")
    public EntityManager entityManager;
    @PersistenceContext(/*type=PersistenceContextType.TRANSACTION,*/properties={@PersistenceProperty(name="javax.persistence.sharedCache.mode", value="ENABLE_SELECTIVE")},unitName = "SMALLPU")
    public EntityManager entityManagerSmall;    
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
                Logger.getLogger(BaseDao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
    public Object save(Object obj) throws Throwable {
    	if(obj instanceof DevEntityObject){
    		DevEntityObject entObj = (DevEntityObject)obj;
    		if(entObj.getEntityClass().getCanonicalClassName()!=null && !entObj.getEntityClass().getCanonicalClassName().isEmpty()){
    			return saveReplicate(entObj);
    		}
    	}
    	return saveImpl(obj);
    }
    public Object saveImpl(Object obj) throws Throwable {
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
                entityManager.refresh(obj);
                userTransaction.commit();
                //obj = entityManager.merge(obj);
            } else {
                userTransaction.begin();
                obj = entityManager.merge(obj);
                entityManager.flush();
                entityManager.refresh(obj);
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
    	return findByObj(obj, true);
    }
    
    public Object findByObj(Object obj, boolean clearCache) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = new JpaUtil().getEm();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
            if (id != null) {
            	if(clearCache){
            		entityManager.getEntityManagerFactory().getCache().evict(obj.getClass(), id);
            	}
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
                    entityManager.refresh(object);
                    //obj = entityManager.merge(obj);
                } else {
                    obj[i] = entityManager.merge(object);
                    entityManager.flush();
                    entityManager.refresh(obj[i]);
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
    	if(obj instanceof DevEntityObject){
    		DevEntityObject entObj = (DevEntityObject)obj;
    		if(entObj.getEntityClass().getCanonicalClassName()!=null && !entObj.getEntityClass().getCanonicalClassName().isEmpty()){
    			return removeReplicate(entObj);
    		}
    	}
    	return removeImpl(obj);    	
    }
    public Object removeImpl(Object obj) throws Throwable {
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
    //@TransactionAttribute(TransactionAttributeType.MANDATORY)
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
    public List<DevEntityObject> findEntityObjectByClass(DevEntityClass className, List<Long> siteIdList, Date startDate, Date endDate) {
    	return findEntityObjectByClass(className, siteIdList, startDate, endDate, false);
    }
    public List<DevEntityObject> findEntityObjectByClass(DevEntityClass className, List<Long> siteIdList, Date startDate, Date endDate, boolean refresh) {
    	if(className.getCanonicalClassName()!=null && !className.getCanonicalClassName().isEmpty()){
    		return findEntityObjectByClassReplicate(className, siteIdList, startDate, endDate, refresh);
    	}else{
    		return findEntityObjectByClassImpl(className, siteIdList, startDate, endDate, refresh);
    	}
    }
    public List<DevEntityObject> findEntityObjectByClassImpl(DevEntityClass className, List<Long> siteIdList, Date startDate, Date endDate, boolean refresh) {
        Throwable t = null;
        List<DevEntityObject> objectList = null;
        Date ini = new Date();
        try {
        	if(className==null){
        		throw new IllegalArgumentException("ClassName is empty or null in findEntityObjectByClass.");
        	}
            CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
            if(refresh){
            	entityManager.getEntityManagerFactory().getCache().evict(DevEntityObject.class);
            }
            CriteriaQuery<DevEntityObject> criteriaQuery = criteriaBuilder.createQuery(DevEntityObject.class);
            Root from = criteriaQuery.from(DevEntityObject.class);

            Path<Object> path2 = from.join("entityClass").get("name");
            CriteriaQuery<DevEntityObject> select = criteriaQuery.select(from);
            Predicate where1 = criteriaBuilder.equal(path2, className.getName());
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
            } else {
                execute = true;
            }
            if (!whereList.isEmpty()) {
            	criteriaQuery.select(from).where(whereList.toArray(new Predicate[whereList.size()]));
            	select.orderBy(criteriaBuilder.asc(from.get("seqnum")));
            }
            if (execute) {
                TypedQuery<DevEntityObject> typedQuery = entityManager.createQuery(select);
                if(refresh){
                	typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
                }
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
            Date end = new Date();
            System.err.println("Time Dao "+className.getName()+": "+(end.getTime()-ini.getTime()));
            return objectList;
        }
    }
    public String generateEntityName(String entityClassName) {
        Throwable t = null;
        String entityName = entityClassName;
        try {
            if (entityClassName != null) {
                System.out.println(entityClassName);
                //entityManager = JpaUtil.getEntityManager();
                //transacao = entityManager.getTransaction();
                //transacao.begin();
                //Object ob = entityManager.createQuery("select t from PurSolicitation t").getSingleResult();
                entityName = entityName.concat(Objects.toString(entityManager.createQuery("select max(t.entityClassId) from DevEntityClass t where t.name = ?1").setParameter(1, entityClassName).getSingleResult(), ""));
            }

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            //e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            return entityName;
        }

    }
    
    public Object saveReplicate(DevEntityObject obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = new JpaUtil().getEm();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
            if (id == null) {
                userTransaction.begin();
                entityManager.persist(obj);
                if(obj.getEntityClass().getCanonicalClassName()!=null){
                	CreateEm();
                	Class clazz = Class.forName(obj.getEntityClass().getCanonicalClassName());
                	Object objRepl = clazz.newInstance();
                	Field[] fields = clazz.getDeclaredFields();
                	for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
							field.setAccessible(true);
							Object val = obj.getProp(field.getName().toLowerCase());
							try {							
								field.set(objRepl, val);
							} catch (IllegalArgumentException e) {
								if(val!=null){
									throw new IllegalArgumentException(e);
								}
								// TODO: handle exception
							}
						}
						
					}
                	entityManagerSmall.persist(objRepl);
                	entityManagerSmall.flush();
                	entityManagerSmall.refresh(objRepl);
                }
                entityManager.flush();
                entityManager.refresh(obj);
                
                userTransaction.commit();
                //obj = entityManager.merge(obj);
            } else {
                userTransaction.begin();
                obj = entityManager.merge(obj);
                entityManager.flush();
                entityManager.refresh(obj);                
                if(obj.getEntityClass().getCanonicalClassName()!=null){
                	CreateEm();
                	Class clazz = Class.forName(obj.getEntityClass().getCanonicalClassName());
                	Object objRepl = clazz.newInstance();
                	Field[] fields = clazz.getDeclaredFields();
                	for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
							field.setAccessible(true);
							Object val = obj.getProp(field.getName().toLowerCase());
							try {							
								field.set(objRepl, val);
							} catch (IllegalArgumentException e) {
								if(val!=null){
									throw new IllegalArgumentException(e);
								}
								// TODO: handle exception
							}
						}
						
					}
                	objRepl = entityManagerSmall.merge(objRepl);
                	entityManagerSmall.flush();
                	entityManagerSmall.refresh(objRepl);
                }                
                
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

    public Object removeReplicate(DevEntityObject obj) throws Throwable {
        Throwable t = null;
        try {
            //entityManager = JpaUtil.getEntityManager();
            userTransaction.begin();
            entityManager.flush();
            obj = entityManager.merge(obj);
            entityManager.remove(obj);
            if(obj.getEntityClass().getCanonicalClassName()!=null){
            	//CreateEm();
            	Class clazz = Class.forName(obj.getEntityClass().getCanonicalClassName());
            	Object objRepl = clazz.newInstance();
            	Field[] fields = clazz.getDeclaredFields();
            	for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
						field.setAccessible(true);
						Object val = obj.getProp(field.getName().toLowerCase());
						try {							
							field.set(objRepl, val);
						} catch (IllegalArgumentException e) {
							if(val!=null){
								throw new IllegalArgumentException(e);
							}
							// TODO: handle exception
						}
					}
					
				}
            	entityManagerSmall.flush();
                objRepl = entityManagerSmall.merge(objRepl);
                entityManagerSmall.remove(objRepl);            	
            }                        
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
    public List<DevEntityObject> findEntityObjectByClassReplicate(DevEntityClass className, List<Long> siteIdList, Date startDate, Date endDate, boolean refresh) {
        Throwable t = null;
        List<DevEntityObject> objectList = new ArrayList<DevEntityObject>();
        Date ini = new Date();
        try {
        	if(className==null){
        		throw new IllegalArgumentException("ClassName is empty or null in findEntityObjectByClass.");
        	}
            boolean execute = false;        	
            /**
             * Consulta para integragração
             */
            if(className.getCanonicalClassName()!=null){            	
            	//CreateEm();
            	Class clazz = Class.forName(className.getCanonicalClassName());
            	Class<?> clazzType = Class.forName(className.getCanonicalClassName());
            	List objectListSmall = null;
            	List objectListSmallRest = null;
	            CriteriaBuilder criteriaBuilderSmall = entityManagerSmall.getCriteriaBuilder();
	            if(refresh){
	            	entityManagerSmall.getEntityManagerFactory().getCache().evict(clazz);
	            }
	            CriteriaQuery criteriaQuerySmall = criteriaBuilderSmall.createQuery(clazzType);
	            Root fromSmall = criteriaQuerySmall.from(clazz);
	
	            //Path<Object> path2Small = fromSmall.join("entityClass").get("name");
	            CriteriaQuery<DevEntityObject> selectSmall = criteriaQuerySmall.select(fromSmall);
	           // Predicate where1Small = criteriaBuilderSmall.equal(path2Small, className.getName());            
                TypedQuery<DevEntityObject> typedQuerySmall = entityManagerSmall.createQuery(selectSmall);
                if(refresh){
                	typedQuerySmall.setHint("javax.persistence.cache.storeMode", "REFRESH");
                }
                objectList = findEntityObjectByClassImpl(className, siteIdList, startDate, endDate, refresh);
                objectListSmall = typedQuerySmall.getResultList();
	            objectListSmallRest = new ArrayList(objectListSmall);
	            //for (Iterator iterator = objectListSmall.iterator(); iterator.hasNext();) {
					//Object objRepl = (Object) iterator.next();
				userTransaction.begin();
				Object objRepl2 = clazz.newInstance();
				Field[] fields = clazz.getDeclaredFields();
				entityManagerSmall.flush();
				entityManager.flush();	            
				for (Iterator iterator2 = objectList.iterator(); iterator2.hasNext();) {
					DevEntityObject obj = (DevEntityObject) iterator2.next();	
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
							field.setAccessible(true);
							Object val = obj.getProp(field.getName().toLowerCase());
							try {							
								field.set(objRepl2, val);
							} catch (IllegalArgumentException e) {
								if(val!=null){
									throw new IllegalArgumentException(e);
								}
								// TODO: handle exception
							} catch (Exception e) {
								if(val!=null){
									throw new IllegalArgumentException(e);
								}
								// TODO: handle exception
							}
						}							
					}
					
					//objRepl2 = entityManagerSmall.merge(objRepl2);	
					objectListSmallRest.remove(objRepl2);
				}
					
					
					
										
	            	
	            //}
	            for (Iterator iterator3 = objectListSmallRest.iterator(); iterator3.hasNext();) {
	            	Object objRepl3 = (Object) iterator3.next();
	            	DevEntityObject obj = new DevEntityObject(className);
	            	AdmInstantiatesSite site = new AdmInstantiatesSite();
	            	site.setInstantiatesSiteId(siteIdList.get(0));
	            	obj.setInstantiatesSite(site);
	            	for (int i = 0; i < fields.length; i++) {
	            		Field field = fields[i];
	            		if(!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")){
	            			field.setAccessible(true);
	            			Object val = field.get(objRepl3);
	            			obj.getPropObj(field.getName().toLowerCase()).setVal(val);
	            		}					
	            	}	            	            	
	            	obj = entityManager.merge(obj);		
	            	objectList.add(obj);
	            }
            }  
            
            userTransaction.commit();
        	
        	     
            
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
    public void CreateEm() {
        try {
            //entityManagerSmall.setProperty("javax.persistence.jdbc.driver", "org.firebirdsql.jdbc.FBDriver");
            //entityManagerSmall.setProperty("javax.persistence.jdbc.url", "jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB)");
            //entityManagerSmall.setProperty("javax.persistence.jdbc.url", "jdbc:firebirdsql:algoboss.zapto.org/3050:D:\\Documents\\@PESSOAL\\ERP\\integração small\\SMALL.GDB");            
            //entityManagerSmall.setProperty("javax.persistence.jdbc.user", "SYSDBA");
            //entityManagerSmall.setProperty("javax.persistence.jdbc.password","masterkey");
            //EntityManagerFactory emf = javax.persistence.Persistence.createEntityManagerFactory("SMALLPU",properties);
            //properties.put("javax.persistence.jdbc.url",  "jdbc:firebirdsql://localhost:3050/" + DBpath + "?roleName=myrole");
            //EntityManager entityManager = emf.createEntityManager();
            //return entityManager;
        } catch (Throwable e) {
            e.printStackTrace();
            //return null;
        }
    }
    
    
}
