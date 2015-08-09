/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({ 
    @NamedQuery(name = "findAllDevReportFieldOptionsMap",
    query = "select u from DevReportFieldOptionsMap u")
})
@Cacheable(true)
@Entity
@Table(name="dev_report_field_options_map")
public class DevReportFieldOptionsMap implements Serializable, Cloneable {
  
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevReportFieldOptionsMap", sequenceName = "sequence_dev_report_field_options_map", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevReportFieldOptionsMap")
    @Column(name = "field_options_map_id")
    private Long fieldOptionsId;
    @Column(name = "options_type")
    private String optionsType;    
    @Column(name = "options_name")
    private String optionsName;
    @Column(name = "options_value")
    private String optionsValue;    

    public DevReportFieldOptionsMap() {
    }    
    
    public DevReportFieldOptionsMap(String optionsType, String optionsName, Object optionsValue) {
        this.optionsType = optionsType;
        this.optionsName = optionsName;
        this.optionsValue = String.valueOf(optionsValue);
    }    
    
    public Long getFieldOptionsId() {
        return fieldOptionsId;
    }

    public void setFieldOptionsId(Long fieldOptionsId) {
        this.fieldOptionsId = fieldOptionsId;
    }

    public String getOptionsType() {
        return optionsType;
    }

    public void setOptionsType(String optionsType) {
        this.optionsType = optionsType;
    }

    public String getOptionsName() {
        return optionsName;
    }

    public void setOptionsName(String optionsName) {
        this.optionsName = optionsName;
    }

    public Object getOptionsValue() {
        Object obj = null;
        if(optionsType!=null && optionsValue!=null){
            if(optionsType.equals("Boolean")){
                obj = Boolean.valueOf(optionsValue);
            }else{
                obj = String.valueOf(optionsValue);
            }
        }
        return obj;
    }

    public void setOptionsValue(String optionsValue) {
        this.optionsValue = optionsValue;
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
        final DevReportFieldOptionsMap other = (DevReportFieldOptionsMap) obj;
        if (this.fieldOptionsId != other.fieldOptionsId && (this.fieldOptionsId == null || !this.fieldOptionsId.equals(other.fieldOptionsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevReportFieldOptions{" + "fieldOptionsId=" + fieldOptionsId + ", optionsType=" + optionsType + ", optionsName=" + optionsName + ", optionsValue=" + optionsValue + '}';
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
