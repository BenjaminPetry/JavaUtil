/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.util.Util;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Static class to ease the access to the Database
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
            
    
    public static boolean init(DBConfig config)
    {
        if (sink == null)
        {
            return true;
        }
        if (!DataSinkFactory.initDataSink(config.getType(), config.getHost(), config.getUser(), config.getPassword(), config.getDbname(), config.getPrefix(), config.getLogPath(), config.getInitResource()))
        {
            return false;
        }
        sink = DataSinkFactory.getInstance();
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
    
    public boolean isConnected()
    {
        return sink != null;
    }
    
    public int count(String table, String whereclause, Object... parameters)
    {
        return sink.count(table, whereclause, parameters);
    }

    public ResultSet select(String query, Object... parameters)
    {
        return sink.select(query, parameters);
    }

    public boolean selectSet(String query)
    {
        return sink.selectSet(query);
    }

    public ResultSet selectExecute(Object... parameters)
    {
        return sink.selectExecute(parameters);
    }

    public int insert(String query, Object... parameters)
    {
        return sink.insert(query, parameters);
    }

    public boolean insertSet(String query)
    {
        return sink.insertSet(query);
    }

    public int insertExecute(Object... parameters)
    {
        return sink.insertExecute(parameters);
    }

    public int update(String query, Object... parameters)
    {
        return sink.update(query, parameters);
    }

    public boolean updateSet(String query)
    {
        return sink.updateSet(query);
    }

    public int updateExecute(Object... parameters)
    {
        return sink.updateExecute(parameters);
    }

    public int delete(String query, Object... parameters)
    {
        return sink.delete(query, parameters);
    }

    public boolean deleteSet(String query)
    {
        return sink.deleteSet(query);
    }

    public int deleteExecute(Object... parameters)
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
            Logger.getLogger(Util.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
