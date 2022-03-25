package com.example.employees.controller;

import com.example.employees.dao.EmployeeDao;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class DeleteEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);
        EmployeeDao.deleteEmployeeByID(id);
        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
