/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
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
import javax.persistence.Temporal;

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
    @Column(name = "system_context_path")
    private String systemContextPath;       
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
    @ManyToOne(cascade= CascadeType.PERSIST)
    @JoinColumn(name="representative_id",nullable=false)
    private AdmRepresentative representative;
    @OneToMany(cascade= {CascadeType.REMOVE,CascadeType.PERSIST},mappedBy="contract",fetch= FetchType.LAZY,orphanRemoval = true)
    private List<AdmInstantiatesSite> instantiatesSiteList;
    
    @Column(name = "private_key")
    private String privateKey;
    @Column(name = "system_schema")
    private String systemSchema;

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
    		if(systemLogo!=null){
	    		if(systemLogo.isEmpty()){
	    			this.systemLogo = null;
	    			return;
	    		}
				if((new File(systemLogo).exists()  || ((HttpURLConnection)new URL(systemLogo).openConnection()).getResponseCode() != HttpURLConnection.HTTP_NOT_FOUND)){
					this.systemLogo = systemLogo;    		
				}
    		}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     
    }        
    
    public String getSystemContextPath() {
		return systemContextPath;
	}

	public void setSystemContextPath(String systemContextPath) {
		this.systemContextPath = systemContextPath;
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
    
    public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getSystemSchema() {
		return systemSchema;
	}

	public void setSystemSchema(String schema) {
		this.systemSchema = schema;
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
