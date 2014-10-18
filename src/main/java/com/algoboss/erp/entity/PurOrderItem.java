/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllPurOrderItem",
       query="select t from PurOrderItem t")
})
@Entity
@Table(name="pur_order_item")
public class PurOrderItem extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurOrderItem",sequenceName="sequence_pur_order_item",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurOrderItem")
    @Column(name="order_item_id")
    private Long orderItemId;
    @ManyToOne()
    @JoinColumn(name="brand_id")
    private StkBrand brand; 
    @Column(precision=12,scale=4)
    private BigDecimal amount;
    @Column(precision=12,scale=4)
    private BigDecimal price;
    @Column(name="expected_delivery_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date expectedDeliveryDate;    
    @Column(name="performed_delivery_date")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date performedDeliveryDate;      
    @ManyToOne()
    @JoinColumn(name = "supply_item_id")
    private StkSupplyItem supplyItem;
    private String observation;
    private String status;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public StkBrand getBrand() {
        return brand;
    }

    public void setBrand(StkBrand brand) {
        this.brand = brand;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Date getPerformedDeliveryDate() {
        return performedDeliveryDate;
    }

    public void setPerformedDeliveryDate(Date performedDeliveryDate) {
        this.performedDeliveryDate = performedDeliveryDate;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public StkSupplyItem getSupplyItem() {
        return supplyItem;
    }

    public void setSupplyItem(StkSupplyItem supplyItem) {
        this.supplyItem = supplyItem;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurOrderItem other = (PurOrderItem) obj;
        if (this.orderItemId != other.orderItemId && (this.orderItemId == null || !this.orderItemId.equals(other.orderItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + (this.orderItemId != null ? this.orderItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurOrderItem{" + "orderItemId=" + orderItemId + ", brand=" + brand + ", amount=" + amount + ", price=" + price + ", expectedDeliveryDate=" + expectedDeliveryDate + ", performedDeliveryDate=" + performedDeliveryDate + ", supplyItem=" + supplyItem + ", observation=" + observation + ", status=" + status + '}';
    }        
    
}
