/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.dao;

import com.utfpr.audiomanager.util.JPAUtil;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.criteria.CriteriaQuery;
import javax.validation.Valid;

/**
 *
 * @author josevictor
 * @param <T>
 * @param <I>
 */
public class GenericDao<T, I extends Serializable> {
    
    private EntityManager em;
    private EntityTransaction transaction;
    
    private Class<T> persistedClass;
    
    protected GenericDao() {
    }
    
    protected GenericDao(Class<T> persistedClass) {
        this();
        this.em = JPAUtil.getEntityManager();
        this.transaction = em.getTransaction();
        this.persistedClass = persistedClass;
    }
    
    public T salvar(@Valid T entity) {
        try {
            transaction.begin();
            em.persist(entity);
            em.flush();
            transaction.commit();     
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                this.em.close();
            }
        }
        return entity;
    }
    
    public T atualizar(@Valid T entity) {
        try {
            transaction = em.getTransaction();
            transaction.begin();
            em.merge(entity);
            em.flush();
            transaction.commit();         
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entity;
    }

    public T remover(I id) {
        T entity = null;
        try {
            transaction = em.getTransaction();
            entity = encontrar(id);
            transaction.begin();
            em.remove(entity);
            em.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entity;
    }
    
    public List<T> getList() {
        List<T> lista = null;
        
        try {
            CriteriaQuery<T> query = em.getCriteriaBuilder().createQuery(persistedClass);
            query.select(query.from(persistedClass));
            lista = em.createQuery(query).getResultList(); 
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return lista;
    }
    
    public T encontrar(I id) {
        T entity = null;
        try {
            entity = (T) em.find(persistedClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null) {
                em.close();
            }
        }
        return entity;
    }
}