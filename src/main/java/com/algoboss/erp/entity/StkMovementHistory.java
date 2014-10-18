/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllStkMovementHistory",
       query="select t from StkMovementHistory t")
})
@Entity
@Table(name="stk_movement_history")
public class StkMovementHistory extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="StkMovementHistory",sequenceName="sequence_stk_movement_history",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="StkMovementHistory")
    @Column(name="movement_history_id")
    private Long movementHistoryId;
    private String code;
    private String description;
    private String movementType;

    public StkMovementHistory(String code, String description, String movementType) {
        this.code = code;
        this.description = description;
        this.movementType = movementType;
    }

    public StkMovementHistory() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMovementHistoryId() {
        return movementHistoryId;
    }

    public void setMovementHistoryId(Long movementHistoryId) {
        this.movementHistoryId = movementHistoryId;
    }

    public String getMovementType() {
        return movementType;
    }

    public void setMovementType(String movementType) {
        this.movementType = movementType;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkMovementHistory other = (StkMovementHistory) obj;
        if (this.movementHistoryId != other.movementHistoryId && (this.movementHistoryId == null || !this.movementHistoryId.equals(other.movementHistoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + (this.movementHistoryId != null ? this.movementHistoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkMovementHistory{" + "movementHistoryId=" + movementHistoryId + ", code=" + code + ", description=" + description + ", movementType=" + movementType + '}';
    }

    
    
}
