package org.example;

public abstract class Expression {
    public abstract Expression derivative(String var);

    public abstract boolean equals(Object obj);
}
