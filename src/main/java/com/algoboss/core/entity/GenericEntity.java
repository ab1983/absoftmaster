/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.entity;

import com.algoboss.core.dao.BaseDao;
import com.algoboss.core.face.GerLoginBean;
import com.algoboss.erp.util.ManualCDILookup;

import javax.inject.Inject;
import javax.persistence.*;

/**
 *
 * @author Agnaldo
 */
//@EntityListeners(value=EntityListener.class)
@MappedSuperclass
public abstract class GenericEntity extends ManualCDILookup {

    @ManyToOne()
    @JoinColumn(name = "instantiates_site_id", nullable = false)
    protected AdmInstantiatesSite instantiatesSite;
    @Column(nullable = false)
    protected Long seqnum;

    public GenericEntity() {
    }
    public AdmInstantiatesSite getInstantiatesSite() {
        return instantiatesSite;
    }

    public void setInstantiatesSite(AdmInstantiatesSite instantiatesSite) {
        this.instantiatesSite = instantiatesSite;
    }


    public Long getSeqnum() {
        return seqnum;
    }

    public void setSeqnum(Long seqnum) {
        this.seqnum = seqnum;
    }        
    
	@PrePersist
    public void prePersist(){
        updateProperty();
    }    
    @PreUpdate
    public void preUpdate(){
        updateProperty();
    }        
    public void updateProperty(){
        if (this.instantiatesSite == null) {
        	this.setInstantiatesSite(getFacadeWithJNDI(GerLoginBean.class).getInstantiatesSiteContract());
            /*try {
                BaseDao dao = getFacadeWithJNDI(BaseDao.class);
				this.setInstantiatesSite((AdmInstantiatesSite)dao.findByObj(getFacadeWithJNDI(GerLoginBean.class).getInstantiatesSiteContract(), true));
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
        }
        if (this.seqnum == null) {
            this.setSeqnum(getFacadeWithJNDI(BaseDao.class).findSeqnum(this.getClass(), this.instantiatesSite.getInstantiatesSiteId()));
        }               
    }    
}
