/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.util.Log;
import java.io.IOException;
import java.io.Reader;

/**
 * Is responsible to initiate the data sink defined in the project file
 *
 * @author Benjamin Petry
 */
public class DataSinkFactory
{

    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------
    // the database types
    public final static String MYSQL = "mysql";

    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------
    private static IDataSink instance = null;

    //-------------------------------------------------------------------------
    /////////////////////////  Public Static Methods //////////////////////////
    //-------------------------------------------------------------------------
    /**
     * Set up the database
     *
     * @param type type of the database
     * @param host server where the database is hosted
     * @param user user that wants to user the database
     * @param password the user's password
     * @param dbname the database to access
     * @param prefix the table prefix
     * @param logPath the log path or null to not log any transaction
     * @param initDBReader the reader that initializes the database or null if
     * the initialization is required
     * @return true if the connection and the setup was successful
     */
    public static boolean initDataSink(String type, String host, String user,
            String password, String dbname, String prefix, String logPath,
            Reader initDBReader)
    {
        instance = createInstance(type);
        if (instance == null)
        {
            throw new IllegalArgumentException(
                    "The type '" + type + "' is not a registered database type and cannot be used.");
        }
        if (!instance.connect(host, user, password, dbname, prefix, logPath))
        {
            throw new IllegalStateException(
                    "Could not connect to database. All transactions won't be executed!");
        }
        if (!setupDatabase(initDBReader))
        {
            instance.disconnect();
            instance = null;
            return false;
        }
        return true;
    }

    public static void disposeDataSink()
    {
        if (instance != null)
        {
            instance.disconnect();
        }
    }

    public static IDataSink getInstance()
    {
        return instance;
    }

    public static IDataSink createInstance(String type)
    {
        switch (type.toLowerCase())
        {
            case "mysql":
                return new MySQLDataSink();
        }
        return null;
    }

    /**
     * Sets up all the tables based on the given reader.
     *
     * @param reader Set to null if no setup required
     * @return true if setting up the database was successful.
     */
    public static boolean setupDatabase(Reader reader)
    {
        if (reader == null)
        {
            return true;
        }
        try
        {
            int c;
            StringBuilder sql = new StringBuilder();
            while ((c = reader.read()) != -1)
            {
                sql.append((char) c);
            }

            int result = instance.executeMultipleQueries(sql.toString());
            if (result == -1)
            {
                Log.warning("Setup database failed");
            }
            else
            {
                Log.info("Setup database Sucessful");
            }
            return result != -1;
        }
        catch (IOException ex)
        {
            Log.error("Could not read database setup file", ex);
        }
        return false;
    }

}
