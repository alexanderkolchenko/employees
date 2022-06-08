package com.example.employees.controller.cookies_and_files;

import javax.servlet.http.*;
import java.io.*;

public class DownloadFileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String uri = request.getRequestURI();
        String filename = uri.substring(uri.lastIndexOf("/") + 1);

        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "attachment");

        String rPath = "/WEB-INF/classes/files";
        String aPath = getServletContext().getRealPath(rPath);

        try (InputStream in = new FileInputStream(aPath + "/" + filename);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[1048];

            int numBytesRead;
            while ((numBytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, numBytesRead);
            }
        }
    }
}
