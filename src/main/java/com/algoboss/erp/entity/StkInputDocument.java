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
       name="findAllStkInputDocument",
       query="select t from StkInputDocument t") 
})
@Entity
@Table(name="stk_input_document")
public class StkInputDocument extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkInputDocument",sequenceName="sequence_stk_input_document",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkInputDocument")
    @Column(name="input_document_id")
    private Long inputDocumentId;
    @Column(name="document_number")
    private String documentNumber; 
    @Column(name="serial_number")
    private String serialNumber;
    private Long portion;    
    @ManyToOne()
    @JoinColumn(name="tipo_documento_id")
    private GerTipoDocumento tipoDocumento;    
    @Column(name="input_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date inputDate = new Date();
    @Column(name="issuance_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date issuanceDate = new Date();
    @Column(name="expiration_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date expirationDate = new Date();  
    @OneToMany(cascade= {CascadeType.MERGE,CascadeType.DETACH,CascadeType.REFRESH},fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="input_document_id")
    private List<PurOrder> orderList = new ArrayList<PurOrder>();  
    @ManyToOne()
    @JoinColumn(name="movement_history_id")
    private StkMovementHistory movementHistory;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="input_document_id")
    private List<StkInputDocumentItem> inputDocumentItemList = new ArrayList<StkInputDocumentItem>();      
    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name="fornecedor_id")
    private GerFornecedor fornecedor;     
    
    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public Long getInputDocumentId() {
        return inputDocumentId;
    }

    public void setInputDocumentId(Long inputDocumentId) {
        this.inputDocumentId = inputDocumentId;
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

    public List<PurOrder> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<PurOrder> orderList) {
        this.orderList = orderList;
    }

    public List<StkInputDocumentItem> getInputDocumentItemList() {
        return inputDocumentItemList;
    }

    public void setInputDocumentItemList(List<StkInputDocumentItem> inputDocumentItemList) {
        this.inputDocumentItemList = inputDocumentItemList;
    }

    public GerFornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(GerFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkInputDocument other = (StkInputDocument) obj;
        if (this.inputDocumentId != other.inputDocumentId && (this.inputDocumentId == null || !this.inputDocumentId.equals(other.inputDocumentId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.inputDocumentId != null ? this.inputDocumentId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkInputDocument{" + "inputDocumentId=" + inputDocumentId + ", inputDate=" + inputDate + ", issuanceDate=" + issuanceDate + ", expirationDate=" + expirationDate + ", orderList=" + orderList + ", movementHistory=" + movementHistory + '}';
    }
    
    
}
