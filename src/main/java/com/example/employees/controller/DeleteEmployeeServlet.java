package com.example.employees.controller;

import com.example.employees.dao.ConnectionDao;
import com.example.employees.dao.EmployeeDao;

import javax.servlet.http.*;
import java.io.IOException;

public class DeleteEmployeeServlet extends HttpServlet {
    private static EmployeeDao employeeDao;

    static {
        employeeDao = new EmployeeDao(new ConnectionDao());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        //todo redirect and ex
        employeeDao.deleteEmployeeByID(id);
        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
