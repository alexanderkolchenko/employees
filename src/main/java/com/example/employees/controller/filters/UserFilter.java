package com.example.employees.controller.filters;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class UserFilter implements Filter {
    public void init(FilterConfig config) {

    }

    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        Map<String, Cookie> map = new HashMap<>();

        Cookie[] cookies = req.getCookies();
        for (Cookie cookie : cookies) {
            map.put(cookie.getName(), cookie);
        }
        if (map.get("User").getValue().equals("admin")) {
            chain.doFilter(request, response);
        } else {
            writer.println("You don't have permission");
        }
    }
}
