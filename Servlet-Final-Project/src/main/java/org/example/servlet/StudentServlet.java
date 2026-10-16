package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.example.dao.StudentDao;
import org.example.model.Student;
import org.example.util.HibernateUtil;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/students")
public class StudentServlet extends HttpServlet {

    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        factory = HibernateUtil.getSessionFactory();
    }

    // Display all students
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        List<Student> students =
                StudentDao.getAllStudents(factory);

        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html><head><title>Students</title></head><body>");

            out.println("<h1>Student List</h1>");

            out.println("<a href='" + request.getContextPath()
                    + "/student-form.html'>Add Student</a>");

            out.println("<table border='1' cellpadding='8'>");
            out.println("<tr><th>ID</th><th>Name</th>"
                    + "<th>Email</th><th>Department</th></tr>");

            for (Student student : students) {
                out.println("<tr>");

                out.println("<td>" + student.getId() + "</td>");
                out.println("<td>" + escapeHtml(student.getName()) + "</td>");
                out.println("<td>" + escapeHtml(student.getEmail()) + "</td>");

                out.println("<td>"
                        + (student.getDepartment() == null
                        ? "Not Assigned"
                        : escapeHtml(student.getDepartment().getName()))
                        + "</td>");

                out.println("</tr>");
            }

            out.println("</table>");
            out.println("</body></html>");
        }
    }

    // Add a student
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name == null || name.isBlank()
                || email == null || email.isBlank()) {

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Name and email are required"
            );
            return;
        }

        Student student = new Student();
        student.setName(name.trim());
        student.setEmail(email.trim());

        StudentDao.addStudent(factory, student);

        response.sendRedirect(
                response.encodeRedirectURL(
                        request.getContextPath() + "/students"
                )
        );
    }

    private String escapeHtml(String value) {
        if (value == null) {
            return "";
        }

        return value
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#x27;");
    }
}