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
    @NamedQuery(name = "findAllPurSolicitationItem",
    query = "select t from PurSolicitationItem t")
})
@Entity
@Table(name = "pur_solicitation_item")
public class PurSolicitationItem extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PurSolicitationItem")
    @SequenceGenerator(name = "PurSolicitationItem", sequenceName = "sequence_pur_solicitation_item", allocationSize = 1)
    @Column(name = "solicitation_item_id")
    private Long solicitationItemId;
    @ManyToOne()
    @JoinColumn(name = "supply_item_id")
    private StkSupplyItem supplyItem;
    @Column(precision = 12, scale = 4)
    private BigDecimal amount;
    private String observation;
    private String status;
    @ManyToOne()
    @JoinColumn(name="quotation_id")
    private PurQuotation quotation;

    public PurQuotation getQuotation() {
        return quotation;
    }

    public void setQuotation(PurQuotation quotation) {
        this.quotation = quotation;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getSolicitationItemId() {
        return solicitationItemId;
    }

    public void setSolicitationItemId(Long solicitationItemId) {
        this.solicitationItemId = solicitationItemId;
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
        final PurSolicitationItem other = (PurSolicitationItem) obj;
        if (this.solicitationItemId != other.solicitationItemId && (this.solicitationItemId == null || !this.solicitationItemId.equals(other.solicitationItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + (this.solicitationItemId != null ? this.solicitationItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurSolicitationItem{" + "solicitationItemId=" + solicitationItemId + ", supplyItem=" + supplyItem + ", amount=" + amount + ", observation=" + observation + ", status=" + status + '}';
    }
}
