package com.efimchick.ifmo.web.servlets;

import java.util.Map;
import java.util.Stack;

public class ExpressionParser {
    public int evaluateExpression(String expression, Map<String, Integer> variables) {
        for (Map.Entry<String, Integer> entry : variables.entrySet())
            expression = expression.replace(entry.getKey(), entry.getValue().toString());
        return evaluateMathExpression(expression);
    }

    private int evaluateMathExpression(String expression) {
        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        int i = 0;
        while (i < expression.length()) {
            char ch = expression.charAt(i);
            char prvCh = ' ';

            if(i != 0)
                prvCh = expression.charAt(i - 1);
            if (ch == '-' && (i == 0 ||
                    (!Character.isDigit(prvCh) && prvCh != ' '))) {
                StringBuilder sb = new StringBuilder();
                sb.append('-');
                i++;

                while (i < expression.length() && Character.isDigit(expression.charAt(i)))
                    sb.append(expression.charAt(i++));

                values.push(Integer.parseInt(sb.toString()));
            } else if (Character.isDigit(ch)) {
                StringBuilder sb = new StringBuilder();
                while (i < expression.length() && Character.isDigit(expression.charAt(i)))
                    sb.append(expression.charAt(i++));

                values.push(Integer.parseInt(sb.toString()));
            } else if (ch == '(') {
                operators.push(ch);
                i++;
            } else if (ch == ')') {
                while (operators.peek() != '(')
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));

                operators.pop();
                i++;
            } else if (isOperator(ch)) {
                while (!operators.isEmpty() && hasPrecedence(ch, operators.peek()))
                    values.push(applyOperator(operators.pop(), values.pop(), values.pop()));

                operators.push(ch);
                i++;
            } else
                i++;

        }

        while (!operators.isEmpty())
            values.push(applyOperator(operators.pop(), values.pop(), values.pop()));

        return values.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;

        return true;
    }

    private int applyOperator(char operator, int b, int a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new ArithmeticException();
                return a / b;
        }
        return 0;
    }
}
