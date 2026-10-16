package org.example.session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("""
                <!DOCTYPE html>
                <html>
                <head><title>Login Demo</title></head>
                <body>
                    <h2>Session Login Demo</h2>

                    <form method="post" action="login">
                        <label>Username:</label>
                        <input type="text" name="username" required>
                        <button type="submit">Continue</button>
                    </form>
                </body>
                </html>
                """);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");

        if (username == null || username.isBlank()) {
            response.sendError(400, "Username is required");
            return;
        }

        username = username.trim();

        // Create/get the user's session.
        HttpSession session = request.getSession();

        // Prevent reuse of an existing session ID after login.
        request.changeSessionId();

        session.setAttribute("username", username);
        session.setMaxInactiveInterval(15 * 60);

        response.sendRedirect(
                response.encodeRedirectURL(
                        request.getContextPath() + "/dashboard"
                )
        );
    }
}