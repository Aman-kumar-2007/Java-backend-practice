package org.example.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

@WebFilter("/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest =
                (HttpServletRequest) request;

        long startTime = System.currentTimeMillis();

        System.out.println(
                "Incoming request: "
                        + httpRequest.getMethod()
                        + " "
                        + httpRequest.getRequestURI()
        );

        try {
            // Pass request to the next filter or Servlet
            chain.doFilter(request, response);

        } finally {
            long duration =
                    System.currentTimeMillis() - startTime;

            System.out.println(
                    "Request completed in "
                            + duration + " ms"
            );
        }
    }
}