package org.example;

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
}
