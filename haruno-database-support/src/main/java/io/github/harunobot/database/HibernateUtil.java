/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.database;


import java.util.Map;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.LoggerFactory;

/**
 * @author iTeam_VEP
 */
public class HibernateUtil {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(HibernateUtil.class);

    public static SessionFactory generateSessionFactory(Map<String, Object> settings, Class<?>[] annotatedClasses) {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder()
                    .applySettings(settings)
                    .build();
            MetadataSources metadataSources = new MetadataSources(registry);
            for(Class annotatedClass:annotatedClasses){
                metadataSources.addAnnotatedClass(annotatedClass);
            }
            return metadataSources
                    .getMetadataBuilder()
                    .build()
                    .getSessionFactoryBuilder()
                    .build();
        } catch (Exception ex) {
            if (registry != null) {
               StandardServiceRegistryBuilder.destroy(registry);
            }
            LOG.error("generate session factory failed", ex);
            throw ex;
        }
    }

}
