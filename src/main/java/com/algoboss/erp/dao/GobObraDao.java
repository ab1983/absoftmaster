/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.algoboss.core.entity.JpaUtil;
import com.algoboss.erp.entity.GobObra;

/**
 *
 * @author Agnaldo
 */
public class GobObraDao {
 public static EntityManager entityManager;
    public static EntityTransaction transacao;
    public static List<GobObra> findAll(){
        List<GobObra> objList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            objList = entityManager.createQuery("select t from Obra t").getResultList();            
            
            //transacao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return objList;
        }        
    }        
}
