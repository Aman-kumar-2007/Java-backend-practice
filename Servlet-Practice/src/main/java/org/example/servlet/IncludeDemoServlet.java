package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/include-demo")
public class IncludeDemoServlet extends HttpServlet{

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException, ServletException {

        response.setContentType("text/html;charset=UTF-8");

        var dispatcher =
                request.getRequestDispatcher("header.html");

        dispatcher.include(request, response);

        PrintWriter out = response.getWriter();

        out.println("<main>");
        out.println("<h2>Employee Dashboard</h2>");
        out.println("<p>This is the main page content.</p>");
        out.println("</main>");

        request.getRequestDispatcher("footer.html")
                .include(request, response);
    }
}