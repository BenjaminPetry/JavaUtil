/*
 * Benjamin Petry (www.bpetry.de)
 * Copyright 2017 by Benjamin Petry.
 * This software is provided on an "AS IS" BASIS,
 * without warranties or conditions of any kind, either express or implied.
 */
package de.bpetry.data;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * MySQL implementation of a data sink
 *
 * @author Benjamin Petry
 */
public class MySQLDataSink extends SQLDataSink
{

    @Override
    protected Connection createConnection(String host, String username,
            String password, String database) throws Exception
    {
        Class.forName("com.mysql.jdbc.Driver").newInstance(); // Load the driver
        return DriverManager
                    .getConnection("jdbc:mysql://" + host + "/" + database + "?"
                            + "user=" + username + "&password=" + password);
    }
}
