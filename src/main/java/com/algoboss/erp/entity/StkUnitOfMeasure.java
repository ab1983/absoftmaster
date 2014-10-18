/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllStkUnitOfMeasure",
       query="select t from StkUnitOfMeasure t")
})
@Entity
@Table(name="stk_unit_of_measure")
public class StkUnitOfMeasure extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkUnitOfMeasure",sequenceName="sequence_stk_unit_of_measure",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkUnitOfMeasure")
    @Column(name="unit_of_measure_id")
    private Long unitOfMeasureId;
    private String abbreviation;
    private String description;

    public String getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUnitOfMeasureId() {
        return unitOfMeasureId;
    }

    public void setUnitOfMeasureId(Long unitOfMeasureId) {
        this.unitOfMeasureId = unitOfMeasureId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkUnitOfMeasure other = (StkUnitOfMeasure) obj;
        if (this.unitOfMeasureId != other.unitOfMeasureId && (this.unitOfMeasureId == null || !this.unitOfMeasureId.equals(other.unitOfMeasureId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.unitOfMeasureId != null ? this.unitOfMeasureId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkUnitOfMeasure{" + "unitOfMeasureId=" + unitOfMeasureId + ", abbreviation=" + abbreviation + ", description=" + description + '}';
    }
    
}
