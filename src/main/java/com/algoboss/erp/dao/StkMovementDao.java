/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.algoboss.core.entity.JpaUtil;
import com.algoboss.erp.entity.StkMovement;
import com.algoboss.erp.entity.StkMovementItem;
import com.algoboss.erp.entity.StkSupplyItem;

/**
 *
 * @author Agnaldo
 */
public class StkMovementDao {

    public static EntityManager entityManager;
    public static EntityTransaction transacao;

    public static StkMovement findBySupplyItemId(Long supplyItemId) {
        StkMovement obj = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            obj = entityManager.createQuery("select t from StkMovement t where t.supplyItem.supplyItemId = ?1", StkMovement.class).setParameter(1, supplyItemId).getSingleResult();

            //transacao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return obj;
        }
    }

    public static void updateCurrentBalance(StkSupplyItem item) {
        List<StkMovementItem> movItemList = item.getMovement().getMovementItemList();
        BigDecimal currentBalance = new BigDecimal(0);
        Date lastMovement = new Date(0);
        for (int i = 0; i < movItemList.size(); i++) {
            StkMovementItem stkMovementItem = movItemList.get(i);
            if (stkMovementItem.getMovementHistory() != null) {
                if(stkMovementItem.getDateOfMovement().after(lastMovement) ){
                    lastMovement = stkMovementItem.getDateOfMovement();
                }
                if (stkMovementItem.getMovementHistory().getMovementType().equals("input")) {
                    currentBalance = currentBalance.add(stkMovementItem.getAmount());
                } else if (stkMovementItem.getMovementHistory().getMovementType().equals("output")) {
                    currentBalance = currentBalance.subtract(stkMovementItem.getAmount());
                }
            }
        }
        if(lastMovement.after(new Date(0))){
            item.getMovement().setDateLastMovement(lastMovement);            
        }
        if(currentBalance.floatValue()>new BigDecimal(0).floatValue()){
            item.getMovement().setCurrentBalance(currentBalance);            
        }
    }
}
