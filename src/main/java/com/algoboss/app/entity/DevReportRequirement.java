/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.entity;

import java.io.Serializable;
import java.util.ArrayList;
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

import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.AdmService;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevReportRequirement",
            query = "select u from DevReportRequirement u")
})
@Entity
@Table(name = "dev_report_requirement")
public class DevReportRequirement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevReportRequirement", sequenceName = "sequence_dev_report_requirement", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevReportRequirement")
    @Column(name = "report_requirement_id")
    private Long reportRequirementId;
    @Column(name = "report_requirement_name")
    private String reportRequirementName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private AdmContract contract;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "report_requirement_id")
    private List<DevReportFieldContainer> fieldContainerList = new ArrayList<DevReportFieldContainer>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_class_id")
    private DevEntityClass entityClass = new DevEntityClass();
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private AdmService service;
    @Column(name = "report_datasource")
    private String reportDatasource;
    @Column(name = "report_file")
    private String reportFile;

    public Long getReportRequirementId() {
        return reportRequirementId;
    }

    public void setReportRequirementId(Long reportRequirementId) {
        this.reportRequirementId = reportRequirementId;
    }

    public String getReportRequirementName() {
        return reportRequirementName;
    }

    public void setReportRequirementName(String reportRequirementName) {
        this.reportRequirementName = reportRequirementName;
    }

    public AdmContract getContract() {
        return contract;
    }

    public void setContract(AdmContract contract) {
        this.contract = contract;
    }

    public List<DevReportFieldContainer> getFieldContainerList() {
        return fieldContainerList;
    }

    public void setFieldContainerList(List<DevReportFieldContainer> fieldList) {
        this.fieldContainerList = fieldList;
    }

    public DevEntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(DevEntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public AdmService getService() {
        return service;
    }

    public void setService(AdmService service) {
        this.service = service;
    }    
    
    public String getReportDatasource() {
		return reportDatasource;
	}

	public void setReportDatasource(String reportDatasource) {
		this.reportDatasource = reportDatasource;
	}

	public String getReportFile() {
		return reportFile;
	}

	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}

	@Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.reportRequirementId != null ? this.reportRequirementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevReportRequirement other = (DevReportRequirement) obj;
        if (this.reportRequirementId != other.reportRequirementId && (this.reportRequirementId == null || !this.reportRequirementId.equals(other.reportRequirementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevReportRequirement{" + "reportRequirementId=" + reportRequirementId + ", reportRequirementName=" + reportRequirementName + ", contract=" + contract + ", fieldList=" + fieldContainerList + ", entityClass=" + entityClass + ", service=" + service + '}';
    }
    
}
