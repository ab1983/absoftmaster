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
    @NamedQuery(name = "findAllDevReportFieldContainer",
    query = "select u from DevReportFieldContainer u")
})
@Cacheable(true)
@Entity
@Table(name="dev_report_field_container")
public class DevReportFieldContainer implements Serializable,Cloneable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevReportField", sequenceName = "sequence_dev_report_field", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevReportField")
    @Column(name = "field_container_id")
    private Long fieldContainerId;  
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "field_container_id")
    private List<DevReportFieldOptions> fieldOptionsList = new ArrayList<DevReportFieldOptions>();   

    public DevReportFieldContainer() {
    }   
    
    public DevReportFieldContainer(String name) {
        this.name = name;
    }

    public Long getFieldContainerId() {
        return fieldContainerId;
    }

    public void setFieldContainerId(Long fieldContainerId) {
        this.fieldContainerId = fieldContainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DevReportFieldOptions> getFieldOptionsList() {
        return fieldOptionsList;
    }

    public void setFieldOptionsList(List<DevReportFieldOptions> fieldOptionsList) {
        this.fieldOptionsList = fieldOptionsList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + (this.fieldContainerId != null ? this.fieldContainerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevReportFieldContainer other = (DevReportFieldContainer) obj;
        if (this.fieldContainerId != other.fieldContainerId && (this.fieldContainerId == null || !this.fieldContainerId.equals(other.fieldContainerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevReportFieldContainer{" + "fieldContainerId=" + fieldContainerId + ", name=" + name + ", fieldOptionsList=" + fieldOptionsList + '}';
    }

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		
		try {
			return super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
		}
	}
    
}
