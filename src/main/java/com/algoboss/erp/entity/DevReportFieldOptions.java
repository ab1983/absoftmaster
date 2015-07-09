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
    @NamedQuery(name = "findAllDevReportFieldOptions",
    query = "select u from DevReportFieldOptions u")
})
@Cacheable(true)
@Entity
@Table(name="dev_report_field_options")
public class DevReportFieldOptions implements Serializable, Cloneable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevReportFieldOptions", sequenceName = "sequence_dev_report_field_options", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevReportFieldOptions")
    @Column(name = "field_options_id")
    private Long fieldOptionsId;
    @Column(name = "name")
    private String name;    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_property_descriptor_id")    
    private DevEntityPropertyDescriptor entityPropertyDescriptor;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "field_options_id")
    private List<DevReportFieldOptionsMap> fieldOptionsMapList = new ArrayList<DevReportFieldOptionsMap>();   

    public Long getFieldOptionsId() {
        return fieldOptionsId;
    }

    public void setFieldOptionsId(Long fieldOptionsId) {
        this.fieldOptionsId = fieldOptionsId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DevEntityPropertyDescriptor getEntityPropertyDescriptor() {
        return entityPropertyDescriptor;
    }

    public void setEntityPropertyDescriptor(DevEntityPropertyDescriptor entityPropertyDescriptor) {
        this.entityPropertyDescriptor = entityPropertyDescriptor;
    }

    public List<DevReportFieldOptionsMap> getFieldOptionsMapList() {
        return fieldOptionsMapList;
    }

    public void setFieldOptionsMapList(List<DevReportFieldOptionsMap> fieldOptionsMapList) {
        this.fieldOptionsMapList = fieldOptionsMapList;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.fieldOptionsId != null ? this.fieldOptionsId.hashCode() : 0);
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
        final DevReportFieldOptions other = (DevReportFieldOptions) obj;
        if (this.fieldOptionsId != other.fieldOptionsId && (this.fieldOptionsId == null || !this.fieldOptionsId.equals(other.fieldOptionsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevReportFieldOptions{" + "fieldOptionsId=" + fieldOptionsId + ", name=" + name + ", entityPropertyDescriptor=" + entityPropertyDescriptor + ", fieldOptionsMapList=" + fieldOptionsMapList + '}';
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
	
	public DevReportFieldOptions attr(String optionName, String optionValue){
		loadOpt(this, optionName, "String").setOptionsValue(optionValue);
		return this;
	}
	
	public static DevReportFieldOptionsMap loadOpt(DevReportFieldOptions fieldOptions, String optionName, String optionType) {
		DevReportFieldOptionsMap fieldOptionsMap = null;
		for (DevReportFieldOptionsMap fieldOptionsMapTmp : fieldOptions.getFieldOptionsMapList()) {
			if (fieldOptionsMapTmp.getOptionsName().equals(optionName)) {
				fieldOptionsMap = fieldOptionsMapTmp;
				break;
			}
		}
		if (fieldOptionsMap == null) {
			fieldOptionsMap = new DevReportFieldOptionsMap();
			if (optionType != null) {
				fieldOptionsMap.setOptionsName(optionName);
				fieldOptionsMap.setOptionsType(optionType);
				fieldOptions.getFieldOptionsMapList().add(fieldOptionsMap);
			}
		}
		return fieldOptionsMap;
	}	
    
}
