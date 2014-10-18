/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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
public class DevComponentContainer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevComponentContainer", sequenceName = "sequence_dev_component_container", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevComponentContainer")
    @Column(name = "component_container_id")
    private Long componentContainerId;
    private String name;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @OrderBy
    @JoinColumn(name="component_container_id")
    private List<DevPrototypeComponentChildren> prototypeComponentChildrenList = new ArrayList<DevPrototypeComponentChildren>();

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
        return prototypeComponentChildrenList;
    }

    public void setPrototypeComponentChildrenList(List<DevPrototypeComponentChildren> prototypeComponentChildrenList) {
        this.prototypeComponentChildrenList = prototypeComponentChildrenList;
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
        
}
