package com.example.employees.dao;

import com.example.employees.model.Employee;

import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EmployeeDao {
    private static final String URL = "jdbc:postgresql://localhost:5432/ee_db?characterEncoding=UTF-8";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String INSERT_EMPLOYEE = "INSERT INTO employee (e_name, e_surname, e_position, e_email, e_city) VALUES(?,?,?,?,?)";
    private static final String UPDATE_EMPLOYEE = "UPDATE employee SET e_name = ?, e_surname = ?, e_position = ?, e_email = ?, e_city = ? WHERE e_id =";

    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Employee> getEmployeesList() {

        ArrayList<Employee> employees = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee ORDER BY e_id");
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Employee e = setParametersInEmployee(new Employee(), rs);
                employees.add(e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    public static void addEmployees(Employee employee) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(INSERT_EMPLOYEE);
            setParametersInStatement(statement, employee);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Employee> getEmployeeByID(String id) {

        Employee employee = null;

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("SELECT * FROM employee WHERE e_id =" + id);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                employee = setParametersInEmployee(new Employee(), rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(employee);
    }

    public static void updateEmployees(Employee employee) {

        getEmployeeByID(String.valueOf(employee.getId())).orElseThrow(NoSuchElementException::new);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            PreparedStatement statement = connection.prepareStatement(UPDATE_EMPLOYEE + employee.getId());
            setParametersInStatement(statement, employee);
            statement.execute();
        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    public static void deleteEmployeeByID(String id) {

        getEmployeeByID(id).orElseThrow(NoSuchElementException::new);

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {

            PreparedStatement statement = connection.prepareStatement("DELETE FROM employee WHERE e_id = " + id);
            statement.execute();

        } catch (SQLException exc) {
            exc.printStackTrace();
        }
    }

    private static Employee setParametersInEmployee(Employee e, ResultSet rs) throws SQLException {
        e.setId(rs.getInt("e_id"));
        e.setName(rs.getString("e_name"));
        e.setSurname(rs.getString("e_surname"));
        e.setPosition(rs.getString("e_position"));
        e.setEmail(rs.getString("e_email"));
        e.setCity(rs.getString("e_city"));
        return e;
    }

    private static void setParametersInStatement(PreparedStatement s, Employee e) throws SQLException {
        s.setString(1, e.getName());
        s.setString(2, e.getSurname());
        s.setString(3, e.getPosition());
        s.setString(4, e.getEmail());
        s.setString(5, e.getCity());
    }

    public static void checkParametersByEmployee(HttpServletRequest request) {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        String city = request.getParameter("city");
        if (name.equals("") || surname.equals("") || position.equals("") || email.equals("") || city.equals("")) {
            throw new IllegalArgumentException();
        }
    }
}
