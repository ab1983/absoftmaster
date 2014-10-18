/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllPurQuotationBySupplierItem",
       query="select t from PurQuotationBySupplierItem t")
})
@Entity
@Table(name="pur_quotation_by_supplier_item")
public class PurQuotationBySupplierItem extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurQuotationBySupplierItem",sequenceName="sequence_pur_quotation_by_supplier_item",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurQuotationBySupplierItem")
    @Column(name="quotation_by_supplier_item_id")
    private Long quotationBySupplierItemId;
    @ManyToOne()
    @JoinColumn(name="brand_id")
    private StkBrand brand; 
    @Column(precision=12,scale=4)
    private BigDecimal amount;
    @Column(name="amount_available",precision=2)
    private BigDecimal amountAvailable;
    @Column(precision=12,scale=4)
    private BigDecimal price;
    @Column(name="delivery_time_in_days")
    private Long deliveryTimeInDays;
    @ManyToOne()
    @JoinColumn(name = "supply_item_id")
    private StkSupplyItem supplyItem;
    private String observation;
    private String status;

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }        
    
    public BigDecimal getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(BigDecimal amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public StkBrand getBrand() {
        return brand;
    }

    public void setBrand(StkBrand brand) {
        this.brand = brand;
    }

    public Long getDeliveryTimeInDays() {
        return deliveryTimeInDays;
    }

    public void setDeliveryTimeInDays(Long deliveryTimeInDays) {
        this.deliveryTimeInDays = deliveryTimeInDays;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Long getQuotationBySupplierItemId() {
        return quotationBySupplierItemId;
    }

    public void setQuotationBySupplierItemId(Long quotationBySupplierItemId) {
        this.quotationBySupplierItemId = quotationBySupplierItemId;
    }

    public StkSupplyItem getSupplyItem() {
        return supplyItem;
    }

    public void setSupplyItem(StkSupplyItem supplyItem) {
        this.supplyItem = supplyItem;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurQuotationBySupplierItem other = (PurQuotationBySupplierItem) obj;
        if (this.quotationBySupplierItemId != other.quotationBySupplierItemId && (this.quotationBySupplierItemId == null || !this.quotationBySupplierItemId.equals(other.quotationBySupplierItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + (this.quotationBySupplierItemId != null ? this.quotationBySupplierItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurQuotationBySupplierItem{" + "quotationBySupplierItemId=" + quotationBySupplierItemId + ", brand=" + brand + ", amountAvailable=" + amountAvailable + ", price=" + price + ", deliveryTimeInDays=" + deliveryTimeInDays + '}';
    }            
}
