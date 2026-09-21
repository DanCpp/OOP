package org.example;

import java.util.HashMap;

public abstract class Expression {
    public abstract Expression derivative(String var);

    public abstract boolean equals(Object obj);

    public abstract String toString();

    public abstract Expression simplify();

    protected abstract int eval(HashMap<String, Integer> signifying);
    protected abstract int eval();

    public int eval(String signifyingString) {
        if (signifyingString.isEmpty()) {
            return eval();
        }
        String[] vars = signifyingString.split("; ");
        HashMap<String, Integer> signifying = new HashMap<String, Integer>();

        for (String variable : vars) {
            String[] name_number = variable.split(" = ");
            signifying.put(name_number[0], Integer.valueOf(name_number[1]));
        }

        return eval(signifying);
    }
}
