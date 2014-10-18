/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.persistence.*;

import com.algoboss.erp.face.GerLoginBean;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevEntityObject",
            query = "select u from DevEntityObject u")
})
@Entity
@Table(name = "dev_entity_object")
public class DevEntityObject extends GenericEntity implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityObject", sequenceName = "sequence_dev_entity_object", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityObject")
    @OrderBy
    @Column(name = "entity_object_id")
    private Long entityObjectId;
    @Temporal(TemporalType.DATE)
    @Column(name = "registration_date")
    private Date registrationDate = new Date();
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "entity_object_id")
    @OrderBy
    private List<DevEntityPropertyValue> entityPropertyValueList = new ArrayList();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_class_id")
    private DevEntityClass entityClass;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="parent_id")    
    private DevEntityPropertyValue propertyParent;    
    @OneToOne(fetch=FetchType.LAZY)   
    @JoinColumn(name="user_id")    
    protected SecUser user;
    @Transient
    private Map<String,DevEntityPropertyValue> entityPropertyValueMap = new HashMap<String,DevEntityPropertyValue>();

    public DevEntityObject() {
    }

    public DevEntityObject(DevEntityObject obj) {
        if (obj != null) {
            //this.entityObjectId = obj.entityObjectId;
            //this.registrationDate = obj.registrationDate;
            this.entityClass = obj.entityClass;
            for (DevEntityPropertyValue devEntityPropertyValue : obj.entityPropertyValueList) {
                this.entityPropertyValueList.add(new DevEntityPropertyValue(devEntityPropertyValue));
            }
        }
    }
    
    public DevEntityPropertyValue getPropObj(String propName) {
        for (DevEntityPropertyValue devEntityPropertyValue : entityPropertyValueList) {
            if (devEntityPropertyValue.getEntityPropertyDescriptor().getPropertyName().equals(propName)) {
                return devEntityPropertyValue;
            }
        }
        return null;
    }
    
    public Object getProp(String propName) {
        Object ret = null;
        for (DevEntityPropertyValue devEntityPropertyValue : entityPropertyValueList) {
            if (devEntityPropertyValue.getEntityPropertyDescriptor().getPropertyName().equals(propName)) {
                ret = getPropObj(propName).getVal();
                break;
            }
        }
        return ret;
    }

    public String $date(String attr, String format) {
    	 DevEntityObject entObj = this;
        String dtFmt = "";
        try {
            Date date = (Date) entObj.$(attr).getVal();
            dtFmt = new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
        }
        return dtFmt;
    }    
    
    public DevEntityPropertyValue $(String attr) {
    	long timeIni = System.nanoTime();
    	DevEntityObject entObj = this;
        DevEntityPropertyValue valReturn = new DevEntityPropertyValue();
        try {
            //SETAR PROPRIEDADES DA ENTITY CLASS NO ENTITY OBJECT
            if (entObj != null && entObj.getEntityClass() != null) {
            	List<DevEntityPropertyDescriptor> entityPropertyDescriptorList = entObj.getEntityClass().getEntityPropertyDescriptorList();
                if (entObj.getEntityPropertyValueList().size() < entityPropertyDescriptorList.size()) {
                    for (DevEntityPropertyDescriptor prop : entityPropertyDescriptorList) {
                        DevEntityPropertyValue value = null;
                        for (DevEntityPropertyValue propVal : entObj.getEntityPropertyValueList()) {
                            if (propVal.getEntityPropertyDescriptor().getPropertyName().equals(prop.getPropertyName())) {
                                value = propVal;
                                break;
                            }
                        }
                        if (value == null) {
                            value = new DevEntityPropertyValue();
                            value.setEntityPropertyDescriptor(prop);
                            entObj.getEntityPropertyValueList().add(value);
                            entObj.putEntityPropertyValueMap(entObj, value);
                        }
                    }
                }
                if (!entObj.getEntityPropertyValueList().isEmpty()) {
                    String[] attrArray = attr.split(Pattern.quote("."));
                    DevEntityObject obj = entObj;
                    for (int i = 1; i < attrArray.length; i++) {
                        String string = attrArray[i];
                        valReturn = findNode(obj, string);
                        if (valReturn != null) {
                            if (valReturn.getPropertyObject() != null && !valReturn.getPropertyObject().getEntityPropertyValueList().isEmpty()) {
                                obj = valReturn.getPropertyObject();
                            } else if (valReturn.getObjectParent() != null && !valReturn.getObjectParent().getEntityPropertyValueList().isEmpty()) {
                                obj = valReturn.getObjectParent();
                            }
                        }
                    }
                }
            }
            if(valReturn==null){
            	this.getClass();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("Time getVal("+attr+"): "+BigDecimal.valueOf((System.nanoTime()-timeIni)/1000000000d).setScale(6));
        return valReturn;
    }

    private DevEntityPropertyValue findNode(DevEntityObject obj, String attr) {
        try {
        	if(obj.getEntityPropertyValueMap().isEmpty()){
        		for (DevEntityPropertyValue val : obj.getEntityPropertyValueList()) {
        			/*
        			if (val.getEntityPropertyDescriptor().getPropertyClass()!=null && val.getEntityPropertyDescriptor().getPropertyClass().getName().equals(attr)) {
        				return val;
        			}            	
        			if (val.getEntityPropertyDescriptor().getPropertyName().equals(attr)) {
        				return val;
        			}
        			*/
        			putEntityPropertyValueMap(obj, val);		
        		}        		
        	}
        	DevEntityPropertyValue val = obj.getEntityPropertyValueMap().get(attr);
        	if(val!=null){
        		return val;
        	}
            if (obj.getPropertyParent() != null
                    && obj.getPropertyParent().getEntityPropertyDescriptor().getEntityClassParent() != null && obj.getPropertyParent().getEntityPropertyDescriptor().getEntityClassParent().getName().equals(attr)) {
                if (obj.getPropertyParent().getObjectParent() != null) {
                    return obj.getPropertyParent();
                }
            }
        } catch (Exception e) {
        }
        return null;
    }
    
    
    public Long getEntityObjectId() {
        return entityObjectId;
    }

    public void setEntityObjectId(Long entityObjectId) {
        this.entityObjectId = entityObjectId;
    }
    //@OrderBy("entityPropertyDescriptor.entityPropertyDescriptorId")
    public List<DevEntityPropertyValue> getEntityPropertyValueList() {
        return entityPropertyValueList;
    }

    public void setEntityPropertyValueList(List<DevEntityPropertyValue> entityPropertyValueList) {
        this.entityPropertyValueList = entityPropertyValueList;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public DevEntityClass getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(DevEntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public DevEntityPropertyValue getPropertyParent() {
        return propertyParent;
    }

    public void setPropertyParent(DevEntityPropertyValue propertyParent) {
        this.propertyParent = propertyParent;
    }

    public SecUser getUser() {
		return user;
	}
	public void setUser(SecUser user) {
		this.user = user;
	}    
    
    public Map<String, DevEntityPropertyValue> getEntityPropertyValueMap() {
		return entityPropertyValueMap;
	}

	public void setEntityPropertyValueMap(Map<String, DevEntityPropertyValue> entityPropertyValueMap) {
		this.entityPropertyValueMap = entityPropertyValueMap;
	}
	
	public void putEntityPropertyValueMap(DevEntityObject obj, DevEntityPropertyValue val){
		if (val.getEntityPropertyDescriptor().getPropertyClass()!=null) {
			obj.entityPropertyValueMap.put(val.getEntityPropertyDescriptor().getPropertyClass().getName(), val);
		}else{
			obj.entityPropertyValueMap.put(val.getEntityPropertyDescriptor().getPropertyName(), val);
		}			
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevEntityObject other = (DevEntityObject) obj;
        if (this.entityObjectId != other.entityObjectId && (this.entityObjectId == null || !this.entityObjectId.equals(other.entityObjectId))) {
            return false;
        }
        if (this.entityObjectId == other.entityObjectId && this.entityObjectId == null && !String.valueOf(this).equals(String.valueOf(obj))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + (this.entityObjectId != null ? this.entityObjectId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        String objectStr = "";
        for (DevEntityPropertyValue devEntityPropertyValue : entityPropertyValueList) {
            if (devEntityPropertyValue.getEntityPropertyDescriptor().isPropertyIdentifier()) {
                if (!objectStr.isEmpty()) {
                    objectStr += " - ";
                }
                objectStr += devEntityPropertyValue;
            }
        }
        return objectStr;
        //return "DevEntityObject{" + "entityObjectId = " + entityObjectId + ", registrationDate=" + registrationDate + ", entityPropertyValueList=" + entityPropertyValueList + '}';               
    }

	@Override
	public void updateProperty() {
        this.setUser(getFacadeWithJNDI(GerLoginBean.class).getUser());
		super.updateProperty();
	}
    
    
}
