/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.exceptions;

import java.io.IOException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.FacesException;
import javax.faces.application.NavigationHandler;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author Agnaldo
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
        Iterator iterator = getUnhandledExceptionQueuedEvents().iterator();

        while (iterator.hasNext()) {
            ExceptionQueuedEvent event = (ExceptionQueuedEvent) iterator.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();

            Throwable throwable = context.getException();

            FacesContext fc = FacesContext.getCurrentInstance();

            try {
                Flash flash = fc.getExternalContext().getFlash();

                // Put the exception in the flash scope to be displayed in the error 
                // page if necessary ...
                flash.put("errorDetails", throwable.getMessage());

                System.err.println("Error Message: " + throwable.getMessage());

                NavigationHandler navigationHandler = fc.getApplication().getNavigationHandler();
                
                navigationHandler.handleNavigation(fc, null, "error?faces-redirect=true");

                try {
                    if (throwable.getMessage()==null && fc.getExternalContext() != null) {
                        //fc.getExternalContext().redirect("/f/error.xhtml");
                        navigationHandler.handleNavigation(fc, null, "/f/login.xhtml?faces-redirect=true");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CustomExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                fc.renderResponse();
            }catch(javax.enterprise.context.ContextNotActiveException ex){
            	throw new IllegalStateException("javax.enterprise.context.ContextNotActiveException in Custom Exception.");
            }catch (Throwable ex) {            
                Logger.getLogger(CustomExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                iterator.remove();
            }
        }

        // Let the parent handle the rest
        getWrapped().handle();
    }
}
