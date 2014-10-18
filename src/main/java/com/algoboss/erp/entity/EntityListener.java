/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.entity;

import com.algoboss.erp.dao.BaseDao;
import com.algoboss.erp.face.GerLoginBean;
import com.algoboss.erp.util.ManualCDILookup;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

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
