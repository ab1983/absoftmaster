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
   @NamedQuery(
       name="findAllGerLocation",
       query="select t from GerLocation t")
})
@Entity
@Table(name="ger_location")
public class GerLocation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name="GerLocation",sequenceName="sequence_ger_location",allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator="GerLocation")
    @Column(name="location_id")
    private Long locationId;
    private String address;
    private Long number;
    private String complement;
    private String postalcode;
    private String neighborhood;
    private String email;
    private String phone;
    private String fax;
    private String city;
    private String state;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GerLocation other = (GerLocation) obj;
        if (this.locationId != other.locationId && (this.locationId == null || !this.locationId.equals(other.locationId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.locationId != null ? this.locationId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "GerLocation{" + "locationId=" + locationId + ", address=" + address + ", number=" + number + ", complement=" + complement + ", postalcode=" + postalcode + ", neighborhood=" + neighborhood + ", email=" + email + ", phone=" + phone + ", fax=" + fax + ", city=" + city + ", state=" + state + '}';
    }
                
}
