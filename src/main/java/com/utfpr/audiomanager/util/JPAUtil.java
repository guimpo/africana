/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.audiomanager.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author josevictor
 */
public class JPAUtil {

    private static EntityManager em;  
    
    public static EntityManager getEntityManager() {
        try {
            EntityManagerFactory factory = Persistence.createEntityManagerFactory("Cidade");
            em = factory.createEntityManager();
        }
        catch (Exception e) {
                        
        }      
        return em;
    }
}
