/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.entity;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.erp.util.ManualCDILookup;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevComponentContainer",
    query = "select u from DevComponentContainer u")
})
@Cacheable(true)
@Entity
@Table(name="dev_component_container")
public class DevComponentContainer extends ManualCDILookup implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevComponentContainer", sequenceName = "sequence_dev_component_container", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevComponentContainer")
    @Column(name = "component_container_id")
    private Long componentContainerId;
    private String name;
    //@OneToMany(cascade= CascadeType.REMOVE,orphanRemoval=true)
    //@OrderBy
    //@JoinColumn(name="component_container_id", updatable=false, insertable=false)
    @Transient
    private List<DevPrototypeComponentChildren> prototypeComponentChildrenList;
    @Column(name = "component_schema")
    private String componentSchema;

    public Long getComponentContainerId() {
        return componentContainerId;
    }

    public void setComponentContainerId(Long componentContainerId) {
        this.componentContainerId = componentContainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DevPrototypeComponentChildren> getPrototypeComponentChildrenList() {
    	if(getComponentSchema()!=null){
    		if(prototypeComponentChildrenList == null){
    			schemaToComponent();    			
    		}
    		//throw new UnsupportedOperationException();
    		return prototypeComponentChildrenList;
    	}else{
    		componentToSchema();
    		return prototypeComponentChildrenList;
    	}
    }

    public void setPrototypeComponentChildrenList(List<DevPrototypeComponentChildren> prototypeComponentChildrenList) {
        this.prototypeComponentChildrenList = prototypeComponentChildrenList;
        componentToSchema();
    }    
    
    public String getComponentSchema() {
		return componentSchema;
	}

	public void setComponentSchema(String componentSchema) {
		this.componentSchema = componentSchema;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevComponentContainer other = (DevComponentContainer) obj;
        if (this.componentContainerId != other.componentContainerId && (this.componentContainerId == null || !this.componentContainerId.equals(other.componentContainerId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + (this.componentContainerId != null ? this.componentContainerId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevComponentContainer{" + "componentContainerId=" + componentContainerId + ", name=" + name + ", prototypeComponentChildrenList=" + prototypeComponentChildrenList + '}';
    }
    
    private void schemaToComponent(){
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
		Type listType = new TypeToken<ArrayList<DevPrototypeComponentChildren>>() {}.getType();
		prototypeComponentChildrenList = gson.fromJson(getComponentSchema(), listType);    	
    }
        
    private void componentToSchema(){
    	Gson gson = new GsonBuilder().create();
    	if(this.prototypeComponentChildrenList!=null){
	    	if(this.prototypeComponentChildrenList.isEmpty()){
	    		this.prototypeComponentChildrenList = getFacadeWithJNDI(BaseDao.class).getEntityManager().createQuery("select t from DevPrototypeComponentChildren t where t.componentContainerId = ?1",DevPrototypeComponentChildren.class).setParameter(1, componentContainerId).getResultList();
	    		setComponentSchema(gson.toJson(prototypeComponentChildrenList, List.class));  
	    		getFacadeWithJNDI(BaseDao.class).getEntityManager().createQuery("delete t from DevPrototypeComponentChildren t where t.componentContainerId = ?1",DevPrototypeComponentChildren.class).setParameter(1, componentContainerId).executeUpdate();
	    		try {
	    			getFacadeWithJNDI(BaseDao.class).save(this);
	    		} catch (Throwable e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    		} 
	    	}else{
	    		setComponentSchema(gson.toJson(prototypeComponentChildrenList, List.class));  
	    	}	    	
    	}
    }
    
	@PrePersist
    public void prePersist(){
		componentToSchema();
    }    
    @PreUpdate
    public void preUpdate(){
    	componentToSchema();
    }    
      
}
