/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.dao;

import com.utfpr.audiomanager.util.HibernateUtil;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.validation.Valid;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author josevictor
 */
public class GenericDao<T, I extends Serializable> {
    
    @Inject
    private Session session;
    
    private Class<T> persistedClass;
    
    protected GenericDao() {
    }
    
    protected GenericDao(Class<T> persistedClass) {
        this();
        this.session = HibernateUtil.getSessionFactory().openSession();
        this.persistedClass = persistedClass;
    }
    
    public T salvar(@Valid T entity) {
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.save(entity);
            session.flush();
            transaction.commit();     
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                this.session.close();
            }
        }
        return entity;
    }
    
    public T atualizar(@Valid T entity) {
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            transaction.begin();
            session.update(entity);
            session.flush();
            transaction.commit();         
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }

    public T remover(I id) {
        T entity = null;
        Transaction transaction = null;
        try {
            transaction = session.getTransaction();
            entity = encontrar(id);
            transaction.begin();
            session.delete(entity);
            session.flush();
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }
    
    public List<T> getList() {
        Criteria criteria = null;
        try {
            criteria = session.createCriteria(persistedClass);
            if (criteria != null) {
                return criteria.list();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return null;
    }
    
    public T encontrar(I id) {
        T entity = null;
        try {
            entity = (T) session.get(persistedClass, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return entity;
    }
}