package com.example.employees.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionDB {
    Connection getConnection() throws SQLException;
}
