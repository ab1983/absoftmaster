/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllAdmInstantiatesSite",
    query = "select u from AdmInstantiatesSite u")
})
@Entity
@Table(name = "adm_instantiates_site")
public class AdmInstantiatesSite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmInstantiatesSite", sequenceName = "sequence_adm_instantiates_site", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmInstantiatesSite")
    @Column(name = "instantiates_site_id")
    private Long instantiatesSiteId;
    @ManyToOne()
    @JoinColumn(name = "contract_id",nullable=false,updatable=false)
    private AdmContract contract;
    @ManyToOne()
    @JoinColumn(name = "company_id")
    private AdmCompany company;
    @ManyToOne()
    @JoinColumn(name = "subsidiary_id")
    private AdmSubsidiary subsidiary;
    @ManyToOne()
    @JoinColumn(name = "business_unit_id")
    private AdmBusinessUnit businessUnit;

    public AdmBusinessUnit getBusinessUnit() {
        return businessUnit;
    }

    public void setBusinessUnit(AdmBusinessUnit businessUnit) {
        this.businessUnit = businessUnit;
    }

    public AdmCompany getCompany() {
        return company;
    }

    public void setCompany(AdmCompany company) {
        this.company = company;
    }

    public AdmContract getContract() {
        return contract;
    }

    public void setContract(AdmContract contract) {
        this.contract = contract;
    }

    public Long getInstantiatesSiteId() {
        return instantiatesSiteId;
    }

    public void setInstantiatesSiteId(Long instantiatesSiteId) {
        this.instantiatesSiteId = instantiatesSiteId;
    }

    public AdmSubsidiary getSubsidiary() {
        return subsidiary;
    }

    public void setSubsidiary(AdmSubsidiary subsidiary) {
        this.subsidiary = subsidiary;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmInstantiatesSite other = (AdmInstantiatesSite) obj;
        if (this.instantiatesSiteId != other.instantiatesSiteId && (this.instantiatesSiteId == null || !this.instantiatesSiteId.equals(other.instantiatesSiteId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + (this.instantiatesSiteId != null ? this.instantiatesSiteId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmInstantiatesSite{" + "instantiatesSiteId=" + instantiatesSiteId + ", contract=" + contract + ", company=" + company + ", subsidiary=" + subsidiary + ", businessUnit=" + businessUnit + '}';
    }    
    
}
