/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.algoboss.core.dao.AdmContractDao;
import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.core.face.SecUserBean;

/**
 * @author Agnaldo
 */
@WebFilter(filterName = "GerAuthFilter", urlPatterns = {"/*"})
public class GerAuthFilter implements Filter {

    @Inject
    private GerLoginBean loginBean;
    @Inject
    private SecUserBean userBean;

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String url = req.getRequestURL().toString();
        try {
            // chain...
            //chain.doFilter(request, response);
        	if(url.contains("/update")){
        		 chain.doFilter(request, response); 
        		 return;
        	}
            String domain = req.getServerName();
            String newUserParam = req.getParameter("newUserForm");
            String uuidParam = req.getParameter("uuid");
            String contractNewUser = req.getParameter("contractNewUser");
            
            if (uuidParam != null && !uuidParam.isEmpty()) {
                doGetFile(req, res, uuidParam);
                //getFile(uuidParam);
                //System.out.println("Recebeu parâmetro uuid:" + uuidParam);
            } else {
                if (newUserParam != null && !newUserParam.isEmpty()) {
                    loginBean.setNewUserForm(true);
                }
                if (contractNewUser != null && !contractNewUser.isEmpty()) {
                    AdmContract contract = AdmContractDao.findById(Long.valueOf(contractNewUser));
                    if (contract != null) {
                        userBean.setBean(new SecUser());
                        loginBean.getUser().setContract(contract);
                    }
                }
                //System.out.println("domain:"+domain);
                String loginUrl = domain.contains("www") ? req.getContextPath() + "/f/home.xhtml" : req.getContextPath() + "/f/login.xhtml";
                if (!req.isRequestedSessionIdValid() && isAjax(req)) {
                    response.getWriter().print(xmlPartialRedirectToPage(req, "/"));
                    response.flushBuffer();
                    //return;
                } else if (chain != null && request != null && response != null && !url.contains("views") && (loginBean.isUserLogged() || url.contains("/login.xhtml") || url.contains("/home.xhtml") || url.contains("/contractNewUser.xhtml") || (!url.contains(".xhtml") && !url.endsWith("/")))) {
                    String uri = req.getRequestURI();
                    if ("GET".equalsIgnoreCase(req.getMethod()) && uri.contains(ResourceHandler.RESOURCE_IDENTIFIER)) {
                        //res.setDateHeader("Expires", System.currentTimeMillis() + 2419200000L); // 1 month in future.
                        //res.setDateHeader("Last-Modified", System.currentTimeMillis() - 2419200000L); // 1 month in past.
                        //res.setHeader("Cache-Control", "public"); // Secure caching
                    }
                    if(url.contains("/login.xhtml") && req.getParameterMap().isEmpty()){
                    	if(!req.getSession().isNew()){
                    		req.getSession().invalidate();
                    		//req.getSession(true);                    		
                    	}
                    }
                    chain.doFilter(request, response);                    	
                } else {
                	if(false && url.contains("/index.xhtml")){
                        response.getWriter().print(xmlPartialRedirectToPage(req, "/"));
                        response.flushBuffer();
                	}else{
                		req.getSession().invalidate();                		
                		res.sendRedirect(loginUrl);                		
                	}
                    //chain.doFilter(request, response);
                }
            }


        } catch (Throwable t) {
        	FacesContext fc = FacesContext.getCurrentInstance();
        	if(fc != null){
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ocorreu uma falha no processamento. Favor repetir a operação e caso o problema persista informe ao administrador do sistema.", "");
				FacesContext.getCurrentInstance().addMessage(null, msg);
        	}
			
            // redirect to error page
            //HttpServletRequest request = (HttpServletRequest) req;
            //req.getSession().setAttribute("lastException", t);
            //req.getSession().setAttribute("lastExceptionUniqueId", t.hashCode());

            //log.error("EXCEPTION unique id: " + e.hashCode(), e);

            //HttpServletResponse response = (HttpServletResponse) resp;

            if (!isAjax(req)) {
                //res.sendRedirect(req.getContextPath() + req.getServletPath() + "/error");
            } else {
                // let's leverage jsf2 partial response
                //res.getWriter().print(xmlPartialRedirectToPage(req, "/error"));
                //res.flushBuffer();
            }
            t.printStackTrace();
        }
    }
    private String xmlPartialMsgReconect(HttpServletRequest request, String page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\"><script>alert('oi')</script></html>");
        return sb.toString();
    }
    
    private String xmlPartialRedirectToPage(HttpServletRequest request, String page) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version='1.0' encoding='UTF-8'?>");
        sb.append("<partial-response><redirect url=\"").append(request.getContextPath()).append(request.getServletPath()).append(page).append("\"/></partial-response>");
        return sb.toString();
    }

    private boolean isAjax(HttpServletRequest request) {
        String headerReq = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equals(headerReq);
    }

    private void doGetFile(HttpServletRequest request, HttpServletResponse response, String id) {
        //HttpSession session = (HttpSession) request.getSession(false);
        Map<String, HashMap<String, Object>> fileDataBaseMap = null;
        if (!loginBean.getFileDataBaseMap().isEmpty()) {

            try {
                fileDataBaseMap = loginBean.getFileDataBaseMap();//(HashMap<String, HashMap<String, Object>>) session.getAttribute("fileDataBaseMap");
                HashMap<String, Object> propMap = fileDataBaseMap.get(id);
                InputStream is = (InputStream) propMap.get("content");
                String fileName = String.valueOf(propMap.get("fileName"));
                //System.out.println("FileName: " + fileName);
                response.reset();
                response.setHeader("Content-Type", request.getServletContext().getMimeType(fileName));
                response.setHeader("Content-Length", String.valueOf(is.available()));
                response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "Mon, 8 Aug 1980 10:00:00 GMT");

                BufferedInputStream input = null;
                BufferedOutputStream output = null;

                try {
                    input = new BufferedInputStream(is);
                    output = new BufferedOutputStream(response.getOutputStream());
                    byte[] buffer = new byte[8192];
                    int length;
                    while ((length = input.read(buffer)) > 0) {
                        output.write(buffer, 0, length);
                    }
                    //response.resetBuffer();
                    response.flushBuffer();
                } catch (IOException ex) {
                    Logger.getLogger(GerAuthFilter.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (output != null) {
                        try {
                            output.close();
                        } catch (IOException logOrIgnore) {
                        }
                    }
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException logOrIgnore) {
                        }
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(GerAuthFilter.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            System.out.println("Não encontrou fileDataBaseMap na sessão.");
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void destroy() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
