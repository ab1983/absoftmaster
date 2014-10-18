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
       name="findAllStkInputDocumentItem",
       query="select t from StkInputDocumentItem t")
})
@Entity
@Table(name="stk_input_document_item")
public class StkInputDocumentItem extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkInputDocumentItem",sequenceName="sequence_stk_input_document_item",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkInputDocumentItem")
    @Column(name="input_document_item_id")
    private Long inputDocumentItemId;
    @ManyToOne()
    @JoinColumn(name="brand_id")
    private StkBrand brand; 
    @Column(precision=12,scale=4)
    private BigDecimal amount;
    @Column(precision=12,scale=4)
    private BigDecimal unitPrice;    
    @ManyToOne()
    @JoinColumn(name = "supply_item_id")
    private StkSupplyItem supplyItem;
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="movement_item_id")
    private StkMovementItem movementItem = new StkMovementItem();    

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

    public Long getInputDocumentItemId() {
        return inputDocumentItemId;
    }

    public void setInputDocumentItemId(Long inputDocumentItemId) {
        this.inputDocumentItemId = inputDocumentItemId;
    }

    public StkSupplyItem getSupplyItem() {
        return supplyItem;
    }

    public void setSupplyItem(StkSupplyItem supplyItem) {
        this.supplyItem = supplyItem;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public StkMovementItem getMovementItem() {
        return movementItem;
    }

    public void setMovementItem(StkMovementItem movementItem) {
        this.movementItem = movementItem;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkInputDocumentItem other = (StkInputDocumentItem) obj;
        if (this.inputDocumentItemId != other.inputDocumentItemId && (this.inputDocumentItemId == null || !this.inputDocumentItemId.equals(other.inputDocumentItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.inputDocumentItemId != null ? this.inputDocumentItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkInputDocumentItem{" + "inputDocumentItemId=" + inputDocumentItemId + ", brand=" + brand + ", amount=" + amount + ", unitPrice=" + unitPrice + ", supplyItem=" + supplyItem + '}';
    }
        
    
}
