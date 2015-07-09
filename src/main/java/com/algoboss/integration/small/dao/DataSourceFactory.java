package com.algoboss.integration.small.dao;


	import java.util.HashMap;
import java.util.Map;

	import javax.sql.DataSource;

	import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.util.Assert;

	//import com.judoscript.JudoEngine;

	/**
	 * This class allows programs to dynamically change the dataSource 
	 * they use. For example, Spring can be configured to know about
	 * three beans - defaultDatasource, testDataSource, and 
	 * prodDataSource. During development,the defaultDataSource is used 
	 * because the getDataSource() method is called without any other 
	 * specifications. When the code is deployed to the test environment, that
	 * process creates a system property called 'datasource.spring.beanname' 
	 * set to testDataSource. Spring's HotSwappableTargetSource
	 * feature is used to dynamically switch to the testDataSource bean 
	 * previously defined in the Spring configuration files.
	 * 
	 * 1. Read system property (datasource.script) to get judoscript to execute.
	 * 
	 * 2. Read system property (datasource.script.filename) to get name 
	 * of judoscript file to execute.
	 * 
	 * 3. Read system property (datasource.spring.beanname) to get name of 
	 * Spring bean to load.
	 */
	public class DataSourceFactory {

	 private Log log = LogFactory.getLog(getClass());

	 private ApplicationContext ctx = null;

	 public DataSourceFactory(ApplicationContext _ctx) {
	  super();
	  this.ctx = _ctx;
	 }

	 public DataSource getDataSource() {
	  DataSource realDataSource = null;

	  String dataSourceScript = System.getProperty("datasource.script");
	  String judoScriptFileName = System.getProperty("datasource.script.filename");
	  String springBeanName = System.getProperty("datasource.spring.beanname");
	  if (dataSourceScript != null) {
	   realDataSource = helperFromJudoScriptString(dataSourceScript);
	   log.debug("defining DataSource from JudoScript string, via system property.");
	  } else if (judoScriptFileName != null) {
	   realDataSource = helperFromJudoScriptFile(judoScriptFileName);
	   log.debug("defining DataSource from JudoScript script, via system property, named [" + judoScriptFileName + "].");
	  } else if (springBeanName != null) {
	   realDataSource = (DataSource) ctx.getBean(springBeanName);
	   log.debug("defining DataSource from Spring bean, via system property, named [" + springBeanName + "].");
	  } else {
	   realDataSource = (DataSource) ctx.getBean("defaultDataSource");
	   log.debug("defining DataSource from default Spring bean [defaultDataSource] in Spring configuration.");
	  }
	  return swapToDataSource(realDataSource);
	 }

	 public DataSource getDataSourceFromSpringBean(final String name) {
	  DataSource realDataSource = (DataSource) ctx.getBean(name);
	  log.debug("defining DataSource from Spring bean named [" + name + "].");
	  return swapToDataSource(realDataSource);
	 }
	 
	 public DataSource getDataSourceFromJudoScriptString(final String script) {
	  DataSource realDataSource = helperFromJudoScriptString(script);
	  log.debug("defining DataSource from JudoScript string.");
	  return swapToDataSource(realDataSource);
	 }
	 
	 public DataSource getDataSourceFromJudoScriptFile(final String filename) {
	  DataSource realDataSource = helperFromJudoScriptFile(filename);
	  log.debug("defining DataSource from JudoScript file called [" + filename + "].");
	  return swapToDataSource(realDataSource);
	 }
	 
	 public DataSource getDataSourceFromDbcpBasicDataSource(final String driverClassName, final String url, final String username, final String password, String validationQuery) {
	  BasicDataSource realDataSource = new BasicDataSource();
	  realDataSource.setDriverClassName(driverClassName);
	  realDataSource.setUrl(url);
	  realDataSource.setUsername(username);
	  realDataSource.setPassword(password);
	  realDataSource.setInitialSize(5);
	  realDataSource.setMinIdle(5);
	  realDataSource.setMaxIdle(10);
	  realDataSource.setMaxTotal(10);
	  realDataSource.setPoolPreparedStatements(true);
	  realDataSource.setValidationQuery(validationQuery);
	  realDataSource.setValidationQueryTimeout(10);
	  realDataSource.setTestOnCreate(true);
	  realDataSource.setMaxWaitMillis(5000);
	  realDataSource.setDefaultQueryTimeout(30);
	  realDataSource.setMinEvictableIdleTimeMillis(60000);
	  realDataSource.setRemoveAbandonedTimeout(30);
	  realDataSource.setRemoveAbandonedOnBorrow(true);
	  realDataSource.setRemoveAbandonedOnMaintenance(true);
	  realDataSource.setNumTestsPerEvictionRun(2);
	  return realDataSource;//swapToDataSource(realDataSource);
	 }
	 
	 public DataSource swapToDataSource(final DataSource realDataSource) {
	  Assert.notNull(realDataSource, "Error defining the real dataSource.");
	  HotSwappableTargetSource swapper = (HotSwappableTargetSource) ctx.getBean("swappableDataSource");
	  swapper.swap(realDataSource);
	  return (DataSource) ctx.getBean("dataSource");
	 }

	 private DataSource helperFromJudoScriptFile(final String filename) {
	  Map sysprops = new HashMap();
	  String[] jeArgs = {};
	  //JudoEngine je = null;
	  DataSource rv = null;

	  // define the datasource via judoscript.
	  try {
	   //je = new JudoEngine();
	   //je.putBean("root", new HashMap());
	   //je.runScript(filename, jeArgs, sysprops);
	   //Map root = (Map) je.getBean("root");
	   //rv = (DataSource) root.get("dataSource");
	  } catch (Throwable e) {
	   throw new RuntimeException(e);
	  }

	  return rv;
	 }

	 private DataSource helperFromJudoScriptString(final String script) {
	  Map sysprops = new HashMap();
	  String[] jeArgs = {};
	  //JudoEngine je = null;
	  DataSource rv = null;

	  // define the datasource via judoscript.
	  try {
	   //je = new JudoEngine();
	   //je.putBean("root", new HashMap());
	   //je.runCode(script, jeArgs, sysprops);
	   //Map root = (Map) je.getBean("root");
	   //rv = (DataSource) root.get("dataSource");
	  } catch (Throwable e) {
	   throw new RuntimeException(e);
	  }

	  return rv;
	 }
}
