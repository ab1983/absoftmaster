/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.face;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.inject.Named;

import com.algoboss.erp.entity.DevEntityClass;
import com.algoboss.erp.entity.DevEntityObject;
import com.algoboss.erp.entity.DevRequirement;
import com.algoboss.erp.face.AdmAlgoappBean.BeanList;

/**
 *
 * @author Agnaldo
 */
public class SessionUtilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected transient Map<String, List<UIComponent>> elementsContainerMap = new HashMap();
    protected transient Map<Long,Map<String, List<UIComponent>>> elementsContainerMapByReq;    
    protected static Map<Long,Map<Long,Map<String, List<UIComponent>>>> elementsContainerMapByReqSession;    
	protected DevEntityClass entity;
	protected DevRequirement requirement;
	protected String containerPage = "form";
	protected Map<String, List<DevEntityObject>> beanListMap = new HashMap();
	protected Map<String, BeanList<DevEntityObject>> beanListFilteredMap = new HashMap();	
	protected DevEntityObject bean;
	protected GenericBean appBean;

	
	
	public SessionUtilBean() {
		super();
		if(elementsContainerMapByReq == null){
			elementsContainerMapByReq = new HashMap();	
		}
		if(elementsContainerMapByReqSession == null){
			elementsContainerMapByReqSession = new HashMap<Long, Map<Long,Map<String,List<UIComponent>>>>();
		}
		if(!elementsContainerMapByReqSession.containsKey(Thread.currentThread().getId())){
			elementsContainerMapByReqSession.put(Thread.currentThread().getId(), elementsContainerMapByReq);			
		}
	}
	
	public Map<Long, Map<String, List<UIComponent>>> getElementsContainerMapByReq() {
		return elementsContainerMapByReqSession.get(Thread.currentThread().getId());
	}

	public void setElementsContainerMapByReq(Map<Long, Map<String, List<UIComponent>>> elementsContainerMapByReq) {
		this.elementsContainerMapByReq = elementsContainerMapByReq;
	}

	public Map<String, List<UIComponent>> getElementsContainerMap(DevRequirement requirement) {
		if(requirement!=null && requirement.getRequirementId()!=null){
			this.requirement = requirement;
			Map<String, List<UIComponent>> elementsContainerMap = getElementsContainerMapByReq().get(requirement.getRequirementId());
			if(elementsContainerMap!=null){
				this.elementsContainerMap = elementsContainerMap;
			}else{
				setElementsContainerMap(requirement,new HashMap<String, List<UIComponent>>());
			}
		}
		return elementsContainerMap;
	}
	public void setElementsContainerMap(DevRequirement requirement,Map<String, List<UIComponent>> elementsContainerMap) {
		if(requirement!=null && requirement.getRequirementId()!=null && elementsContainerMap!=null){
			this.requirement = requirement;
			getElementsContainerMapByReq().put(requirement.getRequirementId(), elementsContainerMap);		
		}		
		this.elementsContainerMap = elementsContainerMap;
	}
	public DevEntityClass getEntity() {
		return entity;
	}
	public void setEntity(DevEntityClass entity) {
		this.entity = entity;
	}
	public DevRequirement getRequirement() {
		return requirement;
	}
	public void setRequirement(DevRequirement requirement) {
		this.requirement = requirement;
	}
	public String getContainerPage() {
		return containerPage;
	}
	public void setContainerPage(String containerPage) {
		this.containerPage = containerPage;
	}
	public Map<String, List<DevEntityObject>> getBeanListMap() {
		return beanListMap;
	}
	public void setBeanListMap(Map<String, List<DevEntityObject>> beanListMap) {
		this.beanListMap = beanListMap;
	}
	public Map<String, BeanList<DevEntityObject>> getBeanListFilteredMap() {
		return beanListFilteredMap;
	}
	public void setBeanListFilteredMap(Map<String, BeanList<DevEntityObject>> beanListFilteredMap) {
		this.beanListFilteredMap = beanListFilteredMap;
	}
	public DevEntityObject getBean() {
		return bean;
	}
	public void setBean(DevEntityObject bean) {
		this.bean = bean;
	}
	public GenericBean getAppBean() {
		return appBean;
	}
	public void setAppBean(GenericBean appBean) {
		this.appBean = appBean;
	}		
	
    
	
}
