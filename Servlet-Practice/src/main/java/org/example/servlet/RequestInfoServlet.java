package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/request-info/*")
public class RequestInfoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/plain;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {

            out.println("Request URI: "
                    + request.getRequestURI());

            out.println("Context Path: "
                    + request.getContextPath());

            out.println("Servlet Path: "
                    + request.getServletPath());

            out.println("Path Info: "
                    + request.getPathInfo());

            out.println("Query String: "
                    + request.getQueryString());

            out.println("Request URL: "
                    + request.getRequestURL());

            out.println("Active Parameter: "
                    + request.getParameter("active"));

            out.println("Sort Parameter: "
                    + request.getParameter("sort"));
        }
    }
}