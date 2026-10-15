package org.example.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register-redirect")
public class RegisterRedirectServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");

        if (name == null || name.isBlank()) {
            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Name is required"
            );
            return;
        }

        // Real application mein yahan database save hoga.
        // Demo mein sirf successful processing assume kar rahe hain.

        response.sendRedirect(
                request.getContextPath() + "/success.html"
        );
    }
}