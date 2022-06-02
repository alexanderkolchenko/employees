package com.example.employees.controller;

import com.example.employees.dao.EmployeeDao;
import com.example.employees.model.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.NoSuchElementException;

public class EditEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);
        Employee e = EmployeeDao.getEmployeeByID(id).orElseThrow(NoSuchElementException::new);
        request.setAttribute("employee", e);
        request.getRequestDispatcher("/edit_employee.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        Employee employee = AddEmployeeServlet.createEmployeeFromRequest(request);
        employee.setId(Integer.parseInt(id));
        EmployeeDao.updateEmployees(employee);

        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
