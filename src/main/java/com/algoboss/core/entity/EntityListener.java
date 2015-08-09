/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.erp.util.ManualCDILookup;

/**
 *
 * @author Agnaldo
 */
public class EntityListener extends ManualCDILookup {
    @PrePersist
    public void prePersist(GenericEntity e){
        updateProperty(e);
    }    
    @PreUpdate
    public void preUpdate(GenericEntity e){
        updateProperty(e);
    }        
    public void updateProperty(GenericEntity e){
        if (e.instantiatesSite == null) {
            e.setInstantiatesSite(getFacadeWithJNDI(GerLoginBean.class).getInstantiatesSiteContract());
        }
        if (e.seqnum == null) {
            e.setSeqnum(getFacadeWithJNDI(BaseDao.class).findSeqnum(e.getClass(), e.instantiatesSite.getInstantiatesSiteId()));
        }               
    }
    
}
