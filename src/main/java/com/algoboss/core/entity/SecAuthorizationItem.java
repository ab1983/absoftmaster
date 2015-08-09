/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
   @NamedQuery(
       name="findAllSecAuthorizationItem",
       query="select t from SecAuthorizationItem t")
})
@Entity
@Table(name="sec_authorization_item")
public class SecAuthorizationItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="SecAuthorizationItem",sequenceName="sequence_sec_authorization_item",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="SecAuthorizationItem")
    @Column(name="authorization_item_id")
    private Long authorizationItemId;
    @Column(name="parameter_key")
    private String parameterKey;
    @Column(name="parameter_value")
    private String parameterValue;

    public Long getAuthorizationItemId() {
        return authorizationItemId;
    }

    public void setAuthorizationItemId(Long authorizationItemId) {
        this.authorizationItemId = authorizationItemId;
    }

    public String getParameterKey() {
        return parameterKey;
    }

    public void setParameterKey(String parameterKey) {
        this.parameterKey = parameterKey;
    }

    public String getParameterValue() {
        return parameterValue;
    }

    public void setParameterValue(String parameterValue) {
        this.parameterValue = parameterValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SecAuthorizationItem other = (SecAuthorizationItem) obj;
        if (this.authorizationItemId != other.authorizationItemId && (this.authorizationItemId == null || !this.authorizationItemId.equals(other.authorizationItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + (this.authorizationItemId != null ? this.authorizationItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "SecAuthorizationItem{" + "authorizationItemId=" + authorizationItemId + ", parameterKey=" + parameterKey + ", parameterValue=" + parameterValue + '}';
    }        
    
}
