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
       name="findAllStkOutputDocument",
       query="select t from StkOutputDocument t") 
})
@Entity
@Table(name="stk_output_document")
public class StkOutputDocument extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkOutputDocument",sequenceName="sequence_stk_output_document",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkOutputDocument")
    @Column(name="output_document_id")
    private Long outputDocumentId;
    @Column(name="document_number")
    private String documentNumber; 
    @Column(name="serial_number")
    private String serialNumber;
    private Long portion;    
    @ManyToOne()
    @JoinColumn(name="tipo_documento_id")
    private GerTipoDocumento tipoDocumento;      
    @Column(name="output_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date outputDate = new Date();
    @Column(name="issuance_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date issuanceDate = new Date();
    @Column(name="expiration_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date expirationDate = new Date();  
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="output_document_id")
    private List<PurSolicitation> solicitationList = new ArrayList<PurSolicitation>();  
    @ManyToOne()
    @JoinColumn(name="movement_history_id")
    private StkMovementHistory movementHistory;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="output_document_id")
    private List<StkOutputDocumentItem> outputDocumentItemList = new ArrayList<StkOutputDocumentItem>();

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public Long getPortion() {
        return portion;
    }

    public void setPortion(Long portion) {
        this.portion = portion;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public GerTipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(GerTipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getIssuanceDate() {
        return issuanceDate;
    }

    public void setIssuanceDate(Date issuanceDate) {
        this.issuanceDate = issuanceDate;
    }

    public StkMovementHistory getMovementHistory() {
        return movementHistory;
    }

    public void setMovementHistory(StkMovementHistory movementHistory) {
        this.movementHistory = movementHistory;
    }

    public Date getOutputDate() {
        return outputDate;
    }

    public void setOutputDate(Date outputDate) {
        this.outputDate = outputDate;
    }

    public Long getOutputDocumentId() {
        return outputDocumentId;
    }

    public void setOutputDocumentId(Long outputDocumentId) {
        this.outputDocumentId = outputDocumentId;
    }

    public List<PurSolicitation> getSolicitationList() {
        return solicitationList;
    }

    public void setSolicitationList(List<PurSolicitation> solicitationList) {
        this.solicitationList = solicitationList;
    }

    public List<StkOutputDocumentItem> getOutputDocumentItemList() {
        return outputDocumentItemList;
    }

    public void setOutputDocumentItemList(List<StkOutputDocumentItem> outputDocumentItemList) {
        this.outputDocumentItemList = outputDocumentItemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkOutputDocument other = (StkOutputDocument) obj;
        if (this.outputDocumentId != other.outputDocumentId && (this.outputDocumentId == null || !this.outputDocumentId.equals(other.outputDocumentId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + (this.outputDocumentId != null ? this.outputDocumentId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkOutputDocument{" + "outputDocumentId=" + outputDocumentId + ", outputDate=" + outputDate + ", issuanceDate=" + issuanceDate + ", expirationDate=" + expirationDate + ", solicitationList=" + solicitationList + ", movementHistory=" + movementHistory + '}';
    }

    
    
}
