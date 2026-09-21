package org.example;

import java.util.HashMap;

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

    @Override
    protected int eval() {
        return number;
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return number;
    }

    @Override
    public String toString() {
        return Integer.toString(number);
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
