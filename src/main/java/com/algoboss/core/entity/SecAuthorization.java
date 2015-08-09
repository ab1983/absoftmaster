/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

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
   @NamedQuery(
       name="findAllSecAuthorization",
       query="select t from SecAuthorization t")
})
@Entity
@Table(name="sec_authorization")
public class SecAuthorization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SecAuthorization",sequenceName="sequence_sec_authorization",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SecAuthorization")
    @Column(name="authorization_id")
    private Long authorizationId;
    @Column(name="date_registration")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date dateRegistration = new Date();    
    @Column(name="validade")
    @Temporal(javax.persistence.TemporalType.DATE)    
    private Date validate = new Date();
    @Column(name="read_only")
    private boolean readOnly;
    private boolean allowed;
    @OneToMany(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)
    @JoinColumn(name="authorization_id")
    private List<SecAuthorizationItem> authorizationItemList = new ArrayList<SecAuthorizationItem>();
    
    public Long getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Long authorizationId) {
        this.authorizationId = authorizationId;
    }

    public Date getDateRegistration() {
        return dateRegistration;
    }

    public void setDateRegistration(Date dateRegistration) {
        this.dateRegistration = dateRegistration;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public Date getValidate() {
        return validate;
    }

    public void setValidate(Date validate) {
        this.validate = validate;
    }

    public List<SecAuthorizationItem> getAuthorizationItemList() {
        return authorizationItemList;
    }

    public void setAuthorizationItemList(List<SecAuthorizationItem> authorizationItemList) {
        this.authorizationItemList = authorizationItemList;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecAuthorization other = (SecAuthorization) obj;
        if (this.authorizationId != other.authorizationId && (this.authorizationId == null || !this.authorizationId.equals(other.authorizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.authorizationId != null ? this.authorizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SecAuthorization{" + "authorizationId=" + authorizationId + ", validate=" + validate + ", readOnly=" + readOnly + '}';
    }
    
    
}
