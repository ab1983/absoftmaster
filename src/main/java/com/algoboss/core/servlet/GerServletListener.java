/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.servlet;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.inject.Inject;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.algoboss.app.entity.DevComponentContainer;
import com.algoboss.app.entity.DevRequirement;
import com.algoboss.app.face.SessionUtilBean;
import com.algoboss.app.util.AlgodevUtil;
import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.core.util.AlgoUtil;
import com.algoboss.erp.util.ManualCDILookup;
import com.algoboss.integration.small.dao.DataSourceContextHolder;

/**
 * Web application lifecycle listener.
 *
 * @author Agnaldo
 */
@WebListener
public class GerServletListener extends ManualCDILookup implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    @Inject
    private BaseDao baseDao;
    @Inject
    private GerLoginBean loginBean;
     
        
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //baseDao = getFacadeWithJNDI(BaseDao.class);
        System.out.println("CONTEXT INITIALIZED!!!!!!!!!! " + new Date());
        baseDao.resetCurrentLicense();
        		 
        //loginBean = getFacadeWithJNDI(GerLoginBean.class);
        Thread t = new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(1000);
                    List<DevRequirement> requirementList = (List<DevRequirement>) (Object) baseDao.findAll("findAllDevRequirement");
                    for (DevRequirement requirement : requirementList) {
                        if (requirement != null) {
                            List<DevComponentContainer> componentContainerList = requirement.getComponentContainerList();
                            for (DevComponentContainer devComponentContainer : componentContainerList) {
                                AlgodevUtil.generateComponentList(devComponentContainer);
                            }
                        }
                    }
                    System.out.println("CONTEXT LOADED!!!!!!!!!! " + new Date());
                } catch (InterruptedException ex) {
                    Logger.getLogger(GerServletListener.class.getName()).log(Level.SEVERE, null, ex);
                } catch (Throwable e) {
                    e.printStackTrace();
                }

            }
        });
        //t.start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    	//baseDao.clearEntityManager();
        System.out.println("CONTEXT DESTROYED!!!!!!!!!! " + new Date());
        DataSourceContextHolder.clearTargetDataSourceMap();
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContextSmall.xml");
        ctx.stop();
        ctx.close();
        ctx.registerShutdownHook();  
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {   	
    	String id = se.getSession().getId();
        System.out.println("SESSION CREATED!!!!!!!!!! " + new Date() + " ID:"+id);
        SessionUtilBean.set(id);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
    	String id = se.getSession().getId();
        try {
            if (loginBean.isUserLogged()) {
                loginBean.removeActiveSession();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
        }
        System.out.println("SESSION DESTROYED!!!!!!!!!! " + new Date()+ " ID:"+id);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        //System.out.println("ATTRIBUTE ADDED!!!");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        //System.out.println("ATTRIBUTE REMOVED!!!");
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        //System.out.println("ATTRIBUTE REPLACED!!!");
        //throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
