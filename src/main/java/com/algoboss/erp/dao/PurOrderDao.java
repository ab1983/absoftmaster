/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.dao;

import com.algoboss.erp.entity.GrtProjeto;
import com.algoboss.erp.entity.JpaUtil;
import com.algoboss.erp.entity.PurOrder;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 *
 * @author Agnaldo
 */
public class PurOrderDao {
 public static EntityManager entityManager;
    public static EntityTransaction transacao;
    public static List<PurOrder> findApprovedByFornecedorId(Long fornecedorId){
        List<PurOrder> obj = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            obj = entityManager.createQuery("select t from PurOrder t where t.fornecedor.fornecedorId = ?1 and t.status = 'approved'",PurOrder.class).setParameter(1, fornecedorId).getResultList();            
            
            //transacao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return obj;
        }        
    }        
    
}
