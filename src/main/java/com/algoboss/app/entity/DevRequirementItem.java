/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevRequirementItem",
    query = "select u from DevRequirementItem u")
})
@Entity
@Table(name="dev_requirement_item")
public class DevRequirementItem implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevRequirementItem", sequenceName = "sequence_dev_requirement_item", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevRequirementItem")
    @Column(name = "requirement_item_id")
    private Long requirementItemId;
    private String description;
    @Lob
    private byte[] file;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public Long getRequirementItemId() {
        return requirementItemId;
    }

    public void setRequirementItemId(Long requirementItemId) {
        this.requirementItemId = requirementItemId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevRequirementItem other = (DevRequirementItem) obj;
        if (this.requirementItemId != other.requirementItemId && (this.requirementItemId == null || !this.requirementItemId.equals(other.requirementItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + (this.requirementItemId != null ? this.requirementItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return "DevRequirementItem{" + "requirementItemId=" + requirementItemId + ", description=" + description + ", file=" + file + '}';
    }
            
}
