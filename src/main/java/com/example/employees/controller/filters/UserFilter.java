package com.example.employees.controller.filters;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;


/**
 * Класс проверяет роли для доступа к редактированию справочника
 */
public class UserFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {


        PrintWriter writer = response.getWriter();
        HttpServletRequest req = (HttpServletRequest) request;
        String user = req.getSession().getAttribute("role").toString();

        if (user.equals("ADMIN")) {
            chain.doFilter(request, response);

        } else {
            writer.println("You don't have permission");
        }
    }
}


