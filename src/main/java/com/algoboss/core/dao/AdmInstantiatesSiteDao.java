/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.algoboss.core.entity.AdmInstantiatesSite;
import com.algoboss.core.entity.JpaUtil;
import com.algoboss.core.entity.SecUser;

/**
 *
 * @author Agnaldo
 */
public class AdmInstantiatesSiteDao {
 public static EntityManager entityManager;
    public static EntityTransaction transacao;
    public static List<SecUser> findAll(){
        List<SecUser> usuarioList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuarioList = entityManager.createQuery("select u from Usuario u").getResultList();            
            
            //transacao.commit();
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return usuarioList;
        }        
    }        
    public static SecUser findByLoginAndPassword(String login,String password){
        SecUser usuario = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuario = (SecUser)entityManager.createQuery("select u from SecUser u where u.login = ?1 and u.password = ?2").setParameter(1, login).setParameter(2, password).getSingleResult();            
            
            //transacao.commit();
        }catch(NoResultException e){
            usuario = null;
        }catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return usuario;
        }        
    }
    public static SecUser findById(Long id){
        SecUser usuario = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuario = (SecUser)entityManager.createQuery("select u from SecUser u where u.userId = ?1").setParameter(1, id).getSingleResult();            
            
            //transacao.commit();
        }catch(NoResultException e){
            usuario = null;
        }catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return usuario;
        }        
    }    
    public static List<AdmInstantiatesSite> findByContractId(Long contractId){
        List<AdmInstantiatesSite> siteList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            siteList = entityManager.createQuery("select u from AdmInstantiatesSite u where u.contract.contractId = ?1").setParameter(1, contractId).getResultList();            
            
            //transacao.commit();
        }catch(NoResultException e){
            siteList = null;
        }catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager!= null && entityManager.isOpen())
                entityManager.close();
                return siteList;
        }        
    }    
    
}
