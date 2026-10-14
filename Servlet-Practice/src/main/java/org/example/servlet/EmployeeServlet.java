package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/employee")
public class EmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws IOException, ServletException {

        response.sendRedirect(
                request.getContextPath() + "/employee-form.html"
        );
    }
    
    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException,ServletException {
        response.setContentType("text/html");

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String salaryText = request.getParameter("salary");

        if (name == null || name.isBlank()
                || email == null || email.isBlank()
                || salaryText == null || salaryText.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "All fields are required"
            );
            return;
        }

        double salary;

        try {
            salary = Double.parseDouble(salaryText);

            if (!Double.isFinite(salary) || salary <= 0) {
                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Salary must be a positive number"
                );
                return;
            }

        } catch (NumberFormatException e) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Invalid salary"
            );
            return;
        }

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html><body>");
            out.println("<h2>Employee Registration Received</h2>");
            out.println("<p>Name: " + escapeHtml(name.trim()) + "</p>");
            out.println("<p>Email: " + escapeHtml(email.trim()) + "</p>");
            out.println("<p>Salary: " + salary + "</p>");
            out.println("<a href='employee-form.html'>Back</a>");
            out.println("</body></html>");
        }
    }

    private String escapeHtml(String value) {
        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }

}
