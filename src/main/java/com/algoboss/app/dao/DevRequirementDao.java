/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.app.dao;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.algoboss.core.entity.AdmContract;
import com.algoboss.core.entity.JpaUtil;
import com.algoboss.core.entity.SecUser;
import com.algoboss.core.face.GerLoginBean;

/**	
 *
 * @author Agnaldo
 */
public class DevRequirementDao {

    @Inject
    private GerLoginBean loginBeanx;	
    public static EntityManager entityManager;
    public static EntityTransaction transacao;

    public static List<SecUser> findAll() {
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
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return usuarioList;
        }
    }

    public static SecUser findByLoginAndPassword(String login, String password) {
        SecUser usuario = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuario = (SecUser) entityManager.createQuery("select u from SecUser u where u.login = ?1 and u.password = ?2").setParameter(1, login).setParameter(2, password).getSingleResult();

            //transacao.commit();
        } catch (NoResultException e) {
            usuario = null;
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return usuario;
        }
    }

    public static AdmContract findById(Long id) {
        AdmContract usuario = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuario = (AdmContract) entityManager.find(AdmContract.class, id);

            //transacao.commit();
        } catch (NoResultException e) {
            usuario = null;
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return usuario;
        }
    }

    public static List<AdmContract> findByRepresentativeId(Long representativeId) {
        List<AdmContract> list = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            list = entityManager.createQuery("select u from AdmContract u where u.representative.representativeId = ?1 ").setParameter(1, representativeId).getResultList();

            //transacao.commit();
        } catch (NoResultException e) {
            list = null;
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
            return list;
        }
    }

}
