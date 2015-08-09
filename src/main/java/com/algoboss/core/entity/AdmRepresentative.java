
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.algoboss.core.entity.AdmCompany.CompanyRegistrationType;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllAdmRepresentative",
    query = "select u from AdmRepresentative u")
})
@Entity
@Table(name="adm_representative")
public class AdmRepresentative implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmRepresentative", sequenceName = "sequence_adm_representative", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmRepresentative")
    @Column(name = "representative_id")
    private Long representativeId;
    private String company;
    private String contact;
    private boolean inactive;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="user_id")
    private SecUser user = new SecUser();
    @OneToMany(mappedBy="representative",fetch= FetchType.LAZY)
    private List<AdmContract> contractList;
    @ManyToOne(fetch = FetchType.LAZY)  
    @JoinColumn(name="supervisor_id")
    private AdmRepresentative supervisor;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supervisor")  
    private List<AdmRepresentative> representativeList = new ArrayList();    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="location_id")
    private GerLocation location = new GerLocation();
    @Enumerated
    @Column(name="registration_type")
    private CompanyRegistrationType registrationType;
    @Column(name="registration_number")
    private String registrationNumber;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name="bank_account_id")    
    private GerBankAccount bankAccount;

    public GerBankAccount getBankAccount() {
        if(bankAccount==null){
            bankAccount = new GerBankAccount();
        }
        return bankAccount;
    }

    public void setBankAccount(GerBankAccount bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public CompanyRegistrationType getRegistrationType() {
        return registrationType;
    }

    public void setRegistrationType(CompanyRegistrationType registrationType) {
        this.registrationType = registrationType;
    }      
    
    public GerLocation getLocation() {
        if(location==null){
            location = new GerLocation();
        }
        return location;
    }

    public void setLocation(GerLocation location) {
        this.location = location;
    }
    
    
    public List<AdmRepresentative> getRepresentativeList() {
        return representativeList;
    }

    public void setRepresentativeList(List<AdmRepresentative> representativeList) {
        this.representativeList = representativeList;
    }

    public AdmRepresentative getSupervisor() {
        return supervisor;
    }

    public void setSupervisor(AdmRepresentative supervisor) {
        this.supervisor = supervisor;
    }        
    
    public List<AdmContract> getContractList() {
        return contractList;
    }

    public void setContractList(List<AdmContract> contractList) {
        this.contractList = contractList;
    }    
    
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public Long getRepresentativeId() {
        return representativeId;
    }

    public void setRepresentativeId(Long representativeId) {
        this.representativeId = representativeId;
    }

    public SecUser getUser() {
        return user;
    }

    public void setUser(SecUser user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmRepresentative other = (AdmRepresentative) obj;
        if (this.representativeId != other.representativeId && (this.representativeId == null || !this.representativeId.equals(other.representativeId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + (this.representativeId != null ? this.representativeId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmRepresentative{" + "representativeId=" + representativeId + ", company=" + company + ", contact=" + contact + ", inactive=" + inactive + ", user=" + user + '}';
    }    
    
}
