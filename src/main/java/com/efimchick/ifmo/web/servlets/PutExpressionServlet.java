package com.efimchick.ifmo.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/calc/expression")
public class PutExpressionServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String exp  = req.getReader().readLine();

        if (session.getAttribute("exp") == null)
            resp.setStatus(201);
        else
            resp.setStatus(200);

        session.setAttribute("exp", exp);
    }

}
