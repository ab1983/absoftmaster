/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.servlet;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Agnaldo
 */
@WebFilter(filterName = "HttpsFilter", urlPatterns = {"/*"})
public class HttpsFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        String prot = httpRequest.getScheme();
        if (!httpRequest.isSecure() && !prot.equals("https") && false) {
            StringBuilder newUrl = new StringBuilder("https://");
            newUrl.append(httpRequest.getServerName());
            if (httpRequest.getRequestURI() != null) {
                newUrl.append(httpRequest.getRequestURI());
            }
            if (httpRequest.getQueryString() != null) {
                newUrl.append("?").append(httpRequest.getQueryString());
            }
            HttpServletResponse httpResponse = (HttpServletResponse)response;
            httpResponse.sendRedirect(newUrl.toString());
        } else {
            if (chain != null) {
            	try {					
            		chain.doFilter(request, response);
				} catch (Throwable e) {
					e.printStackTrace();
					// TODO: handle exception
				}
            }
        }
    }

    @Override
    public void destroy() { }
}
