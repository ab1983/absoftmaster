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
    @NamedQuery(name = "findAllGerBankAccount",
    query = "select t from GerBankAccount t")
})
@Entity
@Table(name = "ger_bank_account")
public class GerBankAccount implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "GerBankAccount", sequenceName = "sequence_ger_bank_account", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GerBankAccount")
    @Column(name = "bank_account_id")
    private Long bankAccountId;
    @ManyToOne()
    @JoinColumn(name = "bank_id")
    private GerBank bank;    
    @Column(name = "agency_bank")
    private String agencyBank;
    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Column(name = "account_number")
    private String accountNumber;

    public GerBank getBank() {
        return bank;
    }

    public void setBank(GerBank bank) {
        this.bank = bank;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAgencyBank() {
        return agencyBank;
    }

    public void setAgencyBank(String agencyBank) {
        this.agencyBank = agencyBank;
    }

    public Long getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Long bankAccountId) {
        this.bankAccountId = bankAccountId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerBankAccount other = (GerBankAccount) obj;
        if (this.bankAccountId != other.bankAccountId && (this.bankAccountId == null || !this.bankAccountId.equals(other.bankAccountId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + (this.bankAccountId != null ? this.bankAccountId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GerBankAccount{" + "bankAccountId=" + bankAccountId + ", bank=" + bank + ", agencyBank=" + agencyBank + ", accountType=" + accountType + ", accountNumber=" + accountNumber + '}';
    }
    
    public enum AccountType {

        CURRENT_ACCOUNT("currentAccount"),
        SAVINGS_ACCOUNT("savingsAccount");

        private String accountTypeName;

        private AccountType(String accountTypeName) {
            this.accountTypeName = accountTypeName;
        }

        public String getAccountTypeName() {
            return accountTypeName;
        }

        public void setAccountTypeName(String accountTypeName) {
            this.accountTypeName = accountTypeName;
        }                        
        
    }
}
