package org.example.session;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/theme")
public class ThemeServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String theme = "light";

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("theme".equals(cookie.getName())) {
                    String value = cookie.getValue();

                    if ("light".equals(value) ||
                            "dark".equals(value)) {
                        theme = value;
                    }
                }
            }
        }

        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            out.println("<h2>Current theme: " + theme + "</h2>");

            out.println("""
                <form method="post" action="theme">
                    <select name="theme">
                        <option value="light">Light</option>
                        <option value="dark">Dark</option>
                    </select>
                    <button type="submit">Save Theme</button>
                </form>
                """);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {

        String theme = request.getParameter("theme");

        if (!"light".equals(theme) && !"dark".equals(theme)) {
            response.sendError(400, "Invalid theme");
            return;
        }

        Cookie cookie = new Cookie("theme", theme);

        String contextPath = request.getContextPath();
        cookie.setPath(contextPath.isEmpty() ? "/" : contextPath);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setHttpOnly(true);
        cookie.setSecure(request.isSecure());

        response.addCookie(cookie);

        response.sendRedirect(
                response.encodeRedirectURL(
                        request.getContextPath() + "/theme"
                )
        );
    }
}