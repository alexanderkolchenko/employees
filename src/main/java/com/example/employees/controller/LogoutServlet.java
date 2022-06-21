package com.example.employees.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.TimeZone;

public class LogoutServlet extends HttpServlet {
    private final static Logger log = LoggerFactory.getLogger(LogoutServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        //логируется только вход и выход админов
        if (session.getAttribute("role").toString().equals("ADMIN")) {
            log.info("Admin logged out");
        }

        session.removeAttribute("role");
        session.removeAttribute("login");
        session.invalidate();

        response.sendRedirect("/employees_war_exploded/login.jsp");
    }
}
