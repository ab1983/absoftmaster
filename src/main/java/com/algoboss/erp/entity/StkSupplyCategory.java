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
       name="findAllStkSupplyCategory",
       query="select t from StkSupplyCategory t")
})
@Entity
@Table(name="stk_supply_category")
public class StkSupplyCategory extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkSupplyCategory",sequenceName="sequence_stk_supply_category",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkSupplyCategory")
    @Column(name="supply_category_id")
    private Long supplyCategoryId;
    private String code;
    private String description;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSupplyCategoryId() {
        return supplyCategoryId;
    }

    public void setSupplyCategoryId(Long supplyCategoryId) {
        this.supplyCategoryId = supplyCategoryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkSupplyCategory other = (StkSupplyCategory) obj;
        if (this.supplyCategoryId != other.supplyCategoryId && (this.supplyCategoryId == null || !this.supplyCategoryId.equals(other.supplyCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.supplyCategoryId != null ? this.supplyCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkSupplyCategory{" + "supplyCategoryId=" + supplyCategoryId + ", code=" + code + ", description=" + description + '}';
    }

}
