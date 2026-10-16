package org.example.servlet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.model.User;
import org.hibernate.SessionFactory;

import java.io.IOException;

import static org.example.util.HibernateUtil.getSessionFactory;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException, ServletException {
        response.setContentType("text/html");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if (username == null || username.isBlank()
                || password == null || password.isBlank()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST
                    ,"Fill all details");
            return;
        }
        username = username.trim();

        User user = org.example.dao.UserDao.findByUsername(getSessionFactory(),username);

        if( user != null && password.equals(user.getPasswordHash())){
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());

            response.sendRedirect(
                    response.encodeRedirectURL(request.getContextPath() + "/student-form.html")
            );
        } else {
                response.sendError(
                        HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid username or password"
                );
        }
    }
}
