package com.example.employees.controller;

import com.example.employees.dao.ConnectionDao;
import com.example.employees.dao.EmployeeDao;
import com.example.employees.model.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.NoSuchElementException;

public class EditEmployeeServlet extends HttpServlet {

    private final static Logger log = LoggerFactory.getLogger(EditEmployeeServlet.class);
    private static final EmployeeDao employeeDao;
    static {
        employeeDao = new EmployeeDao(new ConnectionDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        Employee employee;
        try {
            employee = employeeDao.getEmployeeByID(id).orElseThrow(NoSuchElementException::new);
        } catch (NoSuchElementException e) {
            response.sendRedirect("/employees_war_exploded/error_page.jsp");
            return;
        }

        request.setAttribute("employee", employee);
        request.getRequestDispatcher("/edit_employee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        System.out.println(request.getClass() + " s");
        Employee employee;

        try {
            employee = AddEmployeeServlet.createEmployeeFromRequest(request);
        } catch (RuntimeException e) {
            response.sendRedirect("/employees_war_exploded/error_page.jsp");
            return;
        }

        employee.setId(Integer.parseInt(id));

        try {
            employeeDao.updateEmployees(employee);
        } catch (SQLException e) {
            log.error("Error of connection while updating employee: {} ", e.getMessage());
            response.sendRedirect("/employees_war_exploded/error_page.jsp");
            return;
        }

        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
