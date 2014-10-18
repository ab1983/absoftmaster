/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
       name="findAllStkMovement",
       query="select t from StkMovement t") 
})
@Entity
@Table(name="stk_movement")
public class StkMovement extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkMovement",sequenceName="sequence_stk_movement",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkMovement")    
    @Column(name="movement_id")
    private Long movementId;
    @Column(name="minimum_stock",precision=12,scale=4)
    private BigDecimal minimumStock = new BigDecimal(0);
    @Column(name="current_balance",precision=12,scale=4)
    private BigDecimal currentBalance = new BigDecimal(0);     
    @Column(name="average_price",precision=12,scale=4)
    private BigDecimal averagePrice = new BigDecimal(0);        
    @Column(name="date_last_movement")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dateLastMovement = new Date();
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="movement_id")
    private List<StkMovementItem> movementItemList = new ArrayList<StkMovementItem>();
    @ManyToOne()
    @JoinColumn(name="supply_item_id")
    private StkSupplyItem supplyItem;

    public StkSupplyItem getSupplyItem() {
        return supplyItem;
    }

    public void setSupplyItem(StkSupplyItem supplyItem) {
        this.supplyItem = supplyItem;
    }
           
    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public Date getDateLastMovement() {
        return dateLastMovement;
    }

    public void setDateLastMovement(Date dateLastMovement) {
        this.dateLastMovement = dateLastMovement;
    }

    public Long getMovementId() {
        return movementId;
    }

    public void setMovementId(Long movementId) {
        this.movementId = movementId;
    }

    public List<StkMovementItem> getMovementItemList() {
        return movementItemList;
    }

    public void setMovementItemList(List<StkMovementItem> movementItemList) {
        this.movementItemList = movementItemList;
    }

    public BigDecimal getMinimumStock() {
        return minimumStock;
    }

    public void setMinimumStock(BigDecimal minimumStock) {
        this.minimumStock = minimumStock;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkMovement other = (StkMovement) obj;
        if (this.movementId != other.movementId && (this.movementId == null || !this.movementId.equals(other.movementId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (this.movementId != null ? this.movementId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkMovement{" + "movementId=" + movementId + ", currentBalance=" + currentBalance + ", averagePrice=" + averagePrice + ", dateLastMovement=" + dateLastMovement + '}';
    }
    
}
