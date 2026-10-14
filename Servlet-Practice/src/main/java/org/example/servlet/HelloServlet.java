package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req,HttpServletResponse res)
            throws IOException , ServletException {
        res.setContentType("text/html");

        String name = req.getParameter("name");

        if (name == null || name.trim().isEmpty()) {
            name = "Guest";
        }

        PrintWriter out = res.getWriter();

        out.println("<html><body>");
        out.println("<h1>Employee Welcome Servlet</h1>");
        out.println("<h2>Welcome, " + name + "</h2>");
        out.println("</body></html>");

    }

}
