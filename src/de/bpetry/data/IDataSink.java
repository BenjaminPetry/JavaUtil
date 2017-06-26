/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import java.sql.ResultSet;

/**
 * This interface describes the connection to a data sink, usually a database.
 * Note: All integer return values of the interface's methods describe how many
 * rows have been affected (failures will return -1)
 *
 * @author Benjamin Petry
 */
public interface IDataSink
{
    //-------------------------------------------------------------------------
    /////////////////////////////  Public Methods /////////////////////////////
    //-------------------------------------------------------------------------

    // prefix: the table's prefix - use "#__" in queries in front of table names to use the prefix
    // logPath: set to null if you don't want to log all transactions
    public boolean connect(String host, String username, String password,
            String database, String prefix, String logPath); 

    public boolean disconnect();

    public int count(String table, String whereclause, Object... parameters); // whereclause has to be without "WHERE"; return -1 means failed to count

    public ResultSet select(String query, Object... parameters); // null means failed to select

    public boolean selectSet(String query);

    public ResultSet selectExecute(Object... parameters);

    public int insert(String query, Object... parameters); // returns the inserted id or -1 when it failed

    public boolean insertSet(String query);

    public int insertExecute(Object... parameters); // returns the inserted id or -1 when it failed

    public int update(String query, Object... parameters);

    public boolean updateSet(String query);

    public int updateExecute(Object... parameters);

    public int delete(String query, Object... parameters);

    public boolean deleteSet(String query);

    public int deleteExecute(Object... parameters);

    public int executeMultipleQueries(String queries, Object... parameters);
}
