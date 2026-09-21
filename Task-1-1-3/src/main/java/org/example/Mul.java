package org.example;

import java.util.HashMap;

public class Mul extends Binary {
    public Mul(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Expression derivative(String var) {
        // (uv)' = u'v + uv'
        Expression uiv = new Mul(lhs.derivative(var), rhs); // u'v
        Expression uvi = new Mul(lhs, rhs.derivative(var)); // uv'
        return new Add(uiv, uvi);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Mul mul = (Mul) obj;
        return (lhs.equals(mul.lhs) && rhs.equals(mul.rhs));
    }

    @Override
    protected int eval() {
        return lhs.eval() * rhs.eval();
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) * rhs.eval(signifying);
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + " * " + rhs.toString() + ")";
    }

    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();

        int left = 0;
        int right = 0;

        boolean containsVariable = false;
        try {
            left = lhs.eval();
            if (left == 0) {
                return new Number(0);
            } else if (left == 1) {
                return rhs.simplify();
            }
        } catch(Exception ignored) {
            containsVariable = true;
        }

        try {
            right = rhs.eval();
            if (right == 0) {
                return new Number(0);
            } else if (right == 1) {
                return lhs.simplify();
            }
        } catch(Exception ignored) {
            containsVariable = true;
        }

        if (containsVariable) {
            return this;
        }

        return new Number(left * right);
    }
}
