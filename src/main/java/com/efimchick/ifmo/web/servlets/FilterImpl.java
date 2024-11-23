package com.efimchick.ifmo.web.servlets;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.FilterChain;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/calc/*")
public class FilterImpl implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String path = httpServletRequest.getRequestURI();

        String value = servletRequest.getReader().readLine();
        servletRequest.getReader().reset();

        if (path.equals("/calc/expression")) {
            String exp = httpServletRequest.getReader().readLine();
            httpServletRequest.getReader().reset();

            if (!exp.matches(".*[+\\-*/].*")) {
                httpServletResponse.setStatus(400);
                return;
            }
        }

        try {
            int num = Integer.parseInt(value);
            if (num < -10000 || num > 10000) {
                httpServletResponse.setStatus(403);
                return;
            }
        } catch (RuntimeException e) {e.printStackTrace();}

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    @Override
    public void destroy() {

    }
}
