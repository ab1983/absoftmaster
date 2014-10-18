/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import com.algoboss.erp.util.AlgoUtil;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.List;
import java.util.Random;

import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevEntityPropertyDescriptor",
            query = "select u from DevEntityPropertyDescriptor u")
})
@Cacheable(true)
@Entity
@Table(name = "dev_entity_property_descriptor")
public class DevEntityPropertyDescriptor implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityPropertyDescriptor", sequenceName = "sequence_dev_entity_property_descriptor", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityPropertyDescriptor")
    @Column(name = "entity_property_descriptor_id")
    private Long entityPropertyDescriptorId;
    @Column(name = "property_label")
    private String propertyLabel;
    @Column(name = "property_name")
    private String propertyName;
    @Column(name = "property_type")
    private String propertyType;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "property_class_id")
    private DevEntityClass propertyClass;
    @Column(name = "property_identifier")
    private boolean propertyIdentifier;
    @Column(name = "property_required")
    private boolean propertyRequired;    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "entity_property_descriptor_id")
    private List<DevEntityPropertyDescriptorConfig> entityPropertyDescriptorConfigList;    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "entity_class_id")
    private DevEntityClass entityClassParent;    
    
    public Long getEntityPropertyDescriptorId() {
        return entityPropertyDescriptorId;
    }
    
    public void setEntityPropertyDescriptorId(Long entityPropertyDescriptorId) {
        this.entityPropertyDescriptorId = entityPropertyDescriptorId;
    }
    
    public String getPropertyLabel() {
        return propertyLabel;
    }
    
    public void setPropertyLabel(String propertyLabel) {
        this.propertyLabel = propertyLabel;
        setPropertyName(propertyLabel);
    }
    
    public String getPropertyName() {
        return propertyName;
    }
    
    public void setPropertyName(String propertyName) {
        if (this.propertyName == null && propertyName != null && !propertyName.isEmpty()) {
            this.propertyName = AlgoUtil.normalizerName(propertyName);
        }
    }
    
    public String getPropertyType() {
        return propertyType;
    }
    
    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }
    
    public DevEntityClass getPropertyClass() {
        return propertyClass;
    }
    
    public void setPropertyClass(DevEntityClass propertyClass) {
        this.propertyClass = propertyClass;
    }
    
    public boolean isPropertyIdentifier() {
        return propertyIdentifier;
    }
    
    public void setPropertyIdentifier(boolean propertyIdentifier) {
        this.propertyIdentifier = propertyIdentifier;
    }
    
    public boolean isPropertyRequired() {
        return propertyRequired;
    }
    
    public void setPropertyRequired(boolean propertyRequired) {
        this.propertyRequired = propertyRequired;
    }
    
    public List<DevEntityPropertyDescriptorConfig> getEntityPropertyDescriptorConfigList() {
        return entityPropertyDescriptorConfigList;
    }
    
    public void setEntityPropertyDescriptorConfigList(List<DevEntityPropertyDescriptorConfig> entityPropertyDescriptorConfigList) {
        this.entityPropertyDescriptorConfigList = entityPropertyDescriptorConfigList;
    }
    
    public DevEntityClass getEntityClassParent() {
        return entityClassParent;
    }
    
    public void setEntityClassParent(DevEntityClass entityClassParent) {
        this.entityClassParent = entityClassParent;
    }    
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevEntityPropertyDescriptor other = (DevEntityPropertyDescriptor) obj;
        if (this.entityPropertyDescriptorId != other.entityPropertyDescriptorId && (this.entityPropertyDescriptorId == null || !this.entityPropertyDescriptorId.equals(other.entityPropertyDescriptorId))) {
            return false;
        }
        if (this.entityPropertyDescriptorId == other.entityPropertyDescriptorId && this.entityPropertyDescriptorId == null && this != obj) {
            return false;
        }
        return true;
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + (this.entityPropertyDescriptorId != null ? this.entityPropertyDescriptorId.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString() {
        return "DevEntityPropertyDescriptor{" + "entityPropertyDescriptorId=" + entityPropertyDescriptorId + ", propertyName=" + propertyName + ", propertyType=" + propertyType + '}';
    }
}
