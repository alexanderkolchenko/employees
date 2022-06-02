package com.example.employees.dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionDao {

    private static DataSource dataSource;

    static {
        try {
            Class.forName("org.postgresql.Driver");
            Context context = new InitialContext();
            dataSource = (DataSource) context.lookup("java:comp/env/jdbc/employees");
        } catch (ClassNotFoundException | NamingException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
