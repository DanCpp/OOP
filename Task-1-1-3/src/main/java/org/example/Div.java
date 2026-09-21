package org.example;

import java.util.HashMap;

public class Div extends Binary {
    public Div(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public Expression derivative(String var) {
        // (u / v)' = (u'v - uv') / (v * v)
        Expression uiv = new Mul(lhs.derivative(var), rhs); // u'v
        Expression uvi = new Mul(lhs, rhs.derivative(var)); // uv'
        Expression sub = new Sub(uiv, uvi);
        Expression vv = new Mul(rhs, rhs);

        return new Div(sub, vv);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Div div = (Div) obj;
        return (lhs.equals(div.lhs) && rhs.equals(div.rhs));
    }

    @Override
    protected int eval() {
        return lhs.eval() / rhs.eval();
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) / rhs.eval(signifying);
    }

    @Override
    public String toString() {
        return "(" + lhs.toString() + " / " + rhs.toString() + ")";
    }

    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();

        int left = 0;
        int right = 0;
        try {
            left = lhs.eval();
            right = rhs.eval();
        } catch (Exception ignored) {
            return this;
        }

        return new Number(left / right);
    }
}
