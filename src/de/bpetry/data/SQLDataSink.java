/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import de.bpetry.util.Log;
import de.bpetry.util.Util;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Generic SQL Database implementation of the IDataSink interface
 *
 * @author Benjamin Petry
 */
public abstract class SQLDataSink implements IDataSink
{

    private enum SQLCommand
    {

        Select,
        Update,
        Insert,
        Delete,
        Count
    }

    //-------------------------------------------------------------------------
    ////////////////////////  Private Static Variables ////////////////////////
    //-------------------------------------------------------------------------
    private static Connection connect = null;
    private static PreparedStatement preparedStatement = null;
    private static ResultSet resultSet = null;
    private static String prefix = "";
    private static SQLCommand currentCommand = SQLCommand.Update;
    private static FileWriter log;

    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------
    @Override
    public boolean connect(String host, String username, String password,
            String database, String prefix, String logPath)
    {
        try
        {
            SQLDataSink.prefix = prefix;
            connect = createConnection(host, username, password, database);
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy_HHmm");
            String dateS = sdf.format(new Date());
            log = null;
            if (logPath != null && !logPath.isEmpty())
            {
                String filename = Util.normalizePath(logPath) + "db_" + dateS + ".sql.tmp";
                log = new FileWriter(new File(filename), true);
            }
            return true;
        }
        catch (Exception ex)
        {
            Log.error("Could not connect to database", ex);
        }

        return false;
    }

    @Override
    public boolean disconnect()
    {
        try
        {
            if (resultSet != null)
            {
                resultSet.close();
            }

            if (preparedStatement != null)
            {
                preparedStatement.close();
            }
            if (log != null)
            {
                log.close();
            }
            if (connect != null)
            {
                connect.close();
            }
            return true;
        }
        catch (SQLException | IOException ex)
        {
            Log.error("Could not close database connection",
                    ex);
        }
        return false;
    }

    @Override
    public int count(String tableclause, String whereclause,
            Object... parameters)
    {
        int result = -1;
        try
        {
            String query = "SELECT COUNT(*) FROM " + tableclause + " WHERE " + whereclause;

            if (select(query, parameters) != null && resultSet.next())
            {
                result = resultSet.getInt(1);
            }
            resultSet.close();
        }
        catch (SQLException e)
        {
            Log.error("Could not retrieve count result", e);
        }
        return result;
    }

    @Override
    public ResultSet select(String query, Object... parameters)
    {
        return (selectSet(query)) ? selectExecute(parameters) : null;
    }

    @Override
    public boolean selectSet(String query)
    {
        return setQuery(query, SQLCommand.Select);
    }

    @Override
    public ResultSet selectExecute(Object... parameters)
    {
        if (executeQuery(parameters) >= 0)
        {
            return resultSet;
        }
        return null;
    }

    @Override
    public int insert(String query, Object... parameters)
    {
        return (insertSet(query)) ? insertExecute(parameters) : -1;
    }

    @Override
    public boolean insertSet(String query)
    {
        return setQuery(query, SQLCommand.Insert);
    }

    @Override
    public int insertExecute(Object... parameters)
    {
        int result = executeQuery(parameters);
        if (result != -1)
        {
            try
            {
                ResultSet keySet = preparedStatement.getGeneratedKeys();
                if (keySet.first())
                {
                    int id = keySet.getInt(1);
                    logN("\t\tID\t" + id);
                    return id;
                }
                return 1;
            }
            catch (SQLException ex)
            {
                Log.error("Could not retrieve id from insert result", ex);
            }
        }
        return -1;
    }

    @Override
    public int update(String query, Object... parameters)
    {
        return (updateSet(query)) ? updateExecute(parameters) : -1;
    }

    @Override
    public boolean updateSet(String query)
    {
        return setQuery(query, SQLCommand.Update);
    }

    @Override
    public int updateExecute(Object... parameters)
    {
        return executeQuery(parameters);
    }

    @Override
    public int delete(String query, Object... parameters)
    {
        return (deleteSet(query)) ? deleteExecute(parameters) : -1;
    }

    @Override
    public boolean deleteSet(String query)
    {
        return setQuery(query, SQLCommand.Delete);
    }

    @Override
    public int deleteExecute(Object... parameters)
    {
        return executeQuery(parameters);
    }

    @Override
    public int executeMultipleQueries(String queries, Object... parameters)
    {
        String[] arrayOfQueries = queries.split(";\n");
        int result = 0;
        for (String query : arrayOfQueries)
        {
            int tmpResult = update(query, parameters);
            if (tmpResult == -1)
            {
                result = -1;
            }
            else if (result != -1)
            {
                result += tmpResult;
            }
        }
        return result;
    }

    //-------------------------------------------------------------------------
    /////////////////////////////  Private Methods ////////////////////////////
    //-------------------------------------------------------------------------
    private boolean setQuery(String query, SQLCommand command)
    {
        try
        {
            if (!prefix.isEmpty())
            {
                query = query.replace("#__", prefix);
            }
            if (command == SQLCommand.Insert)
            {
                preparedStatement = connect.prepareStatement(query,
                        Statement.RETURN_GENERATED_KEYS);
            }
            else
            {
                preparedStatement = connect.prepareStatement(query);
            }
            logN("\t" + query);
            SQLDataSink.currentCommand = command;
            return true;
        }
        catch (SQLException ex)
        {
            Log.error("Could not prepare sql query", ex);
        }
        return false;
    }

    private int executeQuery(Object... parameters)
    {
        try
        {
            int counter = 0;
            for (Object parameter : parameters)
            {
                counter++;
                if (parameter instanceof Timestamp)
                {
                    preparedStatement.setTimestamp(counter,
                            (Timestamp) parameter);
                }
                else
                {
                    preparedStatement.setObject(counter, parameter);
                }

                String param = (parameter instanceof Timestamp)
                        ? (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(
                                (Timestamp) parameter)
                        : parameter.toString();
                logN("\t\t" + counter + "\t" + param);
            }
            if (currentCommand == SQLCommand.Select || currentCommand == SQLCommand.Count)
            {
                resultSet = preparedStatement.executeQuery();
            }
            else
            {
                return preparedStatement.executeUpdate();
            }
            return 0;
        }
        catch (SQLException ex)
        {
            Log.error("Could not execute sql query", ex);
        }
        return -1;
    }

    private void logN(String s)
    {
        if (log == null)
        {
            return;
        }
        try
        {
            log.write(s + "\n");
        }
        catch (IOException ex)
        {
            Log.error("Could not write database log", ex);
        }
    }

    //-------------------------------------------------------------------------
    ////////////////////////////  Protected Methods ///////////////////////////
    //-------------------------------------------------------------------------
    protected abstract Connection createConnection(String host, String username,
            String password, String database) throws Exception;

}
