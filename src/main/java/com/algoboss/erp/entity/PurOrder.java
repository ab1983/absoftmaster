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
       name="findAllPurOrder",
       query="select t from PurOrder t"),
   @NamedQuery(
       name="findAllPurOrderOpen",
       query="select t from PurOrder t where t.status = 'opened'")   
})
@Entity
@Table(name="pur_order")
public class PurOrder extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurOrder",sequenceName="sequence_pur_order",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurOrder")
    @Column(name="order_id")
    private Long orderId;
    @Column(name="date_registration")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dateRegistration = new Date();
    private Long number;
    private String observation;
    @ManyToOne() 
    @JoinColumn(name="fornecedor_id")
    private GerFornecedor fornecedor;      
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="order_id")
    private List<PurOrderItem> orderItemList = new ArrayList<PurOrderItem>();   
    private String status = "opened";
    @OneToOne(mappedBy="order",fetch=FetchType.LAZY)
    private PurQuotationBySupplier quotationBySupplier;
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<PurOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<PurOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GerFornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(GerFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public PurQuotationBySupplier getQuotationBySupplier() {
        return quotationBySupplier;
    }

    public void setQuotationBySupplier(PurQuotationBySupplier quotationBySupplier) {
        this.quotationBySupplier = quotationBySupplier;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurOrder other = (PurOrder) obj;
        if (this.orderId != other.orderId && (this.orderId == null || !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.orderId != null ? this.orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurOrder{" + "orderId=" + orderId + ", dateRegistration=" + dateRegistration + ", number=" + number + ", observation=" + observation + ", orderItemList=" + orderItemList + ", status=" + status + '}';
    }
    
}
