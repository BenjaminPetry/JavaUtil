/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.config.Config;
import java.util.HashSet;
import java.util.Set;

/**
 * Configuration file for a hibernate session.
 * To use this class, inherit from it, override the constructor and add the annotated hibernate classes with addClass();
 * @author Benjamin Petry
 */
public class HibernateConfig
{
    
    
    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------

    final public static String TYPE_MYSQL = "mysql";
    
    // Keys for the config file
    final public static String CONFIG_TYPE = "db.hb.type";
    final public static String CONFIG_HOST = "db.hb.host";
    final public static String CONFIG_USER = "db.hb.user";
    final public static String CONFIG_PASSWORD = "db.hb.password";
    final public static String CONFIG_DBNAME = "db.hb.dbname";
    
    
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private String type = "";
    private String host = "";
    private String user = "";
    private String password = "";
    private String dbname = "";
    private Set<Class> setOfClasses = new HashSet<>();
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    /**
     * Creates the login information for a database
     * @param type the type of the database
     * @param host the IP of the host
     * @param user the username to login
     * @param password the password according to the username
     * @param dbname the database name to connect to
     */
    public HibernateConfig(String type, String host, String user, String password, String dbname)
    {
        this.type = type;
        this.host = host;
        this.user = user;
        this.password = password;
        this.dbname = dbname;
    }
    
    //-------------------------------------------------------------------------
    ///////////////////////  Getter and Setter Methods ////////////////////////
    //-------------------------------------------------------------------------

    
    public void addClass(Class c)
    {
        setOfClasses.add(c);
    }
    
    public Set<Class> getClasses()
    {
        return setOfClasses;
    }
    
    public void removeClass(Class c)
    {
        setOfClasses.remove(c);
    }
    
    public String getType()
    {
        return type;
    }

    public String getHost()
    {
        return host;
    }

    public String getUser()
    {
        return user;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDbname()
    {
        return dbname;
    }

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    
    public static HibernateConfig fromConfig(Config c, String initFilename)
    {
        String type = c.get(CONFIG_TYPE);
        String host = c.get(CONFIG_HOST);
        String user = c.get(CONFIG_USER);
        String password = c.get(CONFIG_PASSWORD);
        String dbname = c.get(CONFIG_DBNAME);
        HibernateConfig config = new HibernateConfig(type, host, user, password, dbname);
        return config;
    }
    
    public void save(Config c)
    {
        c.set(CONFIG_TYPE, this.getType());
        c.set(CONFIG_HOST, this.getHost());
        c.set(CONFIG_USER, this.getUser());
        c.set(CONFIG_PASSWORD, this.getPassword());
        c.set(CONFIG_DBNAME, this.getDbname());
    }
}
