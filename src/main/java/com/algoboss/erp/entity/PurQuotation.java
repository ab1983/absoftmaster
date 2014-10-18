/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllPurQuotation",
       query="select t from PurQuotation t")
})
@Entity
@Table(name="pur_quotation")
public class PurQuotation extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurQuotation",sequenceName="sequence_pur_quotation",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurQuotation")
    @Column(name="quotation_id")
    private Long quotationId;
    @Column(name="date_registration")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dateRegistration = new Date();
    private Long number;
    private String observation;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="quotation_id")
    private List<PurQuotationBySupplier> quotationBySupplierList = new ArrayList<PurQuotationBySupplier>();
    @OneToMany(cascade= CascadeType.MERGE,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="quotation_id")
    private List<PurSolicitationItem> solicitationItemList = new ArrayList<PurSolicitationItem>();    
    private String status = "opened";

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public List<PurQuotationBySupplier> getQuotationBySupplierList() {
        return quotationBySupplierList;
    }

    public void setQuotationBySupplierList(List<PurQuotationBySupplier> quotationBySupplierList) {
        this.quotationBySupplierList = quotationBySupplierList;
    }

    public Long getQuotationId() {
        return quotationId;
    }

    public void setQuotationId(Long quotationId) {
        this.quotationId = quotationId;
    }

    public List<PurSolicitationItem> getSolicitationItemList() {
        return solicitationItemList;
    }

    public void setSolicitationItemList(List<PurSolicitationItem> solicitationItemList) {
        this.solicitationItemList = solicitationItemList;
    }



    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurQuotation other = (PurQuotation) obj;
        if (this.quotationId != other.quotationId && (this.quotationId == null || !this.quotationId.equals(other.quotationId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.quotationId != null ? this.quotationId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurQuotation{" + "quotationId=" + quotationId + ", dateRegistration=" + dateRegistration + ", number=" + number + ", observation=" + observation + ", quotationBySupplierList=" + quotationBySupplierList + ", solicitationItemList=" + solicitationItemList + ", status=" + status + '}';
    }        
    
}
