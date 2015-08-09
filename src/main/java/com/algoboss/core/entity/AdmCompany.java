/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllAdmCompany",
    query = "select u from AdmCompany u")
})
@Entity
@Table(name = "adm_company")
public class AdmCompany implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmCompany", sequenceName = "sequence_adm_company", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmCompany")
    @Column(name = "company_id")
    private Long companyId;
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
    private boolean hasSubsidiary;
    @Column(name = "data_source")
    private String dataSource;

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
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

    public boolean isHasSubsidiary() {
        return hasSubsidiary;
    }

    public void setHasSubsidiary(boolean hasSubsidiary) {
        this.hasSubsidiary = hasSubsidiary;
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
    
    public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmCompany other = (AdmCompany) obj;
        if (this.companyId != other.companyId && (this.companyId == null || !this.companyId.equals(other.companyId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + (this.companyId != null ? this.companyId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmCompany{" + "companyId=" + companyId + ", code=" + code + ", corporateName=" + corporateName + ", registrationType=" + registrationType + ", registrationNumber=" + registrationNumber + ", fancyName=" + fancyName + ", stateRegistration=" + stateRegistration + ", municipalRegistration=" + municipalRegistration + ", location=" + location + ", homePage=" + homePage + ", inactive=" + inactive + ", hasSubsidiary=" + hasSubsidiary + '}';
    }
   
    enum CompanyRegistrationType{
        INDIVIDUAL_REGISTRATION("individualRegistration"),
        CORPORATE_REGISTRATION("corporateRegistration");
        
        private String registrationTypeName;

        private CompanyRegistrationType(String registrationTypeName) {
            this.registrationTypeName = registrationTypeName;
        }

        public String getRegistrationTypeName() {
            return registrationTypeName;
        }

        public void setRegistrationTypeName(String registrationTypeName) {
            this.registrationTypeName = registrationTypeName;
        }
        
        
    }
}
