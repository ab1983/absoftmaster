/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllAdmContract",
    query = "select t from AdmContract t")
})
@Entity
@Table(name="adm_contract")
public class AdmContract implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "AdmContract", sequenceName = "sequence_adm_contract", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AdmContract")
    @Column(name = "contract_id")
    private Long contractId;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date startDate = new Date();
    @Column(name = "system_theme")
    private String systemTheme;
    @Column(name = "system_name")
    private String systemName;    
    @Column(name = "system_logo")
    private String systemLogo;        
    @ManyToOne()
    @JoinColumn(name="service_home_page_id")
    private AdmService serviceHomePage;        
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    @JoinColumn(name="contract_id")    
    private List<AdmCompany> companyList = new ArrayList();
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name="location_id")
    private GerLocation locationCollection = new GerLocation();
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    @JoinColumn(name="contract_id")
    private List<AdmServiceModuleContract> serviceModuleContractList = new ArrayList<AdmServiceModuleContract>();     
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.LAZY,orphanRemoval=true)
    @JoinColumn(name="contract_id")
    private List<SecUser> userList = new ArrayList<SecUser>();      
    @ManyToOne()
    @JoinColumn(name="representative_id",nullable=false)
    private AdmRepresentative representative;
    @OneToMany(cascade= CascadeType.REMOVE,mappedBy="contract",fetch= FetchType.LAZY,orphanRemoval = true)
    private List<AdmInstantiatesSite> instantiatesSiteList;

    public List<SecUser> getUserList() {
        return userList;
    }

    public void setUserList(List<SecUser> userList) {
        this.userList = userList;
    }

    public List<AdmInstantiatesSite> getInstantiatesSiteList() {
        return instantiatesSiteList;
    }

    public void setInstantiatesSiteList(List<AdmInstantiatesSite> instantiatesSiteList) {
        this.instantiatesSiteList = instantiatesSiteList;
    }    
    
    public Long getContractId() {
        return contractId;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    public GerLocation getLocationCollection() {
        return locationCollection;
    }

    public void setLocationCollection(GerLocation locationCollection) {
        this.locationCollection = locationCollection;
    }

    public AdmRepresentative getRepresentative() {
        return representative;
    }

    public void setRepresentative(AdmRepresentative representative) {
        this.representative = representative;
    }

    public List<AdmServiceModuleContract> getServiceModuleContractList() {
        return serviceModuleContractList;
    }

    public void setServiceModuleContractList(List<AdmServiceModuleContract> serviceModuleContractList) {
        this.serviceModuleContractList = serviceModuleContractList;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getSystemTheme() {
        return systemTheme;
    }

    public void setSystemTheme(String systemTheme) {
        this.systemTheme = systemTheme;
    }
    
    public String getSystemLogo() {
        return systemLogo;
    }

    public void setSystemLogo(String systemLogo) {    	
    	try {
    		if(systemLogo.isEmpty()){
    			this.systemLogo = null;
    		}
			if(systemLogo!=null && (new File(systemLogo).exists()  || ((HttpURLConnection)new URL(systemLogo).openConnection()).getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND)){
				this.systemLogo = systemLogo;    		
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    
    public List<AdmCompany> getCompanyList() {
        return companyList;
    }

    public void setCompanyList(List<AdmCompany> companyList) {
        this.companyList = companyList;
    }

    public AdmService getServiceHomePage() {
        return serviceHomePage;
    }

    public void setServiceHomePage(AdmService serviceHomePage) {
        this.serviceHomePage = serviceHomePage;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AdmContract other = (AdmContract) obj;
        if (this.contractId != other.contractId && (this.contractId == null || !this.contractId.equals(other.contractId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + (this.contractId != null ? this.contractId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "AdmContract{" + "contractId=" + contractId + ", startDate=" + startDate + ", locationCollection=" + locationCollection + ", serviceModuleContractList=" + serviceModuleContractList + ", representative=" + representative + '}';
    }    
    
}
