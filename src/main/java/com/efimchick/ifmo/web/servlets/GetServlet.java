package com.efimchick.ifmo.web.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/calc/result")
public class GetServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Enumeration<String> enumeration = session.getAttributeNames();
        Map<String, Integer> variables = createVariables(enumeration, session);
        String exp = (String) session.getAttribute("exp");

        ExpressionParser expressionParser = new ExpressionParser();
        try {
            int result = expressionParser.evaluateExpression(exp, variables);
            resp.getWriter().write(String.valueOf(result));
        } catch (RuntimeException e) {
            resp.setStatus(409);
        }
    }

    public Map<String, Integer> createVariables(Enumeration<String> enumeration, HttpSession session) {
        Map<String, Integer> variables = new HashMap<>();
        while (enumeration.asIterator().hasNext()) {
            String name = enumeration.asIterator().next();
            if (name.equals("exp")) {
                continue;
            }
            int value;
            try {
                value = Integer.parseInt((String) session.getAttribute(name));
            } catch (RuntimeException e) {
                String localName = (String) session.getAttribute(name);
                value = Integer.parseInt((String) session.getAttribute(localName));
            }
            variables.put(name, value);
        }
        return variables;
    }
}
