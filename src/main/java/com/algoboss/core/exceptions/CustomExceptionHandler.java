/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.exceptions;

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
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algoboss.core.face.GerLoginBean;

/**
 *
 * @author Agnaldo
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private ExceptionHandler wrapped;
    @Inject GerLoginBean loginBean;
    
    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    @Override
    public void handle() throws FacesException {
    	loadLogin();
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
                
                

                try {
                    if (fc.getExternalContext() != null && (throwable.getMessage()==null || "null source".contains(throwable.getMessage()))) {
                    	throwable.printStackTrace();
                        //fc.getExternalContext().redirect("/f/error.xhtml");
                    	HttpServletRequest req = (HttpServletRequest) fc.getExternalContext().getRequest();
                    	//req.getSession().invalidate();
                    	//req.getSession(true);
                    	//navigationHandler.handleNavigation(fc, null, "/f/login.xhtml?faces-redirect=true");
                    	HttpServletResponse res = (HttpServletResponse) fc.getExternalContext().getResponse();
                    	res.sendRedirect(req.getContextPath() + "/f/login.xhtml");
                    	return;
                    }else{
                    	navigationHandler.handleNavigation(fc, null, "error?faces-redirect=true");
                    }
                } catch (Exception ex) {
                    Logger.getLogger(CustomExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
                fc.renderResponse();
            }catch(javax.enterprise.context.ContextNotActiveException ex){
            	//FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(FacesContext.getCurrentInstance().getELContext(), null, "#{gerLoginBean}");
            	
                //ExternalContext contextExt = FacesContext.getCurrentInstance().getExternalContext();
                //contextExt.getFlash().clear();
                //FacesContext.getCurrentInstance().getApplication().getViewHandler().initView(FacesContext.getCurrentInstance());
                //Util.getStateManager(FacesContext.getCurrentInstance()).
                //FacesContext.getCurrentInstance().getViewRoot().getChildren().clear();
            	//throw new IllegalStateException("javax.enterprise.context.ContextNotActiveException in Custom Exception.",ex);
            	//throw new IllegalStateException("javax.enterprise.context.ContextNotActiveException in Custom Exception.",ex);
            }catch (Throwable ex) {            
                Logger.getLogger(CustomExceptionHandler.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
            	//loginBean.doLogout();
                iterator.remove();
            }
        }

        // Let the parent handle the rest
        getWrapped().handle();
    }
    
    private void loadLogin(){
    	loginBean = FacesContext.getCurrentInstance().getApplication().evaluateExpressionGet(FacesContext.getCurrentInstance(), "#{gerLoginBean}", GerLoginBean.class);
    }
}
