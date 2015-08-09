/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;

import com.algoboss.app.entity.DevReportRequirement;
import com.algoboss.app.entity.DevRequirement;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(
            name = "findAllAdmService",
            query = "select t from AdmService t order by t.serviceId"),
    @NamedQuery(
            name = "findAllAdmServiceApproved",
            query = "select t from AdmService t where t.hitCounter>3 order by t.serviceId")
})
@Cacheable(true)
@Entity
@Table(name = "adm_service")
public class AdmService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmService", sequenceName = "sequence_adm_service", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmService")
    @Column(name = "service_id")
    private Long serviceId;
    @Column(name = "date_registration")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateRegistration = new Date();
    @Column(unique = true)
    private String name;
    private String description;
    @Column(name = "main_address")
    private String mainAddress;
    @Column(name = "authorization_address")
    private String authorizationAddress;
    private String version;
    @Column(name = "last_update")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastUpdate;
    @Column(name = "last_access")
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    private Date lastAccess;    
    private String module;
    private boolean inactive;
    @Column(name = "hit_counter")
    private long hitCounter;
    @OneToOne(mappedBy = "service", fetch = FetchType.LAZY)
    private DevRequirement requirement;
    @OneToOne(mappedBy = "service", fetch = FetchType.LAZY)
    private DevReportRequirement reportRequirement;    
    @OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "service_id")
    private List<AdmServiceContract> serviceContractList;

    public AdmService() {
    }

    public AdmService(String name, String description, String module, String mainAddress, String authorizationAddress, String version, Date lastUpdate) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.mainAddress = mainAddress;
        this.authorizationAddress = authorizationAddress;
        this.version = version;
        this.lastUpdate = lastUpdate;
    }

    public long getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(long hitCounter) {
        this.hitCounter = hitCounter;
    }

    public String getAuthorizationAddress() {
        return authorizationAddress;
    }

    public void setAuthorizationAddress(String authorizationAddress) {
        this.authorizationAddress = authorizationAddress;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Date getLastAccess() {
        return lastAccess;
    }

    public void setLastAccess(Date lastAccess) {
        this.lastAccess = lastAccess;
    }    
    
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public DevRequirement getRequirement() {
        return requirement;
    }

    public void setRequirement(DevRequirement requirement) {
        this.requirement = requirement;
    }

    public DevReportRequirement getReportRequirement() {
        return reportRequirement;
    }

    public void setReportRequirement(DevReportRequirement reportRequirement) {
        this.reportRequirement = reportRequirement;
    }

    public List<AdmServiceContract> getServiceContractList() {
        return serviceContractList;
    }

    public void setServiceContractList(List<AdmServiceContract> serviceContractList) {
        this.serviceContractList = serviceContractList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmService other = (AdmService) obj;
        if (this.serviceId != other.serviceId && (this.serviceId == null || !this.serviceId.equals(other.serviceId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.serviceId != null ? this.serviceId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmService{" + "serviceId=" + serviceId + ", dateRegistration=" + dateRegistration + ", name=" + name + ", description=" + description + ", mainAddress=" + mainAddress + ", authorizationAddress=" + authorizationAddress + '}';
    }
}
