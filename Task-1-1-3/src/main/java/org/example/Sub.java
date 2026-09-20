package org.example;

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
}
