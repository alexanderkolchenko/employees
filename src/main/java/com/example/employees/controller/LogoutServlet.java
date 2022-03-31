package com.example.employees.controller;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.removeAttribute("role");
        session.removeAttribute("login");
        response.sendRedirect("/employees_war_exploded/index.jsp");
    }
}
