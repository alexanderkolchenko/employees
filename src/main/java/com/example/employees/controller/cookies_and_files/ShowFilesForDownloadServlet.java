package com.example.employees.controller.cookies_and_files;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ShowFilesForDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (PrintWriter writer = new PrintWriter(response.getWriter())) {
            String rPath = "/WEB-INF/classes/files";
            String aPath = getServletContext().getRealPath(rPath);
            File file = new File(aPath);

            Set<String> listOfFiles = Stream.of(Objects.requireNonNull(file.listFiles()))
                    .filter(f -> !f.isDirectory())
                    .map(File::getName).collect(Collectors.toSet());

            writer.write("<html>");
            for (String listOfFile : listOfFiles) {
                if (!listOfFile.equals("value.txt"))
                    writer.write("<a href = \"download_file_servlet/" + listOfFile + "\">" + listOfFile + "</a><br>");
            }
            writer.write("<br>");
            writer.write("<a href=\"/employees_war_exploded/send_file_servlet\">Get to Upload Page</a><br>");
            writer.write("</html>");
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }
}
