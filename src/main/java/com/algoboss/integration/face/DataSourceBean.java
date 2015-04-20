/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.integration.face;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.algoboss.erp.face.GerLoginBean;
import com.algoboss.erp.util.ManualCDILookup;

/**
 *
 * @author Agnaldo
 */
@Named
@SessionScoped
public class DataSourceBean extends ManualCDILookup implements Serializable {

    private static final long serialVersionUID = 1L;
    private String url = "jdbc:firebirdsql:127.0.0.1/3050:D:/Documents/@PESSOAL/ERP/integração small/SMALL.GDB?encoding=WIN1252";//"jdbc:firebirdsql:127.0.0.1/3050:C:/Program Files (x86)/SmallSoft/Small Commerce/SMALL.GDB";
    private GerLoginBean login;
    //private List<Theme> advancedThemes;  
    private String other;

	public String getUrl() {
		setUrl("");
		return url;
	}

	public void setUrl(String url) {
		login = getFacadeWithJNDI(GerLoginBean.class);
		try {
			if(login!=null && login.getInstantiatesSiteContract()!=null && login.getInstantiatesSiteContract().getCompany()!=null){
				url = login.getInstantiatesSiteContract().getCompany().getDataSource();
			}		
			if(url!=null){
				this.url = url;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

    //private GuestPreferences gp;  
    //public void setGp(GuestPreferences gp) {  
    //  this.gp = gp;  
    //}  

}
