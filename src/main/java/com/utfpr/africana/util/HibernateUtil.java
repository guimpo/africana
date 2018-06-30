/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.utfpr.africana.util;

import java.util.HashMap;
import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Hibernate Utility class with a convenient method to get Session Factory
 * object.
 *
 * @author josevictor
 */
public class HibernateUtil {

    private static SessionFactory sessionFactory = null;
    private HibernateUtil() {}
    
    public static SessionFactory getSessionFactory() {
        try {
            if (sessionFactory == null) {
                Map<String,String> jdbcUrlSettings = new HashMap<>();
                String jdbcDbUrl = System.getenv("JDBC_DATABASE_URL");
                if (null != jdbcDbUrl) {
                    jdbcUrlSettings.put("hibernate.connection.url", System.getenv("JDBC_DATABASE_URL"));
                }

                ServiceRegistry registry = new StandardServiceRegistryBuilder()
                        .configure("hibernate.cfg.xml")
                        .applySettings(jdbcUrlSettings)
                        .build();

                sessionFactory = new Configuration()
                        .configure()
                        .buildSessionFactory(registry);
            }
        } catch (Throwable ex) {
            System.err.println("Erro ao criar sessionFactory" + ex);
            throw new ExceptionInInitializerError(ex);
        }
        return sessionFactory;
    }
}
