/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.servlet;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.entity.AdmService;
import com.algoboss.erp.entity.DevComponentContainer;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.face.AdmAlgodevBean;
import com.algoboss.erp.face.GerLoginBean;
import com.algoboss.erp.util.AlgodevUtil;
import com.algoboss.erp.util.ManualCDILookup;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener.
 *
 * @author Agnaldo
 */
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
        System.out.println("CONTEXT DESTROYED!!!!!!!!!! " + new Date());
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("SESSION CREATED!!!!!!!!!! " + new Date());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        try {
            if (loginBean.isUserLogged()) {
                loginBean.removeActiveSession();
            }
        } catch (Exception e) {
            //e.printStackTrace();
        } finally {
        }

        System.out.println("SESSION DESTROYED!!!!!!!!!! " + new Date());
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
