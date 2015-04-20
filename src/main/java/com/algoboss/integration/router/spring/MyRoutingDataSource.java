package com.algoboss.integration.router.spring;

import javax.inject.Inject;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.algoboss.erp.face.GerLoginBean;

public class MyRoutingDataSource extends AbstractRoutingDataSource {
	@Inject private GerLoginBean loginBean;
	@Override
	protected Object determineCurrentLookupKey() {
		Integer ds = 1;
		if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1733){
			ds = 1;
		}else if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1766){
			ds = 2;
		}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1765){
			ds = 3;			
		}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1767){
			ds = 4;			
		}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1768){
			ds = 5;			
		}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1769){
			ds = 6;			
		}else  if(loginBean.getInstantiatesSiteContract().getContract().getContractId()==1770){
			ds = 7;			
		}else {
			ds = 1;			
		}	
		return ds;
	}
}
