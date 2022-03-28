package com.example.employees.controller.permission;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SetAdminPermissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("User", "admin" );
        response.addCookie(new Cookie("User", "admin"));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
