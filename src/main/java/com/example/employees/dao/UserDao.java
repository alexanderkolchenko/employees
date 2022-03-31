package com.example.employees.dao;
import com.example.employees.model.User;

import java.sql.*;

public class UserDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/ee_db?characterEncoding=UTF-8";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String FIND_USER = "SELECT login FROM users WHERE login = ? AND password = ?";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static User getUserRole(String login, String password) {
        User user = new User();
        user.setRole(User.ROLE.UNKNOWN);
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement(FIND_USER);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                if (rs.getString("login").equals("admin")) {
                    user.setRole(User.ROLE.ADMIN);
                } else {
                    user.setRole(User.ROLE.USER);
                }
                user.setLogin(rs.getString("login"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
