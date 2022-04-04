package com.example.employees.controller.cookies_and_files;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.io.*;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Optional;
import java.util.Scanner;


public class UploadFileServlet extends HttpServlet {

    private int count;
    private File file;

    @Override
    public void init() {

        String rPath = "/WEB-INF/classes/files";
        String aPath = getServletContext().getRealPath(rPath);
        file = new File(aPath);

        try {
            count = getCountFiles();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

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
        System.out.println(getCountFiles());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Part part = request.getPart("file");
        String c = part.getSubmittedFileName();
        part.write(count + "_" + part.getSubmittedFileName());
        count++;
        setCountFiles(count);
        response.getWriter().write(
                "<html>" +
                        "The file uploaded successfully.<br>" +
                        "<a href=\"send_file_servlet\">Back</a>" +
                        "</html>");
    }

    private int getCountFiles() throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(file.getAbsolutePath() + "/value.txt"));
        int i = scanner.nextInt();
        scanner.close();
        return i;
    }

    private void setCountFiles(int count) throws IOException {
        Writer countWriter = new FileWriter(file.getAbsolutePath() + "/value.txt");
        countWriter.write(String.valueOf(count));
        countWriter.close();
    }
}
