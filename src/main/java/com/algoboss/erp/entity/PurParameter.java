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
       name="findAllPurParameter",
       query="select t from PurParameter t")
})
@Entity
@Table(name="pur_parameter")
public class PurParameter extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurParameter",sequenceName="sequence_pur_parameter",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurParameter")
    @Column(name="parameter_id")
    private Long parameterId;
    @Column(name="email_for_stock_solicitation")
    private String emailForStockSolicitation;
    @Column(name="email_for_purchase_solicitation")
    private String emailForPurchaseSolicitation;    
    @Column(name="stock_solicitation_approve_required")
    private boolean stockSolicitationApproveRequired;
    @Column(name="purchase_solicitation_approve_required")
    private boolean purchaseSolicitationApproveRequired;    
    @Column(name="order_approve_required")
    private boolean orderApproveRequired;
    @Column(name="order_variation_approve_required")
    private boolean orderVariationAppoveRequired;

    public String getEmailForPurchaseSolicitation() {
        return emailForPurchaseSolicitation;
    }

    public void setEmailForPurchaseSolicitation(String emailForPurchaseSolicitation) {
        this.emailForPurchaseSolicitation = emailForPurchaseSolicitation;
    }

    public String getEmailForStockSolicitation() {
        return emailForStockSolicitation;
    }

    public void setEmailForStockSolicitation(String emailForStockSolicitation) {
        this.emailForStockSolicitation = emailForStockSolicitation;
    }

    public boolean isOrderApproveRequired() {
        return orderApproveRequired;
    }

    public void setOrderApproveRequired(boolean orderApproveRequired) {
        this.orderApproveRequired = orderApproveRequired;
    }

    public boolean isOrderVariationAppoveRequired() {
        return orderVariationAppoveRequired;
    }

    public void setOrderVariationAppoveRequired(boolean orderVariationAppoveRequired) {
        this.orderVariationAppoveRequired = orderVariationAppoveRequired;
    }

    public Long getParameterId() {
        return parameterId;
    }

    public void setParameterId(Long parameterId) {
        this.parameterId = parameterId;
    }

    public boolean isPurchaseSolicitationApproveRequired() {
        return purchaseSolicitationApproveRequired;
    }

    public void setPurchaseSolicitationApproveRequired(boolean purchaseSolicitationApproveRequired) {
        this.purchaseSolicitationApproveRequired = purchaseSolicitationApproveRequired;
    }

    public boolean isStockSolicitationApproveRequired() {
        return stockSolicitationApproveRequired;
    }

    public void setStockSolicitationApproveRequired(boolean stockSolicitationApproveRequired) {
        this.stockSolicitationApproveRequired = stockSolicitationApproveRequired;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurParameter other = (PurParameter) obj;
        if (this.parameterId != other.parameterId && (this.parameterId == null || !this.parameterId.equals(other.parameterId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.parameterId != null ? this.parameterId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurParameter{" + "parameterId=" + parameterId + ", emailForStockSolicitation=" + emailForStockSolicitation + ", emailForPurchaseSolicitation=" + emailForPurchaseSolicitation + ", stockSolicitationApproveRequired=" + stockSolicitationApproveRequired + ", purchaseSolicitationApproveRequired=" + purchaseSolicitationApproveRequired + ", orderApproveRequired=" + orderApproveRequired + ", orderVariationAppoveRequired=" + orderVariationAppoveRequired + '}';
    }    

}
