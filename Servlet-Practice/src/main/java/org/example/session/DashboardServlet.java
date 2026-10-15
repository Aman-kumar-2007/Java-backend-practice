package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null ||
                session.getAttribute("username") == null) {

            response.sendRedirect(
                    response.encodeRedirectURL(
                            request.getContextPath() + "/login"
                    )
            );
            return;
        }

        String username =
                (String) session.getAttribute("username");

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html><html><body>");

            out.println("<h1>Dashboard</h1>");
            out.println("<p>Welcome, "
                    + escapeHtml(username) + "</p>");

            out.println("<form method='post' action='logout'>");
            out.println("<button type='submit'>Logout</button>");
            out.println("</form>");

            out.println("</body></html>");
        }
    }

    private String escapeHtml(String value) {
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}