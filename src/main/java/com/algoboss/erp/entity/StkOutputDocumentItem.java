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
       name="findAllStkOutputDocumentItem",
       query="select t from StkOutputDocumentItem t")
})
@Entity
@Table(name="stk_output_document_item")
public class StkOutputDocumentItem extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkOutputDocumentItem",sequenceName="sequence_stk_output_document_item",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkOutputDocumentItem")
    @Column(name="output_document_item_id")
    private Long outputDocumentItemId;
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

    public Long getOutputDocumentItemId() {
        return outputDocumentItemId;
    }

    public void setOutputDocumentItemId(Long outputDocumentItemId) {
        this.outputDocumentItemId = outputDocumentItemId;
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
        final StkOutputDocumentItem other = (StkOutputDocumentItem) obj;
        if (this.outputDocumentItemId != other.outputDocumentItemId && (this.outputDocumentItemId == null || !this.outputDocumentItemId.equals(other.outputDocumentItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (this.outputDocumentItemId != null ? this.outputDocumentItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkOutputDocumentItem{" + "outputDocumentItemId=" + outputDocumentItemId + ", brand=" + brand + ", amount=" + amount + ", unitPrice=" + unitPrice + ", supplyItem=" + supplyItem + '}';
    }
        
    
}
