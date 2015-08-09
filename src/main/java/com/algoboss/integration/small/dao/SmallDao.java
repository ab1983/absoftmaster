/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.small.dao;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.persistence.CacheRetrieveMode;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.PersistenceProperty;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.sql.DataSource;
import javax.transaction.Status;
import javax.transaction.UserTransaction;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.aspectj.AbstractDependencyInjectionAspect;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.mock.jndi.SimpleNamingContextBuilder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.algoboss.core.entity.AdmInstantiatesSite;
import com.algoboss.app.entity.DevEntityClass;
import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.entity.DevEntityPropertyValue;
import com.algoboss.core.face.GenericBean;
import com.algoboss.core.face.GerLoginBean;
import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 *
 * @author Agnaldo
 */
//@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER)
@Repository
//@Transactional(propagation=Propagation.REQUIRED,noRollbackFor=javax.persistence.EntityNotFoundException.class)
public class SmallDao /*extends JdbcDaoSupport*/ implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2470841392919888321L;
	//@Resource(shareable = true)
	private UserTransaction userTransaction;
	@PersistenceContext(unitName = "SMALLPU",type = PersistenceContextType.TRANSACTION, properties = { @PersistenceProperty(name = "javax.persistence.sharedCache.mode", value = "ENABLE_SELECTIVE") }) 
	private EntityManager entityManager;
	public AbstractRoutingDataSource dataSource2;
	private EntityTransaction transacao;
	private boolean manualTransaction = false;
	private GerLoginBean loginBean;
	private String dsKey;	

	public SmallDao() {
		super();
		//CreateEm();
	}

	public UserTransaction getUserTransaction() {
		manualTransaction = true;
		return userTransaction;
	}
	
	public static SmallDao createInstance(GerLoginBean loginBean,SmallDao actual)throws Throwable {
		Throwable t = null;
		Date ini = new Date();
		try {

			/*
		
			if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1733){
				entityManagerSmall = entityManagerSmall_1;
			}else if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1766){
				entityManagerSmall = entityManagerSmall_2;
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1765){
				entityManagerSmall = entityManagerSmall_3;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1767){
				entityManagerSmall = entityManagerSmall_4;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1768){
				entityManagerSmall = entityManagerSmall_5;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1769){
				entityManagerSmall = entityManagerSmall_6;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1770){
				entityManagerSmall = entityManagerSmall_7;			
			}else {
				entityManagerSmall = entityManagerSmall_1;			
			}*/	
			String url="jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB";
			if(loginBean!=null && loginBean.getInstantiatesSiteContract()!=null && loginBean.getInstantiatesSiteContract().getCompany()!=null){
				url = loginBean.getInstantiatesSiteContract().getCompany().getDataSource();
			}				
			String driver="org.firebirdsql.jdbc.FBDriver";
			String url2="jdbc:firebirdsql:127.0.0.1/3050:D:\\Documents\\@PESSOAL\\ERP\\integração small\\SMALL.GDB?encoding=WIN1252";
			String url3="jdbc:firebirdsql:sistemasmall.no-ip.org/3050:C:/Program Files/SmallSoft/Small Commerce/SMALL.GDB";
			String username="sysdba";
			String password="masterkey";
			String validationQuery = "select current_timestamp from RDB$DATABASE";
			boolean refresh = false;
			if(actual == null){
				refresh = true;
			}			
			if(refresh){
				SmallDao.resetDataSource();
			}
			Date iniDS = new Date();
			WebApplicationContext ctx =  FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());			
			//ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"applicationContextSmall.xml"},false);
			String dsKey = driver+url+username+password;
			//System.setProperty("socksProxyHost", "localhost");
			//System.setProperty("socksProxyPort", "8089");			
			if(!DataSourceContextHolder.isSetted(dsKey)){
				refresh = true;
				//ctx.refresh();
				DataSourceFactory dataSourceFactory = new DataSourceFactory(ctx);
				DataSource ds = dataSourceFactory.getDataSourceFromDbcpBasicDataSource(driver, url, username, password, validationQuery);	
				Connection connection = ds.getConnection();
				DataSourceContextHolder.setTargetDataSourceKey(dsKey,ds);
				connection.close();
			}else{
				if(refresh){
					//ctx.refresh();
				}
				Date iniDS2 = new Date();
				DataSourceContextHolder.setTargetDataSourceKey(dsKey);
				//getDataSource().getConnection().close();
				//System.err.println("Time CreateSmallDao-INI DS2 " + ": " + (new Date().getTime() - iniDS2.getTime()));
				//return null;
			}
			//System.err.println("Time CreateSmallDao-INI DS " + ": " + (new Date().getTime() - iniDS.getTime()));
			if(refresh){
				SmallDao smallDao = ctx.getBean(SmallDao.class);
				smallDao.dsKey = dsKey;	
				try {
					smallDao.getEntityManager().getEntityManagerFactory();
				} catch (Exception e) {
					DataSourceContextHolder.clearTargetDataSourceMap();
				}				
				actual = smallDao;				
			}
			//actual.getEntityManager().getEntityManagerFactory();
			return actual;
		} catch (Throwable e) {
			t = e;
		
			if (!(e instanceof Error)) {
				e.printStackTrace();
			}
			if(e instanceof java.sql.SQLException){
				t = new java.sql.SQLException("NÃO FOI POSSÍVEL CONECTAR AO BANCO DE DADOS. FAVOR VERIFICAR SE O SERVIDOR ESTÁ DISPONÍVEL E SE OS DADOS DE ACESSO ESTÃO CORRETOS.\nDetalhe: ("+e.getMessage()+")",e);
				//Logger.getLogger(SmallDao.class.getName()).log(Level.SEVERE, null, t);
			}
			// userTransaction.rollback();
			//obj = null;
			return null;
		} finally {
			// if (entityManager != null && entityManager.isOpen()) {
			// entityManager.close();
			// }
			//System.err.println("Time CreateSmallDao " + ": " + (new Date().getTime() - ini.getTime()));
			if (t != null) {
				throw t;
			}			
		}	
	}
	
	public EntityManager getEntityManager() {
		try {
			/*
			if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1733){
				entityManagerSmall = entityManagerSmall_1;
			}else if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1766){
				entityManagerSmall = entityManagerSmall_2;
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1765){
				entityManagerSmall = entityManagerSmall_3;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1767){
				entityManagerSmall = entityManagerSmall_4;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1768){
				entityManagerSmall = entityManagerSmall_5;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1769){
				entityManagerSmall = entityManagerSmall_6;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1770){
				entityManagerSmall = entityManagerSmall_7;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1771){
				entityManagerSmall = entityManagerSmall_8;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1772){
				entityManagerSmall = entityManagerSmall_9;			
			}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1773){
				entityManagerSmall = entityManagerSmall_10;			
			}else {
				entityManagerSmall = entityManagerSmall_1;			
			}
*/
			
			
			/*
			DataSourceFactory dataSourceFactory = new DataSourceFactory(ctx);
			String url="jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB";
			if(loginBean!=null && loginBean.getInstantiatesSiteContract()!=null && loginBean.getInstantiatesSiteContract().getCompany()!=null){
				url = loginBean.getInstantiatesSiteContract().getCompany().getDataSource();
			}				
			String driver="org.firebirdsql.jdbc.FBDriver";
			String url2="jdbc:firebirdsql:127.0.0.1/3050:D:\\Documents\\@PESSOAL\\ERP\\integração small\\SMALL.GDB?encoding=WIN1252";
			String url3="jdbc:firebirdsql:sistemasmall.no-ip.org/3050:C:/Program Files/SmallSoft/Small Commerce/SMALL.GDB";
			String username="sysdba";
			String password="masterkey";			
			DataSource ds = dataSourceFactory.getDataSourceFromDbcpBasicDataSource(driver, url, username, password);	
			ds.getConnection();
			
			DataSourceContextHolder.setTargetDataSource(ds);
			
			ctx.start();
			ctx.getBean(LocalContainerEntityManagerFactoryBean.class).destroy();
			ctx.refresh();
			ConfigurableApplicationContext context = ctx;//new ClassPathXmlApplicationContext("applicationContextSmall.xml");
			DataSource obj = context.getBean("small-ds1",DriverManagerDataSource.class);
			LocalContainerEntityManagerFactoryBean emf =  context.getBean(LocalContainerEntityManagerFactoryBean.class);
			PersistenceProvider pp =  emf.getPersistenceProvider();
			EntityManagerFactory emf2 = emf.nativeEntityManagerFactory;
			emf.setDataSource(ds);
			emf.setJtaDataSource(ds);
			emf.afterPropertiesSet();
			//emf.destroy();
	
			//BasicDataSourceFactory.createDataSource(connectionProperties)
			//emf.nativeEntityManagerFactory.close();
			SingletonBeanRegistry registry = context.getBeanFactory();
			//registry.registerSingleton("small-ds1", ds);
			context.stop();
			context.start();
		    SmallDao smallDao = context.getBean(SmallDao.class);
		    smallDao.setDataSource(obj);
		    //DataSourceContextHolder.setTargetDataSource("SMALL2");
		    //emf.nativeEntityManagerFactory.close();
		    PersistenceUnitUtil puu = emf.nativeEntityManagerFactory.getPersistenceUnitUtil();
		    Field f = puu.getClass().getDeclaredField("setupImpl");
		    f.setAccessible(true);
		    Field fieldPui =  f.get(puu).getClass().getDeclaredField("persistenceUnitInfo");
		    fieldPui.setAccessible(true);
		    PersistenceUnitInfo puiSetup = (PersistenceUnitInfo)fieldPui.get(f.get(puu));
		    //getNativeEntityManagerFactory().getPersistenceUnitUtil().getPersistenceUnitInfo().getNonJtaDataSource().setJtaDataSource(obj);
		    //entityManagerSmall = emf2.createEntityManager(emf2.getProperties());
		    Field fieldNjds = puiSetup.getClass().getSuperclass().getDeclaredField("nonJtaDataSource"); 
		    fieldNjds.setAccessible(true);
		    fieldNjds.set(puiSetup, ds);
		    PersistenceUnitInfo pui = emf.getPersistenceUnitInfo();
		    //pui.getJtaDataSource()getNonJtaDataSource().getConnection()
		    ds.getConnection();
		    entityManagerSmall = pp.createContainerEntityManagerFactory(emf.getPersistenceUnitInfo(), emf.getJpaPropertyMap()).createEntityManager();
		    */
			//this.setEntityManager(loginBean);
			//entityManagerSmall = this.getEntityManagerSmall();
			return entityManager;			
		} catch (Exception e) {
			return null;
		}
	}
	public static void resetDataSource(){
		getConnection();
		DataSourceContextHolder.clearTargetDataSource();		
	}
	public static Connection getConnection() {
		try {
			return getDataSource().getConnection();	
		} catch (Exception e) {
		}
		DataSourceContextHolder.clearTargetDataSourceMap();
		return null;
	}	

	public static DataSource getDataSource(){
		Object ds = DataSourceContextHolder.getTargetDataSource();
		if(ds instanceof DataSource){
			DataSource ds2 = (DataSource)ds;
			return ds2;						
		}
		return null;
	}
	
	public GerLoginBean getLoginBean() {
		return loginBean;
	}

	public void setLoginBean(GerLoginBean loginBean) {
		this.loginBean = loginBean;
	}

	private void beginTransaction() {
		try {
			if (userTransaction.getStatus() != Status.STATUS_ACTIVE) {
				// userTransaction.begin();
			}
		} catch (Exception e) {
		}
	}

	private void commitTransaction() {
		if (!manualTransaction) {
			try {
				// userTransaction.commit();
			} catch (Exception ex) {
				Logger.getLogger(SmallDao.class.getName()).log(Level.SEVERE, null, ex);
			}

		}

	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)	
	public Object saveReplicate(DevEntityObject obj) throws Throwable {
		Throwable t = null;
		try {
			// entityManager = new JpaUtil().getEm();
			//transacao = getEntityManagerSmall().getTransaction();
			//transacao.begin();
			Object id = "";// entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(obj);
			if (id == null) {
				// userTransaction.begin();
				// entityManager.persist(obj);
				if (obj.getEntityClass().getCanonicalClassName() != null) {
					Object objRepl = entityObjectSyncImpl(false, obj);
					getEntityManager().persist(objRepl);
					getEntityManager().flush();
					getEntityManager().refresh(objRepl);
				}
				// entityManager.flush();
				// entityManager.refresh(obj);

				// userTransaction.commit();
				// obj = entityManager.merge(obj);
			} else {
				// userTransaction.begin();
				// obj = entityManager.merge(obj);
				// entityManager.flush();
				// entityManager.refresh(obj);
				if (obj.getEntityClass().getCanonicalClassName() != null) {
					CreateEm();
					Object objRepl = entityObjectSyncImpl(false, obj);
					objRepl = getEntityManager().merge(objRepl);
					getEntityManager().flush();
					getEntityManager().refresh(objRepl);
				}

				// userTransaction.commit();
			}
			// entityManager.flush();
			//transacao.commit();
		} catch (Throwable e) {
			t = e;
			if (!(e instanceof Error)) {
				e.printStackTrace();
			}
			//transacao.rollback();
			obj = null;
		} finally {
			// if (entityManager != null && entityManager.isOpen()) {
			// entityManager.close();
			// }
			if (t != null) {
				throw t;
			}
			return obj;
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)	
	public Object findByObjImplReplicate(Object obj, boolean clearCache) throws Throwable {
		Throwable t = null;
		try {

			// entityManager = new JpaUtil().getEm();
			// transacao = entityManager.getTransaction();
			// transacao.begin();
			// userTransaction.begin();
			if (obj instanceof DevEntityObject) {
				Object objRepl = entityObjectSyncImpl(false, (DevEntityObject) obj);
				Object id = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objRepl);
				if (id != null) {
					if (clearCache) {
						getEntityManager().getEntityManagerFactory().getCache().evict(objRepl.getClass(), id);
					}
					objRepl = getEntityManager().find(objRepl.getClass(), id);
				} else {
					objRepl = getEntityManager().merge(objRepl);
					getEntityManager().flush();
					getEntityManager().refresh(objRepl);
				}
				entityObjectImportSyncConverter(objRepl, (DevEntityObject) obj, true);
			}

			// obj = entityManager.merge(obj);
			// entityManager.refresh(obj);
			// userTransaction.commit();
		} catch (Throwable e) {
			t = e;
			e.printStackTrace();
			// userTransaction.rollback();
			obj = null;
		} finally {
			// if (entityManager != null && entityManager.isOpen()) {
			// entityManager.close();
			// }
			if (t != null) {
				throw t;
			}
			return obj;
		}
	}

	public Object saveRefresh(boolean refresh, Object... obj) throws Throwable {
		if (obj[0] instanceof DevEntityObject) {
			DevEntityObject entObj = (DevEntityObject) obj[0];
			if (entObj.getEntityClass().getCanonicalClassName() != null && !entObj.getEntityClass().getCanonicalClassName().isEmpty()) {
				return saveReplicate(entObj);
			}
		}
		return null;//saveImpl(refresh, obj);
	}
	
	@Transactional(propagation=Propagation.REQUIRED, noRollbackFor=javax.persistence.EntityNotFoundException.class,rollbackFor=Exception.class)	
	public Object refresh(Object objRepl) throws Throwable {
		//getEntityManager().flush();
		Object id = getEntityManager().getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(objRepl);
		if (id != null) {
			getEntityManager().getEntityManagerFactory().getCache().evict(objRepl.getClass(), id);
			objRepl = getEntityManager().find(objRepl.getClass(), id);
		} 
		if(false){
			objRepl = getEntityManager().merge(objRepl);
			getEntityManager().flush();
			//getEntityManager().refresh(objRepl);
			if(getEntityManager().contains(objRepl)){
				getEntityManager().refresh(objRepl);							
			}		
		}
		return objRepl;
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

	// DEV

	public List<DevEntityObject> findEntityObjectByClass(DevEntityClass className, List<String> cols, List<Long> siteIdList, Date startDate, Date endDate) throws Throwable {
		return findEntityObjectByClass(className, cols, siteIdList, startDate, endDate, false);
	}

	public List<DevEntityObject> findEntityObjectByClass(DevEntityClass className, List<String> cols, List<Long> siteIdList, Date startDate, Date endDate, boolean refresh) throws Throwable {
		if (className.getCanonicalClassName() != null && !className.getCanonicalClassName().isEmpty()) {
			return findEntityObjectByClassReplicate(className, cols, siteIdList, startDate, endDate, refresh);
		} else {
			return null;//findEntityObjectByClassImpl(className, cols, siteIdList, startDate, endDate, refresh);
		}
	}

	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)	
	public Object removeReplicate(DevEntityObject obj) throws Throwable {
		Throwable t = null;
		try {
			// entityManager = JpaUtil.getEntityManager();
			// userTransaction.begin();
			// entityManager.flush();
			// obj = entityManager.merge(obj);
			// entityManager.remove(obj);
			if (obj.getEntityClass().getCanonicalClassName() != null) {
				getEntityManager().remove(entityObjectSync(obj));
			}
			// userTransaction.commit();
		} catch (Exception e) {
			t = e;
			// e.printStackTrace();
			obj = null;
		} finally {
			// if (entityManager != null && entityManager.isOpen()) {
			// entityManager.close();
			// }
			if (t != null) {
				throw t;
			}
			return obj;
		}
	}

	@Transactional(isolation=Isolation.READ_COMMITTED,propagation=Propagation.REQUIRED, rollbackFor=Exception.class, readOnly=true)	
	public List<DevEntityObject> findEntityObjectByClassReplicate(DevEntityClass className, List<String> cols, List<Long> siteIdList, Date startDate, Date endDate, boolean refresh) throws Throwable {
		Throwable t = null;
		List<DevEntityObject> objectList = new ArrayList<DevEntityObject>();
		//System.out.println("1");
		Date ini = new Date();
		try {
			if (className == null) {
				throw new IllegalArgumentException("ClassName is empty or null in findEntityObjectByClass.");
			}
			boolean execute = false;
			/**
			 * Consulta para integragração
			 */
			if (className.getCanonicalClassName() != null) {
				// CreateEm();
				Class clazz = Class.forName(className.getCanonicalClassName());
				Class<?> clazzType = Class.forName(className.getCanonicalClassName());
				List<Object> objectListSmall = null;
				List objectListSmallRest = null;
				// objectList = findEntityObjectByClassImpl(className,
				// siteIdList, startDate, endDate, refresh);
				StringBuilder sql = new StringBuilder("select ");
				Field[] fields = clazz.getDeclaredFields();
				String orderBy = "";
				if (cols.isEmpty()) {
					sql.append(" t ");
				} else {
					for (int i = 0; i < fields.length; i++) {
						Field field = fields[i];
						if (field.isAnnotationPresent(Id.class)) {
							if (!cols.contains(field.getName().toLowerCase())) {
								cols.add(field.getName());
								orderBy = "order by t.".concat(field.getName()).concat(" desc");
							}
						}
					}
					for (int i = 0; i < cols.size(); i++) {
						String col = cols.get(i);
						if (i > 0) {
							sql.append(",");
						}
						for (int j = 0; j< fields.length; j++) {
							Field field = fields[j];
							if(field.getName().equalsIgnoreCase(col)){
								col = field.getName();
								break;
							}
						}						
						sql.append("t.");
						sql.append(col);
					}
				}
				sql.append(" from ");
				sql.append(clazz.getSimpleName());
				sql.append(" t ");
				sql.append(orderBy);

				TypedQuery<Object> query = getEntityManager().createQuery(sql.toString(), clazz);
				Date iniQuerySmall1 = new Date();
				query.setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE);
				objectListSmall = query.getResultList();
				if (objectListSmall!=null && !cols.isEmpty()) {
					List<?> objectListSmallTmp = new ArrayList(objectListSmall);
					objectListSmall.clear();
					for (Object object : objectListSmallTmp) {
						Object objectSmall = clazz.newInstance();
						Object[] result = (Object[]) object;
						for (int i = 0; i < cols.size(); i++) {
							for (int j = 0; j < fields.length; j++) {
								Field field = fields[j];
								if (field.getName().equalsIgnoreCase(cols.get(i))) {
									field.setAccessible(true);
									field.set(objectSmall, result[i]);
								}
							}
						}
						objectListSmall.add(objectSmall);
					}
				}
				//objectListSmall =  getEntityManager().createQuery("select t from "+clazz.getSimpleName()+" t").setHint("javax.persistence.cache.retrieveMode", CacheRetrieveMode.USE).getResultList();
				
				// System.err.println("Time QuerySmall 1 "+className.getName()+": "+(new
				// Date().getTime()-iniQuerySmall1.getTime()));
				CriteriaBuilder criteriaBuilderSmall = getEntityManager().getCriteriaBuilder();
				if (refresh) {
					getEntityManager().getEntityManagerFactory().getCache().evict(clazz);
				}
				CriteriaQuery criteriaQuerySmall = criteriaBuilderSmall.createQuery(clazzType);
				Root fromSmall = criteriaQuerySmall.from(clazz);

				// Path<Object> path2Small =
				// fromSmall.join("entityClass").get("name");
				CriteriaQuery<DevEntityObject> selectSmall = criteriaQuerySmall.select(fromSmall);
				selectSmall.orderBy(criteriaBuilderSmall.desc(fromSmall.get("registro")));
				// Predicate where1Small =
				// criteriaBuilderSmall.equal(path2Small, className.getName());
				TypedQuery<DevEntityObject> typedQuerySmall = getEntityManager().createQuery(selectSmall);
				if (refresh) {
					typedQuerySmall.setHint("javax.persistence.cache.storeMode", "REFRESH");
				}
				Date iniQuerySmall = new Date();
				// userTransaction.begin();
				// userTransaction.setTransactionTimeout(60);
				// objectListSmall = typedQuerySmall.getResultList();
				// userTransaction.commit();
				System.err.println("Time QuerySmall 2 " + className.getName() + ": " + (new Date().getTime() - iniQuerySmall1.getTime()));
				// for (Iterator iterator = objectListSmall.iterator();
				// iterator.hasNext();) {
				// Object objRepl = (Object) iterator.next();
				Date iniLoadFields = new Date();
				// Object objRepl2 = clazz.newInstance();
				// Field[] fields = clazz.getDeclaredFields();
				// entityManagerSmall.flush();
				// entityManager.flush();
				entityObjectSyncList(className, siteIdList, objectList, objectListSmall, clazzType);

				System.err.println("Time Load Fields " + className.getName() + ": " + (new Date().getTime() - iniLoadFields.getTime()));

				// }

			}

			// transacao.commit();
		} catch (Throwable e) {
			t = e;
			// e.printStackTrace();
			objectList = null;
			// transacao.rollback();
		} finally {
			// if (entityManager != null && entityManager.isOpen()) {
			// entityManager.close();
			// }
			if (t != null) {
				throw t;
			}
			Date fim = new Date();
			System.err.println("Time Dao " + className.getName() + ": " + (fim.getTime() - ini.getTime()));
			return objectList;
		}
	}

	public void entityObjectSyncPopulate(DevEntityObject obj) {
		try {
			// userTransaction.begin();
			if (obj.getEntityClass().getCanonicalClassName() != null && !obj.getEntityClass().getCanonicalClassName().isEmpty()) {
				entityObjectImportSyncConverter(entityObjectSync(obj), obj, false);
			}
			// userTransaction.commit();
		} catch (Throwable e) {
			try {
				// userTransaction.rollback();
			} catch (Throwable e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}

	//@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)	
	public Object entityObjectSyncImpl(boolean refresh, DevEntityObject obj) throws Throwable {

		Object objRepl = null;
		boolean hasValue = false;
		try {
			if (obj.getEntityClass().getCanonicalClassName() != null) {
				// CreateEm();
				Class clazz = Class.forName(obj.getEntityClass().getCanonicalClassName());
				Field[] fields = clazz.getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if (!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")) {
						field.setAccessible(true);
						DevEntityPropertyValue entPropVal = obj.getPropObj(field.getName().toLowerCase());
						if (entPropVal == null) {
							throw new IllegalArgumentException("Property name: '" + field.getName().toLowerCase() + "' not found in '" + obj.getEntityClass().getName() + "'.");
						}
						Object val = entPropVal.getVal();
						if(entPropVal.isType(DevEntityPropertyValue.FILE)){
							Object file = entPropVal.getPropertyFile();
							if(file!=null){
								if(field.getType().isAssignableFrom(String.class)){
									val = new String((byte[])file);
								}
							}else{
								val = file;
							}
						}
						if (val instanceof List) {
							hasValue = !((List) val).isEmpty();
						} else {
							hasValue = val != null;
						}
						if (objRepl == null && hasValue) {
							objRepl = clazz.newInstance();
						}
						if (objRepl != null) {
							if (entPropVal.getEntityPropertyDescriptor().getPropertyClass() != null) {
								if (entPropVal.getEntityPropertyDescriptor().getPropertyType().equals("ENTITYCHILDREN") && val != null) {
									List<DevEntityObject> entObjList = entPropVal.getPropertyChildrenList();
									List objReplList = new ArrayList();
									for (DevEntityObject devEntityObject : entObjList) {
										objReplList.add(entityObjectSyncImpl(refresh, devEntityObject));
									}
									field.set(objRepl, objReplList);
								}
							} else {
								try {
									if (hasValue) {
										if(field.getType().isAssignableFrom(BigDecimal.class)){											
											val = BigDecimal.valueOf((Double)val);
										}
										field.set(objRepl, val);
									}
								} catch (IllegalArgumentException e) {
									if (val != null) {
										throw new IllegalArgumentException(e);
									}
									// TODO: handle exception
								}
							}
						}
					}

				}
				if (refresh && objRepl != null) {
					objRepl = refresh(objRepl);
				}
			}

		} catch (Throwable e) {
			throw e;
		}
		return objRepl;
	}

	public Object entityObjectSync(DevEntityObject obj) throws Throwable {
		return entityObjectSyncImpl(true, obj);
	}

	public void entityObjectSyncList(DevEntityClass className, List<Long> siteIdList, List<DevEntityObject> objectList, List objectListSmallRest, Class clazz) {
		try {
			Object objRepl2 = clazz.newInstance();
			Field[] fields = clazz.getDeclaredFields();
			for (Iterator iterator2 = objectList.iterator(); iterator2.hasNext();) {
				DevEntityObject obj = (DevEntityObject) iterator2.next();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if (!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")) {
						field.setAccessible(true);
						Object val = obj.getProp(field.getName().toLowerCase());
						try {
							field.set(objRepl2, val);
						} catch (IllegalArgumentException e) {
							if (val != null) {
								throw new IllegalArgumentException(e);
							}
							// TODO: handle exception
						} catch (Throwable e) {
							if (val != null) {
								throw new IllegalArgumentException(e);
							}
							// TODO: handle exception
						}
					}
				}

				// objRepl2 = entityManagerSmall.merge(objRepl2);
				objectListSmallRest.remove(objRepl2);
			}
			if (!objectListSmallRest.isEmpty()) {
				// userTransaction.begin();
				for (Iterator iterator3 = objectListSmallRest.iterator(); iterator3.hasNext();) {
					Object objRepl3 = (Object) iterator3.next();
					DevEntityObject obj = new DevEntityObject(className);
					AdmInstantiatesSite site = new AdmInstantiatesSite();
					site.setInstantiatesSiteId(siteIdList.get(0));
					obj.setInstantiatesSite(site);
					entityObjectImportSyncConverter(objRepl3, obj, true);
					// obj = entityManager.merge(obj);
					objectList.add(obj);
				}
				// userTransaction.commit();
			}
		} catch (Throwable e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public void entityObjectImportSyncConverter(Object objRepl3, DevEntityObject obj, boolean lazy) {
		try {
			if (objRepl3 != null) {
				Field[] fields = objRepl3.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					if (!Modifier.isStatic(field.getModifiers()) && !field.getName().startsWith("_")) {
						field.setAccessible(true);
						Object val = field.get(objRepl3);
						// ((List)objRepl3.getClass().getDeclaredMethod("getProdutos",
						// new Class<?>[]{}).invoke(objRepl3, new
						// Object[]{})).size();//entityManagerSmall.createQuery("select u from Venda u").getResultList();entityManagerSmall.createQuery("select u from Itens001 u where u.numeronf = '000000630001'").getResultList()//((com.algoboss.integration.small.entity.Venda)objRepl3).getProdutos()
						DevEntityPropertyValue entPropVal = obj.getPropObj(field.getName().toLowerCase());
						if (entPropVal.getEntityPropertyDescriptor().getPropertyClass() != null) {
							if (entPropVal.getEntityPropertyDescriptor().getPropertyType().equals("ENTITYCHILDREN") && val instanceof List && !lazy) {
								if (entPropVal.getPropertyChildrenList() != null) {
									entPropVal.getPropertyChildrenList().clear();
								} else {
									entPropVal.setPropertyChildrenList(new ArrayList<DevEntityObject>());
								}
								List valList = (List) val;
								for (Object object : valList) {
									DevEntityObject obj2 = new DevEntityObject(entPropVal.getEntityPropertyDescriptor().getPropertyClass());
									entityObjectImportSyncConverter(object, obj2, lazy);
									entPropVal.getPropertyChildrenList().add(obj2);
								}
							}
						} else {
							try {
								if (val != null) {
									if(entPropVal.isType(DevEntityPropertyValue.FILE)){
										if(field.getType().isAssignableFrom(String.class)){
											val = String.valueOf(val).getBytes();
										}
										entPropVal.setVal(field.getName());
										entPropVal.setPropertyFile((byte[])val);
									}else{
										entPropVal.setVal(val);
									}
								}
							} catch (Throwable e) {
								throw e;
							}
						}
					}
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	public void CreateEm() {

	}
	public void CreateEm2() {
		try {
			// entityManagerSmall.setProperty("javax.persistence.jdbc.driver",
			// "org.firebirdsql.jdbc.FBDriver");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB)");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql:algoboss.zapto.org/3050:D:\\Documents\\@PESSOAL\\ERP\\integração small\\SMALL.GDB");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.user",
			// "SYSDBA");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.password","masterkey");
			// EntityManagerFactory emf =
			// javax.persistence.Persistence.createEntityManagerFactory("SMALLPU",properties);
			// properties.put("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql://localhost:3050/" + DBpath +
			// "?roleName=myrole");
			// EntityManager entityManager = emf.createEntityManager();
			// return entityManager;
			if(!loginBean.getJndiMap().containsKey(loginBean.getInstantiatesSiteContract().getContract().getContractId().toString())){
				SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
				//SimpleNamingContext dd = new SimpleNamingContext();
				//dd.unbind("java:comp/env/jdbc/small");//getEnvironment().containsKey("java:comp/env/jdbc/small");
	            // Create initial context				
				ComboPooledDataSource ds = new ComboPooledDataSource();
				ds.setDriverClass("org.firebirdsql.jdbc.FBDriver"); // etc. for uid, password, url
				if(loginBean.getInstantiatesSiteContract().getContract().getContractId().equals("1733")){
					ds.setJdbcUrl("jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB?encoding=NONE");					
				}else{
					ds.setJdbcUrl("jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALLAgua na Boca_old.GDB?encoding=NONE");	
				}
				ds.setUser("SYSDBA");
				ds.setPassword("masterkey");			
				builder.bind( "java:comp/env/jdbc/small" , ds );
				loginBean.getJndiMap().put(loginBean.getInstantiatesSiteContract().getContract().getContractId().toString(), ds.getJdbcUrl());
				//builder.getCurrentContextBuilder();
				try {
					builder.activate();							
				} catch (java.lang.IllegalStateException e) {
					Logger.getLogger(SmallDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			// return null;
		}
	}

	public void CreateEm3() {
		try {
			// entityManagerSmall.setProperty("javax.persistence.jdbc.driver",
			// "org.firebirdsql.jdbc.FBDriver");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB)");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql:algoboss.zapto.org/3050:D:\\Documents\\@PESSOAL\\ERP\\integração small\\SMALL.GDB");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.user",
			// "SYSDBA");
			// entityManagerSmall.setProperty("javax.persistence.jdbc.password","masterkey");
			// EntityManagerFactory emf =
			// javax.persistence.Persistence.createEntityManagerFactory("SMALLPU",properties);
			// properties.put("javax.persistence.jdbc.url",
			// "jdbc:firebirdsql://localhost:3050/" + DBpath +
			// "?roleName=myrole");
			// EntityManager entityManager = emf.createEntityManager();
			// return entityManager;
			if(!loginBean.getJndiMap().containsKey(loginBean.getInstantiatesSiteContract().getContract().getContractId().toString())){
				//SimpleNamingContextBuilder builder = new SimpleNamingContextBuilder();
				//SimpleNamingContext dd = new SimpleNamingContext();
				//dd.unbind("java:comp/env/jdbc/small");//getEnvironment().containsKey("java:comp/env/jdbc/small");
	            // Create initial context
	            //System.setProperty(Context.INITIAL_CONTEXT_FACTORY,"org.apache.naming.java.javaURLContextFactory");
	            //System.setProperty(Context.URL_PKG_PREFIXES,"org.apache.naming");  				
			      InitialContext ic = new InitialContext();
			      //try {
			    	  //ic.createSubcontext("java:");
			    	  //ic.createSubcontext("java:/comp");
			    	  //ic.createSubcontext("java:/comp/env");
			    	  //ic.createSubcontext("java:/comp/env/jdbc");
				//} catch (javax.naming.NamingException e) {
					// TODO: handle exception
				//}
			    //DataSource ds=  (DataSource)ic.lookupLink("java:/small");				
				ComboPooledDataSource ds = new ComboPooledDataSource("java:/small");
				ds.setDriverClass("org.firebirdsql.jdbc.FBDriver"); // etc. for uid, password, url
				if(loginBean.getInstantiatesSiteContract().getContract().getContractId().equals("1733")){
					ds.setJdbcUrl("jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALL.GDB?encoding=NONE");					
				}else{
					ds.setJdbcUrl("jdbc:firebirdsql:127.0.0.1/3050:C:\\Program Files (x86)\\SmallSoft\\Small Commerce\\SMALLAgua na Boca_old.GDB?encoding=NONE");	
				}
				ds.setUser("SYSDBA");
				ds.setPassword("masterkey");			
				ic.rebind( "java:/small" , ds );
				//loginBean.getJndiMap().put(loginBean.getInstantiatesSiteContract().getContract().getContractId().toString(), ds.getJdbcUrl());
				//builder.getCurrentContextBuilder();
				try {
					//builder.activate();							
				} catch (java.lang.IllegalStateException e) {
					Logger.getLogger(SmallDao.class.getName()).log(Level.SEVERE, null, e.getMessage());
				}
			}
		} catch (Throwable e) {
			e.printStackTrace();
			// return null;
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)	
	public void executeQuery(String str){
		getEntityManager().createNativeQuery(str).executeUpdate();
	}
	
	public void clearEntityManager(){
		try {
			EntityManager emSmall = getEntityManager();
			if(emSmall!=null){
				emSmall.getEntityManagerFactory().getCache().evictAll();			
			}
			DataSourceContextHolder.clearTargetDataSource();
		} catch (javax.persistence.PersistenceException e) {
			// TODO: handle exception
		} 
	}


}
