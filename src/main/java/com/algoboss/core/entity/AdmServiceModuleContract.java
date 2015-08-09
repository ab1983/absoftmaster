/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllAdmServiceModuleContract",
    query = "select u from AdmServiceModuleContract u")
})
@Entity
@Table(name="adm_service_module_contract")
public class AdmServiceModuleContract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmServiceModuleContract", sequenceName = "sequence_adm_service_module_contract", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmServiceModuleContract")
    @Column(name = "service_module_contract_id")
    private Long serviceModuleContractId;
    private String name;
    private String description;
    private Integer expectedAmount;
    private Integer currentAmount;
    private boolean inactive;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="service_module_contract_id")
    private List<AdmServiceContract> serviceContractList = new ArrayList<AdmServiceContract>();

    public Integer getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Integer currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getExpectedAmount() {
        return expectedAmount;
    }

    public void setExpectedAmount(Integer expectedAmount) {
        this.expectedAmount = expectedAmount;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AdmServiceContract> getServiceContractList() {
        return serviceContractList;
    }

    public void setServiceContractList(List<AdmServiceContract> serviceContractList) {
        this.serviceContractList = serviceContractList;
    }

    public Long getServiceModuleContractId() {
        return serviceModuleContractId;
    }

    public void setServiceModuleContractId(Long serviceModuleContractId) {
        this.serviceModuleContractId = serviceModuleContractId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmServiceModuleContract other = (AdmServiceModuleContract) obj;
        if (this.serviceModuleContractId != other.serviceModuleContractId && (this.serviceModuleContractId == null || !this.serviceModuleContractId.equals(other.serviceModuleContractId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.serviceModuleContractId != null ? this.serviceModuleContractId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmServiceModuleContract{" + "serviceModuleContractId=" + serviceModuleContractId + ", name=" + name + ", description=" + description + ", expectedAmount=" + expectedAmount + ", currentAmount=" + currentAmount + ", inactive=" + inactive + ", serviceContractList=" + serviceContractList + '}';
    }

    
    
}
