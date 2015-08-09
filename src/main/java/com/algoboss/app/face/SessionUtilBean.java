/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.face;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import com.algoboss.app.entity.DevEntityClass;
import com.algoboss.app.entity.DevEntityObject;
import com.algoboss.app.entity.DevRequirement;
import com.algoboss.app.face.AdmAlgoappBean.BeanList;
import com.algoboss.core.face.GenericBean;

/**
 *
 * @author Agnaldo
 */
public class SessionUtilBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    protected transient Map<String, List<UIComponent>> elementsContainerMap = new HashMap();
    protected transient Map<Long,Map<String, List<UIComponent>>> elementsContainerMapByReq;    
    protected static Map<String,Map<Long,Map<String, List<UIComponent>>>> elementsContainerMapByReqSession;  
    private static Map<String, HttpSession> sessionMap = new HashMap<String,HttpSession>();
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

	}
	
	public Map<String, Map<Long,Map<String,List<UIComponent>>>> getElementsContainerMapByReqSession(){
		if(elementsContainerMapByReqSession == null){
			elementsContainerMapByReqSession = new HashMap<String, Map<Long,Map<String,List<UIComponent>>>>();
		}
		if(!elementsContainerMapByReqSession.containsKey(getId())){
			elementsContainerMapByReqSession.put(getId(), elementsContainerMapByReq);			
		}		
		return elementsContainerMapByReqSession;
	}
	
	public Map<Long, Map<String, List<UIComponent>>> getElementsContainerMapByReq() {
		return getElementsContainerMapByReqSession().get(getId());
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
	
	public static void clear(){
		try {
			if(SessionUtilBean.elementsContainerMapByReqSession != null){
				Object rm = SessionUtilBean.elementsContainerMapByReqSession.remove(SessionUtilBean.getId());
				if(rm == null){
					Map<String, HttpSession> newSessionMap = new HashMap<String, HttpSession>();
					for(Entry<String, HttpSession> session: sessionMap.entrySet()){
						try {
							  session.getValue().getCreationTime();
							  newSessionMap.put(session.getKey(),session.getValue());
							} catch (IllegalStateException ise) {
								SessionUtilBean.elementsContainerMapByReqSession.remove(session.getKey());
								sessionMap.put(session.getKey(), null);
							}						
					}
					sessionMap = newSessionMap;
				}else{
					sessionMap.remove(SessionUtilBean.getId());
				}
				Logger.getLogger(SessionUtilBean.class.getName()).log(Level.SEVERE,"SESSIONS ACTIVE: "+sessionMap.size());
			}		
		} catch (Exception e) {
			Logger.getLogger(SessionUtilBean.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
    public static String getId(){
        return get();
    }
    
    private static final ThreadLocal<String> threadId =
            new ThreadLocal<String>() {
                @Override protected String initialValue() {
                	return getSessionId(FacesContext.getCurrentInstance());
            }
        };

    // Returns the current thread's unique ID, assigning it if necessary
    public static String get() {
        return threadId.get();
    }
    
    public static void set(String sessionId) {
        threadId.set(sessionId);
    }     
    
    public static void set(FacesContext fc){
    	set(getSessionId(fc));                	
    }
    private static String getSessionId(FacesContext fc){
    	if(fc != null){
    		ExternalContext context = fc.getExternalContext();
    		HttpSession session = (HttpSession) context.getSession(false);
    		String id = session.getId();
    		sessionMap.put(id, session);
    		return session.getId();                	
    	}
        return "";    	
    }
	
}
