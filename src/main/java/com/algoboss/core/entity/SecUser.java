/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllSecUser",
            query = "select u from SecUser u")
})
@Entity
@Table(name = "sec_user")
public class SecUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "SecUser", sequenceName = "sequence_sec_user", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SecUser")
    @Column(name = "user_id")
    private Long userId;
    private String name;
    @Column(nullable = false, unique = true)
    private String login;
    private String password;
    @Column(nullable = false, unique = true)
    private String email;
    @Transient
    private String emailAgain;
    private boolean inactive;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private List<SecUserAuthorization> userAuthorizationList = new ArrayList<SecUserAuthorization>();
    @ManyToOne
    @JoinColumn(name = "contract_id")
    private AdmContract contract;
    @OneToOne(mappedBy = "user")
    private AdmRepresentative representative;
    private boolean administrator;
    private boolean boss;
    @Column(name = "mother_name")
    private String motherName;
    @Column(name = "registration_number")
    private String registrationNumber;
    private String phone;

    public List<SecUserAuthorization> getUserAuthorizationList() {
        return userAuthorizationList;
    }

    public void setUserAuthorizationList(List<SecUserAuthorization> userAuthorizationList) {
        this.userAuthorizationList = userAuthorizationList;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long usuarioId) {
        this.userId = usuarioId;
    }

    public String getName() {
        return name;
    }

    public void setName(String nome) {
        this.name = nome != null ? nome.toUpperCase() : nome;
        
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login != null ? login.toUpperCase() : login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdministrator() {
        return administrator;
    }

    public void setAdministrator(boolean administrator) {
        this.administrator = administrator;
    }

    public AdmContract getContract() {
        return contract;
    }

    public void setContract(AdmContract contract) {
        this.contract = contract;
    }

    public boolean isInactive() {
        return inactive;
    }

    public void setInactive(boolean inactive) {
        this.inactive = inactive;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email != null ? email.toUpperCase() : email;
        this.login = this.email;
    }

    public String getEmailAgain() {
        return emailAgain;
    }

    public void setEmailAgain(String emailAgain) {
        this.emailAgain = emailAgain;
    }

    public AdmRepresentative getRepresentative() {
        return representative;
    }

    public void setRepresentative(AdmRepresentative representative) {
        this.representative = representative;
    }

    public boolean isBoss() {
        return boss;
    }

    public void setBoss(boolean boss) {
        this.boss = boss;
    }

    public String getMotherName() {
        return motherName;
    }

    public void setMotherName(String motherName) {
        this.motherName = motherName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SecUser)) {
            return false;
        }
        SecUser other = (SecUser) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        //return "com.erp.model.SecUser[ id=" + userId + " ]";
        return name + " - " + email;
    }
}
