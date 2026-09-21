package org.example;

import java.util.HashMap;

public class Add extends Binary {
    public Add(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Expression derivative(String var) {
        return new Add(lhs.derivative(var), rhs.derivative(var));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Add add = (Add) obj;
        return (lhs.equals(add.lhs) && rhs.equals(add.rhs));
    }

    @Override
    protected int eval() {
        return lhs.eval() + rhs.eval();
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) + rhs.eval(signifying);
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + " + " + rhs.toString() + ")";
    }

    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();

        boolean containsVariables = false;
        int left = 0;
        int right = 0;
        try {
            left = lhs.eval();
            if (left == 0) {
                return rhs.simplify();
            }
        } catch (Exception ignored) {
            // ignored because eval can throw exception if lhs contains variable
            containsVariables = true;
        }

        try {
            right = rhs.eval();
            if (right == 0) {
                return lhs.simplify();
            }
        }  catch (Exception ignored) {
            // ignored because eval can throw exception if rhs contains variable
            containsVariables = true;
        }

        if (containsVariables) {
            return this;
        }

        return new Number(left + right);
    }
}
