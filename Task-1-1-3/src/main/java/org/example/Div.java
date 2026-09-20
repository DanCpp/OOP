package org.example;

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
}
