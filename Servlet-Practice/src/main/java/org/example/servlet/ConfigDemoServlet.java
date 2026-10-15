package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(
        value = "/config-demo",
        initParams = {
                @WebInitParam(
                        name = "department",
                        value = "CSE"
                ),
                @WebInitParam(
                        name = "pageSize",
                        value = "20"
                )
        }
)
public class ConfigDemoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        // Servlet-specific configuration
        String department =
                getServletConfig().getInitParameter("department");

        String pageSize =
                getServletConfig().getInitParameter("pageSize");

        // Application-wide configuration
        String collegeName =
                getServletContext().getInitParameter("collegeName");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html><body>");

            out.println("<h2>Servlet Configuration Demo</h2>");

            out.println("<p>Department: " + department + "</p>");
            out.println("<p>Page Size: " + pageSize + "</p>");
            out.println("<p>College: " + collegeName + "</p>");

            out.println("</body></html>");
        }
    }
}