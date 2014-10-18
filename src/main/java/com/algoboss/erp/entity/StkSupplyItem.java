/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllStkSupplyItem",
    query = "select t from StkSupplyItem t")
})
@Entity
@Table(name = "stk_supply_item")
public class StkSupplyItem extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "StkSupplyItem", sequenceName = "sequence_stk_supply_item", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "StkSupplyItem")
    @Column(name = "supply_item_id")
    private Long supplyItemId;
    private String code;
    private String description;
    @Temporal(TemporalType.DATE)
    @Column(name = "date_of_registration")
    private Date dateOfRegistration = new Date();
    @ManyToOne()
    @JoinColumn(name = "supply_category_id")
    private StkSupplyCategory supplyCategory;
    @ManyToOne()
    @JoinColumn(name = "tipo_despesa_id")
    private CpgTipoDespesa tipoDespesa;
    @ManyToOne()
    @JoinColumn(name = "brand_id")
    private StkBrand brand;
    @ManyToOne()
    @JoinColumn(name = "unit_of_measure_id")
    private StkUnitOfMeasure unitOfMeasure;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "supply_item_id")
    private List<StkMovement> movement = new ArrayList();
    private boolean inactive;

    public StkBrand getBrand() {
        return brand;
    }

    public void setBrand(StkBrand brand) {
        this.brand = brand;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public StkSupplyCategory getSupplyCategory() {
        return supplyCategory;
    }

    public void setSupplyCategory(StkSupplyCategory supplyCategory) {
        this.supplyCategory = supplyCategory;
    }

    public Long getSupplyItemId() {
        return supplyItemId;
    }

    public void setSupplyItemId(Long supplyItemId) {
        this.supplyItemId = supplyItemId;
    }

    public CpgTipoDespesa getTipoDespesa() {
        return tipoDespesa;
    }

    public void setTipoDespesa(CpgTipoDespesa tipoDespesa) {
        this.tipoDespesa = tipoDespesa;
    }

    public StkUnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(StkUnitOfMeasure unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    public StkMovement getMovement() {
        if (movement == null || movement.isEmpty()) {
            this.movement.add(new StkMovement());
        }
        return movement.get(0);
    }

    public void setMovement(StkMovement movement) {
        if (this.movement.isEmpty()) {
            this.movement.add(movement);
        } else {
            this.movement.set(0, movement);
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final StkSupplyItem other = (StkSupplyItem) obj;
        if (this.supplyItemId != other.supplyItemId && (this.supplyItemId == null || !this.supplyItemId.equals(other.supplyItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.supplyItemId != null ? this.supplyItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "StkSupplyItem{" + "supplyItemId=" + supplyItemId + ", code=" + code + ", description=" + description + ", dateOfRegistration=" + dateOfRegistration + ", supplyCategory=" + supplyCategory + ", tipoDespesa=" + tipoDespesa + ", brand=" + brand + ", unitOfMeasure=" + unitOfMeasure + ", inactive=" + inactive + '}';
    }
}
