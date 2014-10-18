/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.erp.dao;

import com.algoboss.erp.entity.GobComplementacaoAquisicaoClientePf;
import com.algoboss.erp.entity.JpaUtil;
import com.algoboss.erp.entity.GobObra;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

/**
 *
 * @author Agnaldo
 */
public class GobComplementacaoAquisicaoClientePfDao {
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
    public static GobComplementacaoAquisicaoClientePf findByClienteId(Long clienteId){
        GobComplementacaoAquisicaoClientePf obj = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            obj = (GobComplementacaoAquisicaoClientePf)entityManager.createQuery("select t from GobComplementacaoAquisicaoClientePf t where t.cliente.clienteId = ?1").setParameter(1, clienteId).getSingleResult();                        
            //transacao.commit();
        }catch(NoResultException e){
            obj = null;
        }catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return obj;
        }        
    }        
    
}
