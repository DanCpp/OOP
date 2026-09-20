package org.example;

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
}
