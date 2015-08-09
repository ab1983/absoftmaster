/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevPrototypeComponentProperty",
    query = "select u from DevPrototypeComponentProperty u")
})
@Cacheable(true)
@Entity
@Table(name="dev_prototype_component_property")
public class DevPrototypeComponentProperty implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevPrototypeComponentProperty", sequenceName = "sequence_dev_prototype_component_property", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevPrototypeComponentProperty")
    @Column(name = "prototype_component_property_id")
    private Long prototypeComponentPropertyId;
    @Column(name = "property_name")
    private String propertyName;
    @Column(name = "property_type")
    private String propertyType;
    @Column(name = "property_value")
    private String propertyValue;
    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)  
    @OrderBy
    @JoinColumn(name="parent_id")
    private List<DevPrototypeComponentChildren> children = new ArrayList<DevPrototypeComponentChildren>();
    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)  
    @OrderBy
    @JoinColumn(name="parent_id")
    private List<DevPrototypeComponentFacets> facets = new ArrayList<DevPrototypeComponentFacets>();
    @OneToMany(cascade= CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval=true)  
    @OrderBy
    @JoinColumn(name="parent_id")
    private List<DevPrototypeComponentBehaviors> behaviors = new ArrayList<DevPrototypeComponentBehaviors>();        

    public DevPrototypeComponentProperty() {
        
    }    
    
    public DevPrototypeComponentProperty(String propertyName, String propertyType, String propertyValue, List<DevPrototypeComponentChildren> children, List<DevPrototypeComponentFacets> facets, List<DevPrototypeComponentBehaviors> behaviors) {
        this.propertyName = propertyName;
        this.propertyType = propertyType;
        this.propertyValue = propertyValue;
        this.children = children;
        this.facets = facets;
        this.behaviors = behaviors;
    }
    
    
    
    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    public Long getPrototypeComponentPropertyId() {
        return prototypeComponentPropertyId;
    }

    public void setPrototypeComponentPropertyId(Long prototypeComponentPropertyId) {
        this.prototypeComponentPropertyId = prototypeComponentPropertyId;
    }

    public List<DevPrototypeComponentChildren> getChildren() {
        return children;
    }

    public void setChildren(List<DevPrototypeComponentChildren> children) {
        this.children = children;
    } 

    public List<DevPrototypeComponentFacets> getFacets() {
        return facets;
    }

    public void setFacets(List<DevPrototypeComponentFacets> facets) {
        this.facets = facets;
    }        
    
    public List<DevPrototypeComponentBehaviors> getBehaviors() {
		return behaviors;
	}

	public void setBehaviors(List<DevPrototypeComponentBehaviors> behaviors) {
		this.behaviors = behaviors;
	}

	@Override
    public String toString() {
        return "DevPrototypeComponentProperty{" + "prototypeComponentPropertyId=" + prototypeComponentPropertyId + ", propertyName=" + propertyName + ", propertyType=" + propertyType + ", propertyValue=" + propertyValue + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevPrototypeComponentProperty other = (DevPrototypeComponentProperty) obj;
        if (this.prototypeComponentPropertyId != other.prototypeComponentPropertyId && (this.prototypeComponentPropertyId == null || !this.prototypeComponentPropertyId.equals(other.prototypeComponentPropertyId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.prototypeComponentPropertyId != null ? this.prototypeComponentPropertyId.hashCode() : 0);
        return hash;
    }
    
    
}
