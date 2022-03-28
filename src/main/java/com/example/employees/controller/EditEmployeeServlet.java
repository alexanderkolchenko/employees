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
        System.out.println(response.getHeader("User"));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String position = request.getParameter("position");
        String email = request.getParameter("email");
        String city = request.getParameter("city");

        EmployeeDao.checkParametersByEmployee(request);

        Employee employee = new Employee();
        employee.setId(Integer.parseInt(id));
        employee.setName(name);
        employee.setSurname(surname);
        employee.setPosition(position);
        employee.setEmail(email);
        employee.setCity(city);

        EmployeeDao.updateEmployees(employee);

        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
