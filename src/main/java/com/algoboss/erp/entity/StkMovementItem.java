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
    @NamedQuery(name = "findAllStkMovementItem",
    query = "select t from StkMovementItem t")
})
@Entity
@Table(name = "stk_movement_item")
public class StkMovementItem extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "StkMovementItem", sequenceName = "sequence_stk_movement_item", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StkMovementItem")
    @Column(name = "movement_item_id")
    private Long movementItemId;
    @Column(name = "sequence_registration")
    private Long sequenceRegistration;
    @Column(precision = 12, scale = 4)
    private BigDecimal amount;
    @Column(precision = 12, scale = 4)
    private BigDecimal unitPrice;
    @Column(name = "date_of_movement")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfMovement = new Date();
    @Column(name="movement_history")
    @Enumerated(EnumType.STRING)
    private MovementHistory movementHistory;
    @ManyToOne()
    @JoinColumn(name = "movement_id")
    private StkMovement movement;

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDateOfMovement() {
        return dateOfMovement;
    }

    public void setDateOfMovement(Date dateOfMovement) {
        this.dateOfMovement = dateOfMovement;
    }

    public MovementHistory getMovementHistory() {
        return movementHistory;
    }

    public void setMovementHistory(MovementHistory movementHistory) {
        this.movementHistory = movementHistory;
    }

    public Long getMovementItemId() {
        return movementItemId;
    }

    public void setMovementItemId(Long movementItemId) {
        this.movementItemId = movementItemId;
    }

    public Long getSequenceRegistration() {
        return sequenceRegistration;
    }

    public void setSequenceRegistration(Long sequenceRegistration) {
        this.sequenceRegistration = sequenceRegistration;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public StkMovement getMovement() {
        return movement;
    }

    public void setMovement(StkMovement movement) {
        this.movement = movement;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkMovementItem other = (StkMovementItem) obj;
        if (this.movementItemId != other.movementItemId && (this.movementItemId == null || !this.movementItemId.equals(other.movementItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + (this.movementItemId != null ? this.movementItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkMovementItem{" + "movementItemId=" + movementItemId + ", sequenceRegistration=" + sequenceRegistration + ", amount=" + amount + ", unitPrice=" + unitPrice + ", dateOfMovement=" + dateOfMovement + ", movementHistory=" + movementHistory + '}';
    }

    public enum MovementHistory {

        INPUT_INVENTORY("inventory", "input", false),
        INPUT_ORDER("order", "input", true),
        INPUT_SEPARATE("separate", "input", true),
        OUTPUT_INVENTORY("inventory", "output", false),
        OUTPUT_SOLICITATION("solicitation", "output", false);
        private String attribute;
        private String movementType;
        private boolean finacialIntegration;

        public String getAttribute() {
            return attribute;
        }

        public void setAttribute(String attribute) {
            this.attribute = attribute;
        }

        public boolean isFinacialIntegration() {
            return finacialIntegration;
        }

        public void setFinacialIntegration(boolean finacialIntegration) {
            this.finacialIntegration = finacialIntegration;
        }

        public String getMovementType() {
            return movementType;
        }

        public void setMovementType(String movementType) {
            this.movementType = movementType;
        }

        private MovementHistory(String attribute, String movementType, boolean finacialIntegration) {
            this.attribute = attribute;
            this.movementType = movementType;
            this.finacialIntegration = finacialIntegration;
        }
    }
}
