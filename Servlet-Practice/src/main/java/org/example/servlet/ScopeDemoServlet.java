package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.ServletContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/scope-demo")
public class ScopeDemoServlet extends HttpServlet {

    private static final String COUNTER_KEY = "scopeDemoActions";

    private AtomicInteger getCounter(ServletContext context) {
        synchronized (context) {
            AtomicInteger counter =
                    (AtomicInteger) context.getAttribute(COUNTER_KEY);

            if (counter == null) {
                counter = new AtomicInteger(0);
                context.setAttribute(COUNTER_KEY, counter);
            }

            return counter;
        }
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Don't create a new session just to read existing data.
        HttpSession session = request.getSession(false);

        String username = session == null
                ? null
                : (String) session.getAttribute("demoUsername");

        // A new GET request generally has no previous request attributes.
        String message =
                (String) request.getAttribute("requestMessage");

        int count = getCounter(getServletContext()).get();

        renderPage(request, response, message, username, count);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        if (name == null || name.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Name is required"
            );
            return;
        }

        name = name.trim();

        // 1. Request scope
        request.setAttribute(
                "requestMessage",
                "Form submitted successfully"
        );

        // 2. Session scope
        HttpSession session = request.getSession();
        session.setAttribute("demoUsername", name);

        // 3. Application scope
        AtomicInteger counter = getCounter(getServletContext());
        int count = counter.incrementAndGet();

        response.setContentType("text/html;charset=UTF-8");

        renderPage(
                request,
                response,
                (String) request.getAttribute("requestMessage"),
                (String) session.getAttribute("demoUsername"),
                count
        );
    }

    private void renderPage(
            HttpServletRequest request,
            HttpServletResponse response,
            String message,
            String username,
            int count
    ) throws IOException {

        PrintWriter out = response.getWriter();

        String baseUrl = request.getContextPath() + "/scope-demo";

        out.println("<!DOCTYPE html>");
        out.println("<html><head><meta charset='UTF-8'>");
        out.println("<title>Servlet Scopes</title></head><body>");

        out.println("<h1>Servlet Scope Demo</h1>");

        out.println("<h3>Request Scope</h3>");
        out.println("<p>Message: " + escapeHtml(message) + "</p>");

        out.println("<h3>Session Scope</h3>");
        out.println("<p>Username: " + escapeHtml(username) + "</p>");

        out.println("<h3>Application Scope</h3>");
        out.println("<p>Total form submissions: " + count + "</p>");

        out.println("<hr>");

        out.println("<form method='post' action='" + baseUrl + "'>");
        out.println("<label>Name: </label>");
        out.println("<input type='text' name='name' required>");
        out.println("<button type='submit'>Submit</button>");
        out.println("</form>");

        out.println("<p><a href='" + baseUrl
                + "'>Make a new GET request</a></p>");

        out.println("</body></html>");
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "(not available)";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}