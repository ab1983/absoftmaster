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
    @NamedQuery(name = "findAllAdmServiceContract",
    query = "select u from AdmServiceContract u")
})
@Entity
@Table(name="adm_service_contract")
public class AdmServiceContract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmServiceContract", sequenceName = "sequence_adm_service_contract", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmServiceContract")
    @Column(name = "service_contract_id")
    private Long serviceContractId;
    @ManyToOne()
    @JoinColumn(name="service_id",nullable=false)    
    private AdmService service;
    private Integer amount;
    private boolean inactive;
    @Column(name = "hit_counter")
    private long hitCounter;
    @ManyToOne()
    @JoinColumn(name="service_module_contract_id",nullable=false)    
    private AdmServiceModuleContract serviceModuleContract;
    @OneToOne(cascade = CascadeType.ALL,orphanRemoval = true, mappedBy = "serviceContract", fetch = FetchType.LAZY)
    private SecUserAuthorization userAuthorization;

    public AdmServiceModuleContract getServiceModuleContract() {
        return serviceModuleContract;
    }

    public void setServiceModuleContract(AdmServiceModuleContract serviceModuleContract) {
        this.serviceModuleContract = serviceModuleContract;
    }       
    
    public long getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(long hitCounter) {
        this.hitCounter = hitCounter;
    }
    
    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public AdmService getService() {
        return service;
    }

    public void setService(AdmService service) {
        this.service = service;
    }

    public Long getServiceContractId() {
        return serviceContractId;
    }

    public void setServiceContractId(Long serviceContractId) {
        this.serviceContractId = serviceContractId;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public SecUserAuthorization getUserAuthorization() {
        return userAuthorization;
    }

    public void setUserAuthorization(SecUserAuthorization userAuthorization) {
        this.userAuthorization = userAuthorization;
    }    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmServiceContract other = (AdmServiceContract) obj;
        if (this.serviceContractId != other.serviceContractId && (this.serviceContractId == null || !this.serviceContractId.equals(other.serviceContractId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.serviceContractId != null ? this.serviceContractId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmServiceContract{" + "serviceContractId=" + serviceContractId + ", service=" + service + ", amount=" + amount + ", inactive=" + inactive + '}';
    }



    
    
}
