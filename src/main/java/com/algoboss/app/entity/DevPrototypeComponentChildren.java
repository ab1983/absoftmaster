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
    @NamedQuery(name = "findAllDevPrototypeComponentChildren",
    query = "select u from DevPrototypeComponentChildren u")
})
@Cacheable(true)
@Entity
@Table(name="dev_prototype_component_children")
public class DevPrototypeComponentChildren implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevPrototypeComponentChildren", sequenceName = "sequence_dev_prototype_component_children", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevPrototypeComponentChildren")
    @Column(name = "prototype_component_children_id")
    private Long prototypeComponentChildrenId;
    @Column(name = "prototype_component_type")
    private String prototypeComponentType;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @OrderBy
    @JoinColumn(name="prototype_component_children_id")    
    List<DevPrototypeComponentProperty> prototypeComponentPropertyList = new ArrayList<DevPrototypeComponentProperty>();
    @Column(name="component_container_id")
    private Long componentContainerId;
    
    public DevPrototypeComponentChildren() {
    }    
    
    public DevPrototypeComponentChildren(String prototypeComponentType,List<DevPrototypeComponentProperty> prototypeComponentPropertyList) {
        this.prototypeComponentType = prototypeComponentType;
        this.prototypeComponentPropertyList = prototypeComponentPropertyList;
    }    
    
    public Long getPrototypeComponentChildrenId() {
        return prototypeComponentChildrenId;
    }

    public void setPrototypeComponentChildrenId(Long prototypeComponentChildrenId) {
        this.prototypeComponentChildrenId = prototypeComponentChildrenId;
    }

    public List<DevPrototypeComponentProperty> getPrototypeComponentPropertyList() {
        return prototypeComponentPropertyList;
    }

    public void setPrototypeComponentPropertyList(List<DevPrototypeComponentProperty> prototypeComponentPropertyList) {
        this.prototypeComponentPropertyList = prototypeComponentPropertyList;
    }

    public String getPrototypeComponentType() {
        return prototypeComponentType;
    }

    public void setPrototypeComponentType(String prototypeComponentType) {
        this.prototypeComponentType = prototypeComponentType;
    }    
    
    public Long getComponentContainerId() {
		return componentContainerId;
	}

	public void setComponentContainerId(Long componentContainerId) {
		this.componentContainerId = componentContainerId;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevPrototypeComponentChildren other = (DevPrototypeComponentChildren) obj;
        if (this.prototypeComponentChildrenId != other.prototypeComponentChildrenId && (this.prototypeComponentChildrenId == null || !this.prototypeComponentChildrenId.equals(other.prototypeComponentChildrenId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.prototypeComponentChildrenId != null ? this.prototypeComponentChildrenId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevPrototypeComponentChildren{" + "prototypeComponentChildrenId=" + prototypeComponentChildrenId + ", prototypeComponentType=" + prototypeComponentType + ", prototypeComponentPropertyList=" + prototypeComponentPropertyList + '}';
    }
        
}
