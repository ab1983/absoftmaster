/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllPurQuotationBySupplier",
       query="select t from PurQuotationBySupplier t")
})
@Entity
@Table(name="pur_quotation_by_supplier")
public class PurQuotationBySupplier extends GenericEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="PurQuotationBySupplier",sequenceName="sequence_pur_quotation_by_supplier",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="PurQuotationBySupplier")
    @Column(name="quotation_by_supplier_id")
    private Long quotationBySupplierId;
    @ManyToOne()
    @JoinColumn(name="fornecedor_id")
    private GerFornecedor fornecedor;    
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="quotation_by_supplier_id")
    private List<PurQuotationBySupplierItem> quotationBySupplierItemList = new ArrayList<PurQuotationBySupplierItem>();
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="order_id")
    private PurOrder order = new PurOrder();
    public GerFornecedor getFornecedor() {
        return fornecedor;
    }

    public void setFornecedor(GerFornecedor fornecedor) {
        this.fornecedor = fornecedor;
    }

    public Long getQuotationBySupplierId() {
        return quotationBySupplierId;
    }

    public void setQuotationBySupplierId(Long quotationBySupplierId) {
        this.quotationBySupplierId = quotationBySupplierId;
    }

    public List<PurQuotationBySupplierItem> getQuotationBySupplierItemList() {
        return quotationBySupplierItemList;
    }

    public void setQuotationBySupplierItemList(List<PurQuotationBySupplierItem> quotationBySupplierItemList) {
        this.quotationBySupplierItemList = quotationBySupplierItemList;
    }

    public PurOrder getOrder() {
        return order;
    }

    public void setOrder(PurOrder order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurQuotationBySupplier other = (PurQuotationBySupplier) obj;
        if (this.quotationBySupplierId != other.quotationBySupplierId && (this.quotationBySupplierId == null || !this.quotationBySupplierId.equals(other.quotationBySupplierId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + (this.quotationBySupplierId != null ? this.quotationBySupplierId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "PurQuotationBySupplier{" + "quotationBySupplierId=" + quotationBySupplierId + ", fornecedor=" + fornecedor + ", quotationBySupplierItemList=" + quotationBySupplierItemList + '}';
    }
        
}
