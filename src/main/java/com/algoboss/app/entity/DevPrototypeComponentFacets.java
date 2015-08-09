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
    @NamedQuery(name = "findAllDevPrototypeComponentFacets",
    query = "select u from DevPrototypeComponentFacets u")
})
@Cacheable(true)
@Entity
@Table(name="dev_prototype_component_facets")
public class DevPrototypeComponentFacets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevPrototypeComponentFacets", sequenceName = "sequence_dev_prototype_component_facets", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevPrototypeComponentFacets")
    @Column(name = "prototype_component_facets_id")
    private Long prototypeComponentFacetsId;
    @Column(name = "prototype_component_name")
    private String prototypeComponentName;    
    @Column(name = "prototype_component_type")
    private String prototypeComponentType;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="prototype_component_facets_id")    
    List<DevPrototypeComponentProperty> prototypeComponentPropertyList = new ArrayList<DevPrototypeComponentProperty>();

    public DevPrototypeComponentFacets() {
    }    
    
    public DevPrototypeComponentFacets(String prototypeComponentName, String prototypeComponentType,List<DevPrototypeComponentProperty> prototypeComponentPropertyList) {
        this.prototypeComponentName = prototypeComponentName;
        this.prototypeComponentType = prototypeComponentType;
        this.prototypeComponentPropertyList = prototypeComponentPropertyList;
    }    
    
    public Long getPrototypeComponentFacetsId() {
        return prototypeComponentFacetsId;
    }

    public void setPrototypeComponentFacetsId(Long prototypeComponentFacetsId) {
        this.prototypeComponentFacetsId = prototypeComponentFacetsId;
    }

    public List<DevPrototypeComponentProperty> getPrototypeComponentPropertyList() {
        return prototypeComponentPropertyList;
    }

    public void setPrototypeComponentPropertyList(List<DevPrototypeComponentProperty> prototypeComponentPropertyList) {
        this.prototypeComponentPropertyList = prototypeComponentPropertyList;
    }

    public String getPrototypeComponentName() {
        return prototypeComponentName;
    }

    public void setPrototypeComponentName(String prototypeComponentName) {
        this.prototypeComponentName = prototypeComponentName;
    }

    public String getPrototypeComponentType() {
        return prototypeComponentType;
    }

    public void setPrototypeComponentType(String prototypeComponentType) {
        this.prototypeComponentType = prototypeComponentType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevPrototypeComponentFacets other = (DevPrototypeComponentFacets) obj;
        if (this.prototypeComponentFacetsId != other.prototypeComponentFacetsId && (this.prototypeComponentFacetsId == null || !this.prototypeComponentFacetsId.equals(other.prototypeComponentFacetsId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.prototypeComponentFacetsId != null ? this.prototypeComponentFacetsId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevPrototypeComponentFacets{" + "prototypeComponentFacetsId=" + prototypeComponentFacetsId + ", prototypeComponentType=" + prototypeComponentType + ", prototypeComponentPropertyList=" + prototypeComponentPropertyList + '}';
    }
        
}
