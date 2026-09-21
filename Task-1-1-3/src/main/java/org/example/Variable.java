package org.example;

import java.util.HashMap;

public class Variable extends Expression {
    private final String varName;

    public Variable(String varName) {
        this.varName = varName;
    }

    @Override
    public Expression derivative(String var) {
        if (var.equals(varName)) {
            return new Number(1);
        }
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

        Variable var = (Variable) obj;
        return varName.equals(var.varName);
    }

    @Override
    protected int eval() {
        throw new IllegalStateException("Cannot evaluate variable without signifying");
    }

    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return signifying.get(varName);
    }

    @Override
    public String toString() {
        return varName;
    }

    @Override
    public Expression simplify() {
        return this;
    }
}
