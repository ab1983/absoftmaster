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
    @NamedQuery(name = "findAllDevPrototypeComponentBehaviors",
    query = "select u from DevPrototypeComponentBehaviors u")
})
@Cacheable(true)
@Entity
@Table(name="dev_prototype_component_behaviors")
public class DevPrototypeComponentBehaviors implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevPrototypeComponentBehaviors", sequenceName = "sequence_dev_prototype_component_behaviors", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevPrototypeComponentBehaviors")
    @Column(name = "prototype_component_behaviors_id")
    private Long prototypeComponentBehaviorsId;
    @Column(name = "prototype_component_name")
    private String prototypeComponentName;    
    @Column(name = "prototype_component_type")
    private String prototypeComponentType;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="prototype_component_behaviors_id")    
    List<DevPrototypeComponentProperty> prototypeComponentPropertyList = new ArrayList<DevPrototypeComponentProperty>();

    public DevPrototypeComponentBehaviors() {
    }    
    
    public DevPrototypeComponentBehaviors(String prototypeComponentName, String prototypeComponentType,List<DevPrototypeComponentProperty> prototypeComponentPropertyList) {
        this.prototypeComponentName = prototypeComponentName;
        this.prototypeComponentType = prototypeComponentType;
        this.prototypeComponentPropertyList = prototypeComponentPropertyList;
    }        

    public Long getPrototypeComponentBehaviorsId() {
		return prototypeComponentBehaviorsId;
	}

	public void setPrototypeComponentBehaviorsId(Long prototypeComponentBehaviorsId) {
		this.prototypeComponentBehaviorsId = prototypeComponentBehaviorsId;
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((prototypeComponentBehaviorsId == null) ? 0
						: prototypeComponentBehaviorsId.hashCode());
		result = prime
				* result
				+ ((prototypeComponentName == null) ? 0
						: prototypeComponentName.hashCode());
		result = prime
				* result
				+ ((prototypeComponentPropertyList == null) ? 0
						: prototypeComponentPropertyList.hashCode());
		result = prime
				* result
				+ ((prototypeComponentType == null) ? 0
						: prototypeComponentType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DevPrototypeComponentBehaviors other = (DevPrototypeComponentBehaviors) obj;
		if (prototypeComponentBehaviorsId == null) {
			if (other.prototypeComponentBehaviorsId != null)
				return false;
		} else if (!prototypeComponentBehaviorsId
				.equals(other.prototypeComponentBehaviorsId))
			return false;
		if (prototypeComponentName == null) {
			if (other.prototypeComponentName != null)
				return false;
		} else if (!prototypeComponentName.equals(other.prototypeComponentName))
			return false;
		if (prototypeComponentPropertyList == null) {
			if (other.prototypeComponentPropertyList != null)
				return false;
		} else if (!prototypeComponentPropertyList
				.equals(other.prototypeComponentPropertyList))
			return false;
		if (prototypeComponentType == null) {
			if (other.prototypeComponentType != null)
				return false;
		} else if (!prototypeComponentType.equals(other.prototypeComponentType))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DevPrototypeComponentBehaviors [prototypeComponentBehaviorsId="
				+ prototypeComponentBehaviorsId + ", prototypeComponentName="
				+ prototypeComponentName + ", prototypeComponentType="
				+ prototypeComponentType + ", prototypeComponentPropertyList="
				+ prototypeComponentPropertyList + "]";
	}


        
}
