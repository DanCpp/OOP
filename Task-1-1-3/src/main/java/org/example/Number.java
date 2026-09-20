package org.example;

public class Number extends Expression {
    private final int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Number objNumber = (Number) obj;
        return objNumber.number == number;
    }
}
