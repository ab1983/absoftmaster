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
    @NamedQuery(name = "findAllAdmBusinessUnit",
    query = "select u from AdmBusinessUnit u")
})
@Entity
@Table(name="adm_business_unit")
public class AdmBusinessUnit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmBusinessUnit", sequenceName = "sequence_adm_business_unit", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmBusinessUnit")
    @Column(name = "business_unit_id")
    private Long businessUnitId;
    private Long code;
    private String name;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="location_id")
    private GerLocation location = new GerLocation();
    private boolean inactive;

    public Long getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(Long businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public GerLocation getLocation() {
        return location;
    }

    public void setLocation(GerLocation location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmBusinessUnit other = (AdmBusinessUnit) obj;
        if (this.businessUnitId != other.businessUnitId && (this.businessUnitId == null || !this.businessUnitId.equals(other.businessUnitId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + (this.businessUnitId != null ? this.businessUnitId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmBusinessUnit{" + "businessUnitId=" + businessUnitId + ", code=" + code + ", name=" + name + ", location=" + location + ", inactive=" + inactive + '}';
    }

    
    
}
