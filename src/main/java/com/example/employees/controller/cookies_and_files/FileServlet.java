package com.example.employees.controller.cookies_and_files;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Optional;


public class FileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter writer = response.getWriter();
        writer.write("<html>");
        writer.write("<form action=\"send_file_servlet\" method=\"post\" enctype=\"multipart/form-data\">" +
                "<input type=\"file\" name=\"file\" required>" +
                "<button type=\"submit\">Send file</button>" +
                "</form><br>");
        writer.write("<a href=\"/employees_war_exploded\">Main Page</a><br>");
        writer.write("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        part.write(part.getSubmittedFileName());
        response.getWriter().write(
                "<html>" +
                        "The file uploaded successfully.<br>" +
                        "<a href=\"send_file_servlet\">Back</a>" +
                        "</html>");
    }
}
