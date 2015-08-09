/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllSecUserAuthorization",
       query="select t from SecUserAuthorization t")
})
@Entity
@Table(name="sec_user_authorization")
public class SecUserAuthorization implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SecUserAuthorization",sequenceName="sequence_sec_user_authorization",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SecUserAuthorization")
    @Column(name="user_authorization_id")
    private Long userAuthorizationId;
    @OneToOne()
    @JoinColumn(name="service_contract_id",nullable=false)    
    private AdmServiceContract serviceContract = new AdmServiceContract();    
    @OneToOne(cascade= CascadeType.ALL,fetch=FetchType.EAGER,orphanRemoval=true)   
    @JoinColumn(name="authorization_id",nullable=false)
    private SecAuthorization authorization = new SecAuthorization();
    @Column(name = "hit_counter")
    private long hitCounter;

    public long getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(long hitCounter) {
        this.hitCounter = hitCounter;
    }
    
    public SecAuthorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(SecAuthorization authorization) {
        this.authorization = authorization;
    }

    public AdmServiceContract getServiceContract() {
        return serviceContract;
    }

    public void setServiceContract(AdmServiceContract serviceContract) {
        this.serviceContract = serviceContract;
    }

    public Long getUserAuthorizationId() {
        return userAuthorizationId;
    }

    public void setUserAuthorizationId(Long userAuthorizationId) {
        this.userAuthorizationId = userAuthorizationId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecUserAuthorization other = (SecUserAuthorization) obj;
        if (this.userAuthorizationId != other.userAuthorizationId && (this.userAuthorizationId == null || !this.userAuthorizationId.equals(other.userAuthorizationId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + (this.userAuthorizationId != null ? this.userAuthorizationId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SecUserAuthorization{" + "userAuthorizationId=" + userAuthorizationId +  ", serviceContract=" + serviceContract + ", authorization=" + authorization + '}';
    }    
    
}
