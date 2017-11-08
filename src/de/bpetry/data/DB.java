/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.util.Log;
import java.io.Reader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

/**
 * Static class to ease the access to the Database
 *
 * @author Benjamin Petry
 */
public class DB
{
    //-------------------------------------------------------------------------
    ////////////////////////////////  Constants ///////////////////////////////
    //-------------------------------------------------------------------------

    final public static long DATETIME_ZERO = 86400001; // in ms

    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------
    private static IDataSink sink = null;
    private static DBConfig config;

    public static boolean init(DBConfig config)
    {
        if (sink != null)
        {
            dispose();
        }
        Reader initSourceReader = config.isAutoSetup() ? config.getInitResource() : null;
        if (!DataSinkFactory.initDataSink(config.getType(), config.getHost(),
                config.getUser(), config.getPassword(), config.getDbname(),
                config.getPrefix(), config.getLogPath(), initSourceReader))
        {
            return false;
        }
        sink = DataSinkFactory.getInstance();
        DB.config = config;
        return true;
    }

    public static void dispose()
    {
        DataSinkFactory.disposeDataSink();
        sink = null;
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    public static boolean setup()
    {
        return DataSinkFactory.setupDatabase(config.getInitResource());
    }

    public static boolean isConnected()
    {
        return sink != null;
    }

    public static int count(String table, String whereclause,
            Object... parameters)
    {
        return sink.count(table, whereclause, parameters);
    }

    public static ResultSet select(String query, Object... parameters)
    {
        return sink.select(query, parameters);
    }

    public static boolean selectSet(String query)
    {
        return sink.selectSet(query);
    }

    public static ResultSet selectExecute(Object... parameters)
    {
        return sink.selectExecute(parameters);
    }

    public static int insert(String query, Object... parameters)
    {
        return sink.insert(query, parameters);
    }

    public static boolean insertSet(String query)
    {
        return sink.insertSet(query);
    }

    public static int insertExecute(Object... parameters)
    {
        return sink.insertExecute(parameters);
    }

    public static int update(String query, Object... parameters)
    {
        return sink.update(query, parameters);
    }

    public static boolean updateSet(String query)
    {
        return sink.updateSet(query);
    }

    public static int updateExecute(Object... parameters)
    {
        return sink.updateExecute(parameters);
    }

    public static int delete(String query, Object... parameters)
    {
        return sink.delete(query, parameters);
    }

    public static boolean deleteSet(String query)
    {
        return sink.deleteSet(query);
    }

    public static int deleteExecute(Object... parameters)
    {
        return sink.deleteExecute(parameters);
    }

    // UTIL FUNCTIONS
    public static Timestamp toTS(long msUTC)
    {
        if (msUTC == 0)
        {
            msUTC = DATETIME_ZERO;
        }
        return new Timestamp(msUTC);
    }

    public static long fromTS(ResultSet set, String colLabel)
    {
        try
        {
            Timestamp st = set.getTimestamp(colLabel);
            long time = st.getTime();
            return (time == DATETIME_ZERO) ? 0 : time;
        }
        catch (SQLException ex)
        {
            Log.error("Cannot retrieve timestamp", ex);
        }
        return -1;
    }
}
