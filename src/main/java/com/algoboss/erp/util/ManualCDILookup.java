/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.util;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author Agnaldo
 */
public abstract class ManualCDILookup {

    public <T> T getFacadeWithJNDI(Class<T> classToFind) {
        BeanManager bm = getBeanManager();
        Bean<T> bean = (Bean<T>) bm.getBeans(classToFind).iterator().next();
        CreationalContext<T> ctx = bm.createCreationalContext(bean);
        T dao = (T) bm.getReference(bean, classToFind, ctx); // this could be inlined, but intentionally left this way
        return dao;
    }

    public BeanManager getBeanManager()
    {
        try{
            InitialContext initialContext = new InitialContext();
            return (BeanManager) initialContext.lookup("java:comp/BeanManager");
        }
        catch (NamingException e) {
            e.printStackTrace();
            return null;
        }
    }


}
