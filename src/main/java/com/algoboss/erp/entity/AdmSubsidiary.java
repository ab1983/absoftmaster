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
    @NamedQuery(name = "findAllAdmSubsidiary",
    query = "select u from AdmSubsidiary u")
})
@Entity
@Table(name="adm_subsidiary")
public class AdmSubsidiary implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmSubsidiary", sequenceName = "sequence_adm_subsidiary", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmSubsidiary")
    @Column(name = "subsidiary_id")
    private Long subsidiaryId;
    private Long code;
    private String corporateName;
    private String registrationType;
    private String registrationNumber;
    private String fancyName;
    private String stateRegistration;
    private String municipalRegistration;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="location_id")
    private GerLocation location = new GerLocation();
    private String homePage;
    private boolean inactive;
    @ManyToOne()
    @JoinColumn(name="company_id",nullable=false)
    private AdmCompany company = new AdmCompany();

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public AdmCompany getCompany() {
        return company;
    }

    public void setCompany(AdmCompany company) {
        this.company = company;
    }

    public String getCorporateName() {
        return corporateName;
    }

    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    public String getFancyName() {
        return fancyName;
    }

    public void setFancyName(String fancyName) {
        this.fancyName = fancyName;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
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

    public String getMunicipalRegistration() {
        return municipalRegistration;
    }

    public void setMunicipalRegistration(String municipalRegistration) {
        this.municipalRegistration = municipalRegistration;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(String registrationType) {
        this.registrationType = registrationType;
    }

    public String getStateRegistration() {
        return stateRegistration;
    }

    public void setStateRegistration(String stateRegistration) {
        this.stateRegistration = stateRegistration;
    }

    public Long getSubsidiaryId() {
        return subsidiaryId;
    }

    public void setSubsidiaryId(Long subsidiaryId) {
        this.subsidiaryId = subsidiaryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmSubsidiary other = (AdmSubsidiary) obj;
        if (this.subsidiaryId != other.subsidiaryId && (this.subsidiaryId == null || !this.subsidiaryId.equals(other.subsidiaryId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (this.subsidiaryId != null ? this.subsidiaryId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmSubsidiary{" + "subsidiaryId=" + subsidiaryId + ", code=" + code + ", corporateName=" + corporateName + ", registrationType=" + registrationType + ", registrationNumber=" + registrationNumber + ", fancyName=" + fancyName + ", stateRegistration=" + stateRegistration + ", municipalRegistration=" + municipalRegistration + ", location=" + location + ", homePage=" + homePage + ", inactive=" + inactive + ", company=" + company + '}';
    }

    
}
