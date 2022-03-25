package com.example.employees.controller;

import com.example.employees.dao.EmployeeDao;
import com.example.employees.model.Employee;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.ArrayList;

public class AddEmployeeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        String city = request.getParameter("city");

        EmployeeDao.checkParametersByEmployee(request);

        Employee employee = new Employee();
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPosition(position);
        employee.setEmail(email);
        employee.setCity(city);

        EmployeeDao.addEmployees(employee);
        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
