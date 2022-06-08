package com.example.employees.controller;

import com.example.employees.dao.ConnectionDao;
import com.example.employees.dao.EmployeeDao;
import com.example.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;

public class AddEmployeeServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(AddEmployeeServlet.class);
    private final static EmployeeDao employeeDao;

    static {
        employeeDao = new EmployeeDao(new ConnectionDao());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Employee employee;

        try {
            employee = createEmployeeFromRequest(request);
        } catch (RuntimeException e) {
            response.sendRedirect("/employees_war_exploded/error_page.jsp");
            return;
        }

        try {
            employeeDao.addEmployees(employee);
        } catch (SQLException e) {
            response.sendRedirect("/employees_war_exploded/error_page.jsp");
            log.error("Error of connection while creating employee: {} ", e.getMessage());
            return;
        }
        response.sendRedirect("/employees_war_exploded/index.jsp");
    }

    public static Employee createEmployeeFromRequest(HttpServletRequest request) throws RuntimeException {

        checkParametersByEmployee(request);

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        String city = request.getParameter("city");

        Employee employee = new Employee();

        employee.setName(name);
        employee.setSurname(surname);
        employee.setPosition(position);
        employee.setEmail(email);
        employee.setCity(city);

        return employee;
    }

    static void checkParametersByEmployee(HttpServletRequest request) throws RuntimeException {
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
