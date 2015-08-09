/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.dao;

import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Agnaldo
 */
//@Stateless
//@TransactionManagement(TransactionManagementType.CONTAINER)
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SeqnumDao implements Serializable {
    private static final long serialVersionUID = 1L;
    //@PersistenceContext(unitName="ERPPU")
    public EntityManager entityManager;
    public static EntityTransaction transacao;

    public Long findSeqnum(Class resultClass, Long siteId) {
        Throwable t = null;
        long seqnum = 0L;
        try {
            if (siteId != null) {
                System.out.println(resultClass.getName());
            //entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            Object ob = entityManager.createQuery("select t from PurSolicitation t").getSingleResult();
            //seqnum = (Long)entityManager.createQuery("select max(t.seqnum) from "+resultClass.getSimpleName()+" t where t.instantiatesSite.instantiatesSiteId = ?1").setParameter(1, siteId).getSingleResult();
                
            }

            //transacao.commit();
        } catch (Exception e) {
            t = e;
            //e.printStackTrace();
            seqnum = 0L;
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                //entityManager.close();
            }
            return seqnum+1;
        }

    }

}
