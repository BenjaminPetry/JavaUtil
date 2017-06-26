/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.config.Config;
import de.bpetry.util.ResourceManager;
import java.io.Reader;

/**
 * Contains Values for a database configuration
 * @author Benjamin Petry
 */
public class DBConfig
{
    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------

    // Keys for the config file
    final public static String CONFIG_TYPE = "db.type";
    final public static String CONFIG_HOST = "db.host";
    final public static String CONFIG_USER = "db.user";
    final public static String CONFIG_PASSWORD = "db.password";
    final public static String CONFIG_DBNAME = "db.dbname";
    final public static String CONFIG_PREFIX = "db.prefix";
    final public static String CONFIG_LOG_PATH = "db.logpath";
    final public static String CONFIG_SETUP = "db.setup";
    
    
    //-------------------------------------------------------------------------
    ////////////////////////////  Private Variables ///////////////////////////
    //-------------------------------------------------------------------------
    
    private String type = "";
    private String host = "";
    private String user = "";
    private String password = "";
    private String dbname = "";
    private String prefix = "";
    private String logPath = null;
    private Reader initReader = null;
    
    //-------------------------------------------------------------------------
    //////////////////////////////  Constructor ///////////////////////////////
    //-------------------------------------------------------------------------

    /**
     * Creates the login information for a database (assumes a LOCAL MySQL database, no table prefix and the database name to be equal to the username)
     * @param user the username to login
     * @param password the password according to the username
     */
    public DBConfig(String user, String password)
    {
        this(DataSinkFactory.MYSQL, "127.0.0.1", user, password, user, "");
    }
    
    /**
     * Creates the login information for a database (assumes a MySQL database, no table prefix and the database name to be equal to the username)
     * @param host the IP of the host
     * @param user the username to login
     * @param password the password according to the username
     */
    public DBConfig(String host, String user, String password)
    {
        this(DataSinkFactory.MYSQL, host, user, password, user, "");
    }
    
    /**
     * Creates the login information for a database (assumes a MySQL database and no table prefix)
     * @param host the IP of the host
     * @param user the username to login
     * @param password the password according to the username
     * @param dbname the database name to connect to
     */
    public DBConfig(String host, String user, String password, String dbname)
    {
        this(DataSinkFactory.MYSQL, host, user, password, dbname, "");
    }
    
    /**
     * Creates the login information for a database (assumes a MySQL database)
     * @param host the IP of the host
     * @param user the username to login
     * @param password the password according to the username
     * @param dbname the database name to connect to
     * @param prefix the prefix of all tables
     */
    public DBConfig(String host, String user, String password, String dbname, String prefix)
    {
        this(DataSinkFactory.MYSQL, host, user, password, dbname, prefix);
    }
    
    /**
     * Creates the login information for a database
     * @param type the type of the database (see DataSinkFactor for more details)
     * @param host the IP of the host
     * @param user the username to login
     * @param password the password according to the username
     * @param dbname the database name to connect to
     * @param prefix the prefix of all tables
     */
    public DBConfig(String type, String host, String user, String password, String dbname, String prefix)
    {
        this.type = type;
        this.host = host;
        this.user = user;
        this.password = password;
        this.dbname = dbname;
        this.prefix = prefix;
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

    public String getPrefix()
    {
        return prefix;
    }

    public String getLogPath()
    {
        return logPath;
    }

    public void setLogPath(String logPath)
    {
        this.logPath = logPath;
    }
    
    public Reader getInitResource()
    {
        return this.initReader;
    }
    
    public void setInitResource(Reader reader)
    {
        this.initReader = reader;
    }
    
    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------

    
    public static DBConfig fromConfig(Config c, String initFilename)
    {
        String type = c.get(CONFIG_TYPE);
        String host = c.get(CONFIG_HOST);
        String user = c.get(CONFIG_USER);
        String password = c.get(CONFIG_PASSWORD);
        String dbname = c.get(CONFIG_DBNAME);
        String prefix = c.get(CONFIG_PREFIX);
        DBConfig config = new DBConfig(type, host, user, password, dbname, prefix);
        
        if (!c.isNull(CONFIG_LOG_PATH))
        {
            config.setLogPath(c.get(CONFIG_LOG_PATH));
        }
        
        if (c.getBool(CONFIG_SETUP))
        {
            config.setInitResource(ResourceManager.read(initFilename));
        }
        
        return config;
    }
    
    public void save(Config c)
    {
        c.set(CONFIG_TYPE, this.getType());
        c.set(CONFIG_HOST, this.getHost());
        c.set(CONFIG_USER, this.getUser());
        c.set(CONFIG_PASSWORD, this.getPassword());
        c.set(CONFIG_DBNAME, this.getDbname());
        c.set(CONFIG_PREFIX, this.getPrefix());
        if (this.getLogPath() == null)
        {
            c.setNull(CONFIG_LOG_PATH);
        }
        else
        {
            c.set(CONFIG_LOG_PATH, this.getLogPath());
        }
    }
}
