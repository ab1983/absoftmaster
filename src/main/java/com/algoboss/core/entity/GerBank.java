/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllGerBank",
    query = "select t from GerBank t")
})
@Entity
@Table(name = "ger_bank")
public class GerBank implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "GerBank", sequenceName = "sequence_ger_bank", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GerBank")
    @Column(name = "bank_id")
    private Long bankId;
    @Column(name = "code")
    private String code;    
    @Column(name = "name")
    private String name;
    @Column(name="home_page")
    private String homePage;
    
    public Long getBankId() {
        return bankId;
    }

    public void setBankId(Long bankId) {
        this.bankId = bankId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerBank other = (GerBank) obj;
        if (this.bankId != other.bankId && (this.bankId == null || !this.bankId.equals(other.bankId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + (this.bankId != null ? this.bankId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GerBank{" + "bankId=" + bankId + ", code=" + code + ", name=" + name + '}';
    }
    
}
