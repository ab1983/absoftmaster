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
       name="findAllStkBrand",
       query="select t from StkBrand t")
})
@Entity
@Table(name="stk_brand")
public class StkBrand extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkBrand",sequenceName="sequence_stk_brand",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkBrand")
    @Column(name="brand_id")
    private Long brandId;
    private String code;
    @Column(nullable=false)
    private String description;

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkBrand other = (StkBrand) obj;
        if (this.brandId != other.brandId && (this.brandId == null || !this.brandId.equals(other.brandId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (this.brandId != null ? this.brandId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkBrand{" + "brandId=" + brandId + ", code=" + code + ", description=" + description + '}';
    }    
}
