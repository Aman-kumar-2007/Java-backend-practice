package org.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/error-demo")
public class ErrorDemoServlet extends HttpServlet {

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {

//        String type = request.getParameter("type");
//
//        if ("404".equals(type)) {
//
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//
//        } else if ("exception".equals(type)) {
//
//            throw new ServletException("Demo exception");
//
//        } else {
//
//        response.setContentType("text/plain;charset=UTF-8");
//        response.getWriter().println("No error occurred.");
//
//    }

//            response.sendError(404);

//        response.setStatus(404);


            response.setContentType("text/plain;charset=UTF-8");
            response.getWriter().println("No error occurred.");
    }
}