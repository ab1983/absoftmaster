/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.util.AlgoUtil;
import com.algoboss.erp.util.ManualCDILookup;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevEntityClass",
            query = "select u from DevEntityClass u")
})
@Cacheable(true)
@Entity
@Table(name = "dev_entity_class")
public class DevEntityClass extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityClass", sequenceName = "sequence_dev_entity_class", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityClass")
    @Column(name = "entity_class_id")
    private Long entityClassId;
    private String label;
    private String name;
    @Column(name = "canonical_class_name")
    private String canonicalClassName;    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    //@OrderBy
    @JoinColumn(name = "entity_class_id")
    private List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = new ArrayList<DevEntityPropertyDescriptor>();
    @OneToMany(fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "entity_class_id")
    private List<DevEntityObject> entityObjectList;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "entity_class_id")
    private List<DevEntityClassConfig> entityClassConfigList;     
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_class_id")
    private List<DevEntityPropertyDescriptor> entityPropertyDescriptorParentList;    

    public Long getEntityClassId() {
        return entityClassId;
    }

    public void setEntityClassId(Long entityClassId) {
        this.entityClassId = entityClassId;
    }

    public List<DevEntityObject> getEntityObjectList() {
        return entityObjectList;
    }

    public void setEntityObjectList(List<DevEntityObject> entityObjectList) {
        this.entityObjectList = entityObjectList;
    }

    public List<DevEntityPropertyDescriptor> getEntityPropertyDescriptorList() {
        return entityPropertyDescriptorList;
    }

    public void setEntityPropertyDescriptorList(List<DevEntityPropertyDescriptor> entityPropertyDescriptorList) {
        this.entityPropertyDescriptorList = entityPropertyDescriptorList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
        setName(label);
    }

    public String getName() {
        return name;
    } 

    public void setName(String name) {
        if (this.name == null && name != null && !name.isEmpty()) {
        	updateProperty();
        	String classPreffix = "";
        	if(instantiatesSite!=null && instantiatesSite.getInstantiatesSiteId()!=null){
        		classPreffix = instantiatesSite.getInstantiatesSiteId()+"_";
        	}
            this.name = getFacadeWithJNDI(BaseDao.class).generateEntityName(classPreffix+AlgoUtil.normalizerName(name));
        }
    }

    public String getCanonicalClassName() {
		return canonicalClassName;
	}

	public void setCanonicalClassName(String canonicalClassName) {
		this.canonicalClassName = canonicalClassName;
	}

	public List<DevEntityClassConfig> getEntityClassConfigList() {
        return entityClassConfigList;
    }

    public void setEntityClassConfigList(List<DevEntityClassConfig> entityClassConfigList) {
        this.entityClassConfigList = entityClassConfigList;
    }

    public List<DevEntityPropertyDescriptor> getEntityPropertyDescriptorParentList() {
        return entityPropertyDescriptorParentList;
    }

    public void setEntityPropertyDescriptorParentList(List<DevEntityPropertyDescriptor> entityPropertyDescriptorParentList) {
        this.entityPropertyDescriptorParentList = entityPropertyDescriptorParentList;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevEntityClass other = (DevEntityClass) obj;
        if (this.entityClassId != other.entityClassId && (this.entityClassId == null || !this.entityClassId.equals(other.entityClassId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.entityClassId != null ? this.entityClassId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevEntityClass{" + "entityClassId=" + entityClassId + ", name=" + name + ", entityPropertyDescriptorList=" + entityPropertyDescriptorList + ", entityObjectList=" + entityObjectList + '}';
    }
}
