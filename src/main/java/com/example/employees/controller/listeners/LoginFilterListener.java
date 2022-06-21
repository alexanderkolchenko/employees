package com.example.employees.controller.listeners;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.util.EnumSet;

@WebListener
public class LoginFilterListener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {

    public LoginFilterListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("app context created");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("app context destroy");
        /* This method is called when the servlet Context is undeployed or Application Server shuts down. */
    }

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("session create");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("session destroyed");
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is added to a session. */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is removed from a session. */
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {
        /* This method is called when an attribute is replaced in a session. */
    }
}
