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
    @NamedQuery(name = "findAllPurSolicitation",
    query = "select t from PurSolicitation t"),
    @NamedQuery(name = "findPurSolicitationOpen",
    query = "select t from PurSolicitation t where t.status='opened'"),    
    @NamedQuery(name = "findPurSolicitationApproved",
    query = "select distinct t from PurSolicitation t join t.solicitationItemList s where s.status='approved' and t.status='approved' and s.quotation is null")
})
@Entity
@Table(name = "pur_solicitation")
public class PurSolicitation extends GenericEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "PurSolicitation", sequenceName = "sequence_pur_solicitation", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PurSolicitation")
    @Column(name = "solicitation_id")
    private Long solicitationId;
    @Column(name = "date_solicitation")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateRegistration = new Date();
    private Long number;
    private String observation;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "solicitation_id")
    private List<PurSolicitationItem> solicitationItemList = new ArrayList<PurSolicitationItem>();
    private String status = "opened";

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Long getSolicitationId() {
        return solicitationId;
    }

    public void setSolicitationId(Long solicitationId) {
        this.solicitationId = solicitationId;
    }

    public List<PurSolicitationItem> getSolicitationItemList() {
        return solicitationItemList;
    }

    public void setSolicitationItemList(List<PurSolicitationItem> solicitationItemList) {
        this.solicitationItemList = solicitationItemList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PurSolicitation other = (PurSolicitation) obj;
        if (this.solicitationId != other.solicitationId && (this.solicitationId == null || !this.solicitationId.equals(other.solicitationId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + (this.solicitationId != null ? this.solicitationId.hashCode() : 0);
        return hash;
    }
}
