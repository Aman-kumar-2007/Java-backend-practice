package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;

import java.io.IOException;

@WebServlet("/forward-demo")
public class ForwardDemoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        String name = request.getParameter("name");

        if (name == null || name.isBlank()) {
            name = "Guest";
        }

        // Store data in the current request
        request.setAttribute("employeeName", name);

        // Forward the same request to another servlet
        request.getRequestDispatcher("/show-employee")
                .forward(request, response);
    }
}