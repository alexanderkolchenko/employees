package com.example.employees.controller.permission;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class SetUserPermissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("User", "user");
        response.addCookie(new Cookie("User", "user"));
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }
}
