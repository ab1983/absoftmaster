/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevRequirement",
            query = "select u from DevRequirement u")
})
@Cacheable(true)
@Entity
@Table(name = "dev_requirement")
public class DevRequirement implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevRequirement", sequenceName = "sequence_dev_requirement", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevRequirement")
    @Column(name = "requirement_id")
    private Long requirementId;
    @Column(name = "requirement_name")
    private String requirementName;
    private String interfaceType;
    private String visibilitySharing;
    @Column(name = "requirement_style")
    private String requirementStyle;
    private Integer dataElementAmount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contract_id")
    private AdmContract contract;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "requirement_parent_id")
    private DevRequirement requirementParent;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "requirement_id")
    private List<DevRequirementItem> requirementItemList = new ArrayList<DevRequirementItem>();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "requirement_id")
    private List<DevComponentContainer> componentContainerList = new ArrayList<DevComponentContainer>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_class_id")
    private DevEntityClass entityClass = new DevEntityClass();
    @OneToOne(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    @JoinColumn(name = "service_id")
    private AdmService service = new AdmService();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "requirement_id")
    private List<DevReportFieldContainer> fieldContainerList = new ArrayList<DevReportFieldContainer>();
    
    public DevRequirement() {
		super();
	}

    public static DevRequirement clone(DevRequirement req) {
		//super();
    	try {
    		DevRequirement reqCloned = (DevRequirement) req.clone();
			List<DevReportFieldContainer> fieldContainerList = req.fieldContainerList;
			for (DevReportFieldContainer devReportFieldContainer : fieldContainerList) {
				
			}
			//reqCloned.
			
			return reqCloned;
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}    
    
	public List<DevComponentContainer> getComponentContainerList() {
        return componentContainerList;
    }

    public void setComponentContainerList(List<DevComponentContainer> componentContainerList) {
        this.componentContainerList = componentContainerList;
    }

    public String getRequirementName() {
        return requirementName;
    }

    public void setRequirementName(String requirementName) {
        this.requirementName = requirementName;
    }

    public String getRequirementStyle() {
		return requirementStyle;
	}

	public void setRequirementStyle(String requirementStyle) {
		this.requirementStyle = requirementStyle;
	}

	public Integer getDataElementAmount() {
        return dataElementAmount;
    }

    public void setDataElementAmount(Integer dataElementAmount) {
        this.dataElementAmount = dataElementAmount;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType;
    }

    public Long getRequirementId() {
        return requirementId;
    }

    public void setRequirementId(Long requirementId) {
        this.requirementId = requirementId;
    }

    public List<DevRequirementItem> getRequirementItemList() {
        return requirementItemList;
    }

    public void setRequirementItemList(List<DevRequirementItem> requirementItemList) {
        this.requirementItemList = requirementItemList;
    }

    public String getVisibilitySharing() {
        return visibilitySharing;
    }

    public void setVisibilitySharing(String visibilitySharing) {
        this.visibilitySharing = visibilitySharing;
    }        

	public AdmContract getContract() {
        return contract;
    }

    public void setContract(AdmContract contract) {
        this.contract = contract;
    }

    public DevRequirement getRequirementParent() {
        return requirementParent;
    }

    public void setRequirementParent(DevRequirement requirementParent) {
        this.requirementParent = requirementParent;
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

    public List<DevReportFieldContainer> getFieldContainerList() {
		return fieldContainerList;
	}

	public void setFieldContainerList(
			List<DevReportFieldContainer> fieldContainerList) {
		this.fieldContainerList = fieldContainerList;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevRequirement other = (DevRequirement) obj;
        if (this.requirementId != other.requirementId && (this.requirementId == null || !this.requirementId.equals(other.requirementId))) {
            return false;
        }
        return true;
    }

    @Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + (this.requirementId != null ? this.requirementId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevRequirement{" + "requirementId=" + requirementId + ", interfaceType=" + interfaceType + ", visibilitySharing=" + visibilitySharing + ", dataElementAmount=" + dataElementAmount + ", requirementItemList=" + requirementItemList + ", componentContainerList=" + componentContainerList + '}';
    }
}
