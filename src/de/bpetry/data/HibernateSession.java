/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bpetry.data;

import de.bpetry.util.Log;
import de.bpetry.util.Util;
import java.util.Arrays;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Benjamin Petry
 */
public class HibernateSession
{
    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------

    private static SessionFactory sessionFactory = null;
    private static Session openedSession = null;

    //-------------------------------------------------------------------------
    ////////////////////////////  Init and Dispose ////////////////////////////
    //-------------------------------------------------------------------------
    public static boolean init(HibernateConfig hbConfig)
    {
        if (sessionFactory != null)
        {
            dispose();
        }
        try
        {
            Configuration config = createConfiguration(hbConfig);
            sessionFactory = config.buildSessionFactory();
            openedSession = sessionFactory.openSession();
            Log.info("Hibernate session initialized");
        }
        catch (Exception ex)
        {
            Log.error("Could not initiate hibernate session", ex);
            return false;
        }
        return true;
    }

    public static boolean dispose()
    {
        try
        {
            if (openedSession != null)
            {
                openedSession.close();
                openedSession = null;
            }
            if (sessionFactory != null)
            {
                sessionFactory.close();
                sessionFactory = null;
            }
            Log.info("Hibernate session disposed");
        }
        catch (Exception ex)
        {
            Log.warning("Could not dispose existing hibernate session", ex);
            return false;
        }
        return true;
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    public static <T extends Object> void delete(Iterable<T> iter)
    {
        executeTransaction((session) ->
        {
            for (T obj : iter)
            {
                session.delete(obj);
            }
            return null;
        });
    }

    public static void delete(Object... arrayOfObjects)
    {
        delete(Arrays.asList(arrayOfObjects));
    }

    public static <T extends Object> void save(Iterable<T> iter)
    {
        executeTransaction((session) ->
        {
            for (T obj : iter)
            {
                session.saveOrUpdate(obj);
            }
            return null;
        });
    }

    public static void save(Object... arrayOfObjects)
    {
        save(Arrays.asList(arrayOfObjects));
    }

    public static long countByField(String query, String field,
            Object... parameters)
    {
        List result = get("SELECT COUNT(" + field + ") " + query, parameters);
        if (result.isEmpty())
        {
            return -1;
        }
        return (long) result.get(0);
    }

    public static long count(String query, Object... parameters)
    {
        List result = get("SELECT COUNT(*) " + query, parameters);
        if (result.isEmpty())
        {
            return -1;
        }
        return (long) result.get(0);
    }

    public static List get(String query, Object... parameters)
    {
        return getWithLimit(query, -1, parameters);
    }

    public static List getWithLimit(String query, int maxResults,
            Object... parameters)
    {
        return executeTransaction((session) ->
        {
            Query q = session.createQuery(query);
            if (maxResults >= 0)
            {
                q.setMaxResults(maxResults);
            }
            for (int n = 0; n < parameters.length; n++)
            {
                q.setParameter(n, parameters[n]);
            }
            return q.list();
        });
    }

    public static List executeTransaction(TransactionAction action)
    {
        while (openedSession.getTransaction().isActive())
        {
            Util.sleep(10);
        }
        openedSession.beginTransaction();
        List result = action.perform(openedSession);
        openedSession.getTransaction().commit();

        return result;
    }

    public static interface TransactionAction
    {

        public List perform(Session session);
    }

    //-------------------------------------------------------------------------
    //////////////////////////  Private Static Methods ////////////////////////
    //-------------------------------------------------------------------------
    private static Configuration createConfiguration(HibernateConfig config)
    {
        Configuration c = new Configuration();
        c.setProperty("hibernate.connection.driver_class", getDriverClass(
                config.getType()));
        c.setProperty("hibernate.connection.url", getURL(config.getType(),
                config.getHost(), config.getDbname()));
        c.setProperty("hibernate.connection.username", config.getUser());
        c.setProperty("hibernate.connection.password", config.getPassword());
        c.setProperty("hibernate.connection.pool_size", "10");
        c.setProperty("hibernate.connection.autocommit", "false");
        c.setProperty("hibernate.dialect", getDialect(config.getType()));
        c.setProperty("hibernate.cache.provider_class",
                "org.hibernate.cache.internal.NoCacheProvider");
        c.setProperty("hibernate.show_sql", "true");
        c.setProperty("hibernate.hbm2ddl.auto", "update");
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
                return "jdbc:" + type + "://" + host + "/" + database;
        }
        return "";
    }

}
