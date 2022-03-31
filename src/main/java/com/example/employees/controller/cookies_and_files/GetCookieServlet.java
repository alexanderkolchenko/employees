package com.example.employees.controller.cookies_and_files;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class GetCookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String id = uri.substring(uri.lastIndexOf("/") + 1);

        Map<String, Cookie> map = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie);
        }

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<p>" + map.get(id).getName() + " : " + map.get(id).getValue() + "</p>");
        writer.println("<a href=\"/employees_war_exploded\">Main Page</a><br>");
        writer.println("<a href=\"/employees_war_exploded/cookie_employee_servlet\">Back to cookies</a><br>");
        writer.println("</html>");
    }
}
