package com.example.employees.controller.cookies_and_files;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class CookieEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Cookie cookie1 = new Cookie("first_cookie", "value1");
        Cookie cookie2 = new Cookie("second_cookie", "value2");
        response.addCookie(cookie1);
        response.addCookie(cookie2);

        Cookie[] cookies = request.getCookies();
        PrintWriter writer = response.getWriter();

        writer.println("<html>");
        for (Cookie cookie : cookies) {
            writer.println("<p>" + cookie.getName() + " : " + cookie.getValue() + "</p>");
        }
        writer.println("<br>");
        writer.println("<a href=\"/employees_war_exploded\">Main Page</a><br>");
        writer.println("<a href=\"/employees_war_exploded/get_cookie_servlet/" + cookie1.getName() + "\">Show first cookie</a><br>");
        writer.println("<a href=\"/employees_war_exploded/get_cookie_servlet/" + cookie2.getName() + "\">Show second cookie</a><br>");
        writer.println("</html>");
    }
}
