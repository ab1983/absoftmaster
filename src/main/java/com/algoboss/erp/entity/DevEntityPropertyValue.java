/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import com.algoboss.erp.face.BaseBean;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
@NamedQueries({
    @NamedQuery(name = "findAllDevEntityPropertyValue",
            query = "select u from DevEntityPropertyValue u")
})
@Entity
@Table(name = "dev_entity_property_value")
public class DevEntityPropertyValue implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @SequenceGenerator(name = "DevEntityPropertyValue", sequenceName = "sequence_dev_entity_property_value", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DevEntityPropertyValue")
    @Column(name = "entity_property_value_id")
    private Long entityPropertyValueId;
    @Column(name = "property_value")
    private String propertyValue;
    @Column(name = "property_file")
    private byte[] propertyFile;
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "entity_object_id")
    private DevEntityObject objectParent;    
    @ManyToOne(fetch = FetchType.EAGER,optional = false)
    @JoinColumn(name = "user_id")
    private SecUser user;    
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "parent_id")
    private List<DevEntityObject> propertyChildrenList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "object_id")
    private DevEntityObject propertyObject;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "entity_property_descriptor_id")
    @OrderBy
    private DevEntityPropertyDescriptor entityPropertyDescriptor;
    @Transient
    private DevEntityObject entityObjectMapped; 

    public DevEntityPropertyValue() {
    }

    public DevEntityPropertyValue(DevEntityPropertyValue val) {
        //this.entityPropertyValueId = val.entityPropertyValueId;
        if (val != null) {
            this.propertyValue = val.propertyValue;
            this.propertyFile = val.propertyFile;
            this.propertyObject = val.propertyObject;
            this.user = val.user;
            if (val.propertyChildrenList != null) {
                for (DevEntityObject devEntityObject : val.propertyChildrenList) {
                    this.propertyChildrenList.add(new DevEntityObject(devEntityObject));
                }
            }
            this.entityPropertyDescriptor = val.entityPropertyDescriptor;
        }
    }

    @PrePersist
    public void prePersist() throws Exception {
        propertyValueValidate();
    }

    @PreUpdate
    public void preUpdate() throws Exception {
        propertyValueValidate();
    }

    private void propertyValueValidate() {
        if (entityPropertyDescriptor != null
                && entityPropertyDescriptor.isPropertyRequired()
                && entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")
                && (propertyChildrenList == null || propertyChildrenList.isEmpty())) {
        	FacesMessage	 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, entityPropertyDescriptor.getPropertyLabel() + " : " + BaseBean.getBundle("required", "msg"), "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
                    	
            throw new IllegalArgumentException(entityPropertyDescriptor.getPropertyLabel() + " : " + BaseBean.getBundle("required", "msg"));
            //throw new Error(BaseBean.getBundle("required", "msg") + " : " + entityPropertyDescriptor.getPropertyLabel());
        }
    }

    public Long getEntityPropertyValueId() {
        return entityPropertyValueId;
    }

    public void setEntityPropertyValueId(Long entityPropertyValueId) {
        this.entityPropertyValueId = entityPropertyValueId;
    }

    public List<DevEntityObject> getPropertyChildrenList() {
        List<DevEntityObject> obj = null;
        if (propertyChildrenList != null) {
            if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
                obj = propertyChildrenList;
            }
        }
        return obj;
    }

    public void setPropertyChildrenList(List<DevEntityObject> propertyChildrenList) {
        this.propertyChildrenList = propertyChildrenList;
    }

    public DevEntityObject getPropertyObject() {
        DevEntityObject obj = null;
        if (propertyObject != null) {
            if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS")) {
                obj = propertyObject;
            }
        }
        return obj;
    }

    public void setPropertyObject(DevEntityObject propertyObject) {
        if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS")) {
            this.propertyObject = propertyObject;
        }
    }

    public Object getPropertyValue() {
        Object obj = null;
        try {
            if (propertyValue != null && !String.valueOf("null").equals(propertyValue) && entityPropertyDescriptor != null) {
            	if(entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING")){
            		obj = propertyValue;   
            	}else if(!propertyValue.isEmpty()){
            		if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
            			obj = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").parse(propertyValue);
            		} else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
            			obj = Boolean.valueOf(propertyValue).booleanValue();
            		} else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")){
            			obj = Double.valueOf(propertyValue).doubleValue();
            			//obj = propertyValue;     
            		}else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")){
            			obj = Integer.valueOf(propertyValue).intValue();
            			//obj = propertyValue;     
            		}else{
            			obj = propertyValue;                	
            		}
            		
            	}
            }
        } catch(java.text.ParseException ex){
            Logger.getLogger(DevEntityPropertyValue.class.getName()).log(Level.SEVERE, "Parse Exception ->"+ex.getMessage(), obj);
        }catch (Exception ex) {
            Logger.getLogger(DevEntityPropertyValue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            return obj;
        }
    }

    public void setPropertyValue(Object propertyValue) {
        Object obj = null;
        try {
            if (propertyValue != null && !String.valueOf("null").equals(propertyValue) && entityPropertyDescriptor != null) {
            	if(entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("STRING")){
            		obj = propertyValue;   
            	}else if(!String.valueOf(propertyValue).isEmpty()){
            		if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("DATE")) {
            			obj = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(propertyValue);
            		} else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("BOOLEAN")) {
            			obj = String.valueOf(propertyValue);
            		} else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("FLOAT")){
            			DecimalFormat df = new DecimalFormat();
            			DecimalFormatSymbols dfs = new DecimalFormatSymbols();
            			dfs.setDecimalSeparator(BaseBean.getBundle("decimalSeparator", "msg").charAt(0));
            			dfs.setGroupingSeparator(BaseBean.getBundle("thousandsSeparator", "msg").charAt(0));
            			df.setDecimalFormatSymbols(dfs);
            			obj = df.parse(String.valueOf(propertyValue));
            			//obj = propertyValue;     
            		}else if (entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("INTEGER")){
            			obj = String.valueOf(propertyValue);
            			//obj = propertyValue;     
            		}else {
            			obj = propertyValue;
            		}            		
            	}
            }
        } catch (Exception ex) {
            Logger.getLogger(DevEntityPropertyValue.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (obj != null) {
                this.propertyValue = String.valueOf(obj);
            }
        }
    }
    
    public Object getVal() {
        Object obj = getPropertyValue();
        if (obj == null && user != null) {
            obj = userToEntObj(user);
        }
        if (obj == null && entityPropertyDescriptor != null && entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCLASS")) {
            obj = getPropertyObject();
        }
        if (obj == null && entityPropertyDescriptor != null && entityPropertyDescriptor.getPropertyType().equalsIgnoreCase("ENTITYCHILDREN")) {
            obj = getPropertyChildrenList();
        }
        return obj;
    }
    
    public String str(){
    	return propertyValue;
    }
    
    public void clear() {
        this.propertyObject = null;
        this.propertyFile = null;
        this.propertyValue = null;
        this.propertyChildrenList = null;
    }

    public void setVal(Object propertyValue) {
        try {

            clear();
            if (propertyValue != null && propertyValue instanceof DevEntityObject && ((DevEntityObject) propertyValue).getEntityClass().getEntityClassId() == 1L) {
                setUser(entObjToUser((DevEntityObject) propertyValue));
            } else if (propertyValue != null && propertyValue instanceof DevEntityObject) {
                setPropertyObject((DevEntityObject) propertyValue);
            } else if (propertyValue != null && propertyValue instanceof List) {
                setPropertyChildrenList((List) propertyValue);
            } else {
                if (entityPropertyDescriptor.isPropertyRequired() && propertyValue == null) {
                	FacesMessage	 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, entityPropertyDescriptor.getPropertyLabel() + " : " + BaseBean.getBundle("required", "msg"), "");
                    //FacesContext.getCurrentInstance().addMessage(null, msg);
                            	
                    throw new ValidatorException(msg);
                } else {
                    setPropertyValue(propertyValue);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(byte[] propertyFile) {
        this.propertyFile = propertyFile;
    }

    public DevEntityPropertyDescriptor getEntityPropertyDescriptor() {
        return entityPropertyDescriptor;
    }

    public void setEntityPropertyDescriptor(DevEntityPropertyDescriptor entityPropertyDescriptor) {
        this.entityPropertyDescriptor = entityPropertyDescriptor;
    }

    public DevEntityObject getObjectParent() {
        return objectParent;
    }

    public void setObjectParent(DevEntityObject objectParent) {
        this.objectParent = objectParent;
    }

    public DevEntityObject userToEntObj(SecUser user) {
        try {
            DevEntityClass entClass = new DevEntityClass();
            entClass.setEntityClassId(1L);
            DevEntityObject entObj = new DevEntityObject();
            entObj.setEntityClass(entClass);
            entObj.setEntityObjectId(Double.valueOf(Math.pow(user.getUserId(), 5)).longValue());
            DevEntityPropertyValue propVal = new DevEntityPropertyValue();
            //propVal.setUser(user);
            DevEntityPropertyDescriptor propDesc = new DevEntityPropertyDescriptor();
            propDesc.setPropertyIdentifier(true);
            propDesc.setPropertyType("STRING");
            propVal.setEntityPropertyDescriptor(propDesc);
            propVal.setPropertyValue(user.toString());

            entObj.getEntityPropertyValueList().add(propVal);

            return entObj;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public SecUser entObjToUser(DevEntityObject user) {
        try {
            return user.getEntityPropertyValueList().get(0).getUser();
        } catch (Exception e) {
            e.printStackTrace();
            return null;

        }
    }

    public SecUser getUser() {
        return user;
    }

    public void setUser(SecUser user) {
        this.user = user;
    }

    public DevEntityObject getEntityObjectMapped() {
		return entityObjectMapped;
	}

	public void setEntityObjectMapped(DevEntityObject entityObjectMapped) {
		this.entityObjectMapped = entityObjectMapped;
	}

	@Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DevEntityPropertyValue other = (DevEntityPropertyValue) obj;
        if (this.entityPropertyValueId != other.entityPropertyValueId && (this.entityPropertyValueId == null || !this.entityPropertyValueId.equals(other.entityPropertyValueId))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + (this.entityPropertyValueId != null ? this.entityPropertyValueId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        return Objects.toString(getVal(),"");
        //return "DevEntityPropertyValue{" + "entityPropertyValueId=" + entityPropertyValueId + ", propertyValue=" + propertyValue + ", propertyChildrenList=" + propertyChildrenList + ", propertyObject=" + propertyObject + '}';
    }
}
