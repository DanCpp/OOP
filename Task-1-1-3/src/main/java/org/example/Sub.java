package org.example;

import java.util.HashMap;

public class Sub extends Binary {
    public Sub(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(lhs.derivative(var), rhs.derivative(var));
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Sub sub = (Sub) obj;
        return (lhs.equals(sub.lhs) && rhs.equals(sub.rhs));
    }

    @Override
    protected int eval() {
        return lhs.eval() - rhs.eval();
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) - rhs.eval(signifying);
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + " - " + rhs.toString() + ")";
    }

    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();
        if (lhs.equals(rhs)) {
            return new Number(0);
        }

        int left = 0;
        int right = 0;
        try {
            left = lhs.eval();
            right = rhs.eval();
        } catch (Exception ignored) {
            // ignored because eval can throw exception if lhs or rhs contains variable
            return this;
        }

        return new Number(left - right);
    }
}
