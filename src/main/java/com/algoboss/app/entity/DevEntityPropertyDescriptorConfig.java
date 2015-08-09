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
    @NamedQuery(name = "findAllDevEntityPropertyDescriptorConfig",
            query = "select u from DevEntityPropertyDescriptorConfig u")
})
@Cacheable(true)
@Entity
@Table(name = "dev_entity_property_descriptor_config")
public class DevEntityPropertyDescriptorConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityPropertyDescriptorConfig", sequenceName = "sequence_dev_entity_property_descriptor_config", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityPropertyDescriptorConfig")
    @Column(name = "entity_property_descriptor_config_id")
    private Long entityPropertyDescriptorConfigId;
    @Column(name = "config_name")
    private String configName;
    @Column(name = "config_value")
    private String configValue;

    public Long getEntityPropertyDescriptorConfigId() {
        return entityPropertyDescriptorConfigId;
    }

    public void setEntityPropertyDescriptorConfigId(Long entityPropertyDescriptorConfigId) {
        this.entityPropertyDescriptorConfigId = entityPropertyDescriptorConfigId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public String getConfigValue() {
        return configValue;
    }

    public void setConfigValue(String configValue) {
        this.configValue = configValue;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 13 * hash + (this.entityPropertyDescriptorConfigId != null ? this.entityPropertyDescriptorConfigId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevEntityPropertyDescriptorConfig other = (DevEntityPropertyDescriptorConfig) obj;
        if (this.entityPropertyDescriptorConfigId != other.entityPropertyDescriptorConfigId && (this.entityPropertyDescriptorConfigId == null || !this.entityPropertyDescriptorConfigId.equals(other.entityPropertyDescriptorConfigId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevEntityPropertyDescriptorConfig{" + "entityPropertyDescriptorConfigId=" + entityPropertyDescriptorConfigId + ", configName=" + configName + ", configValue=" + configValue + '}';
    }
}
