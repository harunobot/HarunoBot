/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.harunobot.database;

import io.github.harunobot.database.configuration.DatabaseConfiguration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Table;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Environment;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.slf4j.LoggerFactory;

/**
 *
 * @author iTeam_VEP
 * https://stackoverflow.com/questions/17335530/can-we-configure-hibernate-without-hibernate-cfg-xml
 * https://www.tutorialspoint.com/hibernate/hibernate_annotations.htm
 * https://www.boraji.com/hibernate-5-hikaricp-configuration-example
 * https://stackoverflow.com/questions/16572039/connecting-postgresql-9-2-1-with-hibernate
 * https://developer.okta.com/blog/2019/02/20/spring-boot-with-postgresql-flyway-jsonb
 * https://www.journaldev.com/3481/hibernate-session-merge-vs-update-save-saveorupdate-persist-example
 */
public class SessionManager<T> implements AutoCloseable {
    private static final org.slf4j.Logger LOG = LoggerFactory.getLogger(SessionManager.class);
    
    private final DatabaseConfiguration configuration;
    
    private SessionFactory sessionFactory;
    
    public SessionManager(DatabaseConfiguration configuration){
        this.configuration = configuration;
    }
    
    public void init(Map<String, Object> settings, Class<?>[] annotatedClasses){
        if(settings == null){
            settings = new HashMap<>();
        }
        if(configuration.getUsername()!=null){
            settings.put(Environment.USER, configuration.getUsername());
        }
        if(configuration.getPassword()!=null){
            settings.put(Environment.PASS, configuration.getPassword());
        }
        settings.put(Environment.DRIVER, configuration.getDriver());
        settings.put(Environment.URL, configuration.getUrl());
        settings.put(Environment.DIALECT, configuration.getDialect());
        settings.put(Environment.HBM2DDL_AUTO, configuration.getHbm2ddlAuto().value());

        sessionFactory = HibernateUtil.generateSessionFactory(settings, annotatedClasses);
        LOG.info("session factory inited");
    }
    
    public void persist(T object){
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            session.persist(object);
            tx.commit();
        }
    }
    
    public List<T> selectRandom(int count, Class<T> entity){
        try (Session session = sessionFactory.openSession()) {
            
            String tableName = getTableName(entity);
            if(tableName == null){
                throw new NullPointerException();
            }
            String sql = new StringBuilder()
                    .append("SELECT * FROM ")
                    .append(tableName)
                    .append(" ORDER BY random() limit ")
                    .append(count)
                    .toString();
            return session.createQuery(sql, entity).getResultList();
        }
    }
    
    public String getTableName(Class<T> entity){
        return entity.getAnnotation(Table.class).name();
    }
    
    public Session openSession(){
        return sessionFactory.openSession();
    }
    
    @Override
    public void close() throws Exception {
        sessionFactory.close();
    }
    
}
