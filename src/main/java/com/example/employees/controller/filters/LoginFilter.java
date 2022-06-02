package com.example.employees.controller.filters;

import com.example.employees.dao.UserDao;
import com.example.employees.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;


public class LoginFilter implements Filter {

    private final static Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        PrintWriter writer = response.getWriter();

        final HttpServletResponse resp = (HttpServletResponse) response;
        final HttpServletRequest req = (HttpServletRequest) request;

        final String login = req.getParameter("login");
        final String password = req.getParameter("password");

        User user = UserDao.getUserRole(login, password);

        if (user.getRole().equals(User.ROLE.UNKNOWN)) {
            writer.println("<html>");
            writer.println("You don't have permission");
            writer.println("</br><a href=\"/employees_war_exploded/login.jsp\">main page</a>");
            writer.println("</html>");

        } else {

            final HttpSession session = req.getSession();
            session.setMaxInactiveInterval(1800);

            Cookie cookie = new Cookie("userTime", "value");
            cookie.setMaxAge(1800);
            resp.addCookie(cookie);

            session.setAttribute("login", user.getLogin());
            session.setAttribute("role", user.getRole().toString());

            //логируется только вход и выход админов
            if(user.getRole().equals(User.ROLE.ADMIN)) {
                log.info("Admin logged in");
            }

           // chain.doFilter(request, response);
            resp.sendRedirect("/employees_war_exploded/index.jsp");
        }
    }
}
