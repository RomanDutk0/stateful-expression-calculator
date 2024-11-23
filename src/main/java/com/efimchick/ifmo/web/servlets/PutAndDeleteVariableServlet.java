package com.efimchick.ifmo.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/calc/*")
public class PutAndDeleteVariableServlet extends HttpServlet {

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String nameVariable = path.substring(path.length() - 1);
        String  value = req.getReader().readLine();

        HttpSession session = req.getSession();

        if (session.getAttribute(nameVariable) == null)
            resp.setStatus(201);
        else
            resp.setStatus(200);

        session.setAttribute(nameVariable, value);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String path = req.getRequestURI();
        String nameVariable = path.substring(path.length() - 1);

        HttpSession session = req.getSession();
        session.removeAttribute(nameVariable);
        resp.setStatus(204);
    }

}
