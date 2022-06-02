package com.example.employees.dao;

import com.example.employees.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserDao {

    private final static Logger log = LoggerFactory.getLogger(UserDao.class);
    private static final String FIND_USER = "SELECT login FROM users WHERE login = ? AND password = ?";

    public static User getUserRole(String login, String password) {

        User user = new User();
        user.setRole(User.ROLE.UNKNOWN);
        try (Connection connection = ConnectionDao.getConnection()) {

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
            log.error(e.getMessage());
        }
        return user;
    }
}
