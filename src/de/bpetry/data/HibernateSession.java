/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bpetry.data;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Benjamin Petry
 */
public class HibernateSession
{
    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------
    
    private static SessionFactory sessionFactory;
    
    //-------------------------------------------------------------------------
    ////////////////////////////  Init and Dispose ////////////////////////////
    //-------------------------------------------------------------------------
    
    public static void init(HibernateConfig hbConfig)
    {
        if (sessionFactory != null)
        {
            dispose();
        }
        Configuration config = createConfiguration(hbConfig);
        sessionFactory = config.buildSessionFactory();
    }
    
    
    public static void dispose()
    {
        if ( sessionFactory != null ) {
            sessionFactory.close();
            sessionFactory = null;
        }
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------

    public static <T extends Object> void saveAll(Iterable<T> iter)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (T obj : iter)
        {
            session.save( obj );
        }
        session.getTransaction().commit();
        session.close();
    }
    
    public static void save(Object... arrayOfObjects)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        for (Object obj : arrayOfObjects)
        {
            session.save( obj );
        }
        session.getTransaction().commit();
        session.close();
    }
    
    public static List get(String query)
    {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List result = session.createQuery(query).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
    
    //-------------------------------------------------------------------------
    //////////////////////////  Private Static Methods ////////////////////////
    //-------------------------------------------------------------------------
    
    private static Configuration createConfiguration(HibernateConfig config)
    {
        Configuration c = new Configuration();
        c.setProperty("hibernate.connection.driver_class",  getDriverClass(config.getType()));
        c.setProperty("hibernate.connection.url", getURL(config.getType(), config.getHost(), config.getDbname()));
        c.setProperty("hibernate.connection.username", config.getUser() );
        c.setProperty("hibernate.connection.password", config.getPassword() );
        c.setProperty("hibernate.connection.pool_size", "1" );
        c.setProperty("hibernate.dialect",  getDialect(config.getType()));
        c.setProperty("hibernate.cache.provider_class", "org.hibernate.cache.internal.NoCacheProvider" );
        c.setProperty("hibernate.show_sql", "true" );
        c.setProperty("hibernate.hbm2ddl.auto", "update" );
        for (Class cl : config.getClasses())
        {
            c.addAnnotatedClass(cl);
        }
        
        return c;
    }
    
    private static String getDialect(String type)
    {
        switch (type)
        {
            case HibernateConfig.TYPE_MYSQL:
                return "org.hibernate.dialect.MySQL5InnoDBDialect";
        }
        return "";
    }
    
    private static String getDriverClass(String type)
    {
        switch (type)
        {
            case HibernateConfig.TYPE_MYSQL:
                return "com.mysql.jdbc.Driver";
        }
        return "";
    }
    
    private static String getURL(String type, String host, String database)
    {
        switch (type)
        {
            case HibernateConfig.TYPE_MYSQL:
                return "jdbc:"+type+"://" + host + "/" + database;
        }
        return "";
    }
    
    
}
