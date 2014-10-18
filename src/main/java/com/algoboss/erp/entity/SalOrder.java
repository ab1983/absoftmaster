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
       name="findAllSalOrder",
       query="select t from SalOrder t"),
   @NamedQuery(
       name="findAllSalOrderOpen",
       query="select t from SalOrder t where t.status = 'opened'")   
})
@Entity
@Table(name="sal_order")
public class SalOrder extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SalOrder",sequenceName="sequence_sal_order",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SalOrder")
    @Column(name="order_id")
    private Long orderId;
    @Column(name="date_registration")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dateRegistration = new Date();
    private Long number;
    private String observation;
    @ManyToOne()
    @JoinColumn(name="customer_id",referencedColumnName="cliente_id")
    private GerCliente customer;      
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="order_id")
    private List<SalOrderItem> orderItemList = new ArrayList<SalOrderItem>();   
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

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<SalOrderItem> getOrderItemList() {
        return orderItemList;
    }

    public void setOrderItemList(List<SalOrderItem> orderItemList) {
        this.orderItemList = orderItemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public GerCliente getCustomer() {
        return customer;
    }

    public void setCustomer(GerCliente customer) {
        this.customer = customer;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SalOrder other = (SalOrder) obj;
        if (this.orderId != other.orderId && (this.orderId == null || !this.orderId.equals(other.orderId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + (this.orderId != null ? this.orderId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SalOrder{" + "orderId=" + orderId + ", dateRegistration=" + dateRegistration + ", number=" + number + ", observation=" + observation + ", customer=" + customer + ", orderItemList=" + orderItemList + ", status=" + status + '}';
    }
    
}
