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
    @NamedQuery(name = "findAllDevEntityClassConfig",
            query = "select u from DevEntityClassConfig u")
})
@Cacheable(true)
@Entity
@Table(name = "dev_entity_class_config")
public class DevEntityClassConfig implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityClassConfig", sequenceName = "sequence_dev_entity_class_config", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityClassConfig")
    @Column(name = "entity_class_config_id")
    private Long entityClassConfigId;
    @Column(name = "config_name")
    private String configName;
    @Column(name = "config_value")
    private String configValue;

    public Long getEntityClassConfigId() {
        return entityClassConfigId;
    }

    public void setEntityClassConfigId(Long entityClassConfigId) {
        this.entityClassConfigId = entityClassConfigId;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }

    public Object getConfigValue() {
        Object objRet = null;

        if (configValue != null && !String.valueOf("null").equals(configValue)) {
            if ("true_false".contains(configValue)) {
                objRet = Boolean.valueOf(configValue).booleanValue();;
            } else {
                objRet = configValue;
            }
        }
        return objRet;
    }

    public void setConfigValue(Object configValue) {
        if (configName != null) {
            this.configValue = String.valueOf(configValue);
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + (this.entityClassConfigId != null ? this.entityClassConfigId.hashCode() : 0);
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
        final DevEntityClassConfig other = (DevEntityClassConfig) obj;
        if (this.entityClassConfigId != other.entityClassConfigId && (this.entityClassConfigId == null || !this.entityClassConfigId.equals(other.entityClassConfigId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "DevEntityClassConfig{" + "entityClassConfigId=" + entityClassConfigId + ", configName=" + configName + ", configValue=" + configValue + '}';
    }
}
