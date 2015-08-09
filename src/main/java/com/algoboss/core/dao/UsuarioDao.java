/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.algoboss.core.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;

import com.algoboss.core.entity.JpaUtil;
import com.algoboss.core.entity.SecUser;

/**
 *
 * @author Agnaldo
 */
public class UsuarioDao implements Serializable {

    private static final long serialVersionUID = 1L;
    private BaseDao baseDao;    
    public static EntityManager entityManager;
    public static EntityTransaction transacao;

    public UsuarioDao(BaseDao baseDao) {
        this.baseDao = baseDao;
    }

    public static List<SecUser> findAll() {
        List<SecUser> usuarioList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuarioList = entityManager.createQuery("select u from SecUser u").setHint("javax.persistence.cache.storeMode", "REFRESH").getResultList();

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
            usuario = entityManager.createQuery("select u from SecUser u where u.login = ?1 and u.password = ?2", SecUser.class).setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, login).setParameter(2, password).getSingleResult();
            Object id = entityManager.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(usuario);
            entityManager.getEntityManagerFactory().getCache().evict(usuario.getClass(), id);         
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

    public SecUser findByEmailAndPassword(String email, String password) {
        SecUser usuario = null;
        EntityManager em = null;
        try {
            em = baseDao.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin(); 
            usuario = em.createQuery("select u from SecUser u where u.email = ?1 and u.password = ?2", SecUser.class).setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, email).setParameter(2, password).getSingleResult();
            Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(usuario);
            em.getEntityManagerFactory().getCache().evict(usuario.getClass(), id);
            //transacao.commit();
        } catch (NoResultException e) {
            usuario = null;
        } catch (Exception e) {
            e.printStackTrace();
            //transacao.rollback();
        } finally {
            if (em != null && em.isOpen()) {
                //em.close();
            }
            return usuario;
        }
    }

    public static SecUser findById(Long id) {
        SecUser usuario = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuario = entityManager.createQuery("select u from SecUser u where u.userId = ?1", SecUser.class).setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, id).getSingleResult();

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

    public static List<SecUser> findAdministratorByContractId(Long contractId) {
        List<SecUser> usuarioList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuarioList = entityManager.createQuery("select u from SecUser u where u.administrator = 'TRUE' and u.contract.contractId = ?1").setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, contractId).getResultList();

            //transacao.commit();
        } catch (NoResultException e) {
            usuarioList = null;
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

    public static List<SecUser> findByContractId(Long contractId) {
        List<SecUser> usuarioList = null;
        try {
            entityManager = JpaUtil.getEntityManager();
            //transacao = entityManager.getTransaction();
            //transacao.begin();
            usuarioList = entityManager.createQuery("select u from SecUser u where u.contract.contractId = ?1").setHint("javax.persistence.cache.storeMode", "REFRESH").setParameter(1, contractId).getResultList();

            //transacao.commit();
        } catch (NoResultException e) {
            usuarioList = null;
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
}
