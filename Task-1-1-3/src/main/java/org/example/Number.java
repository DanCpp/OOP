package org.example;

import java.util.HashMap;

/**
 * Represents a constant numeric node in a mathematical expression tree.
 * This class holds a fixed integer value and handles its evaluation,
 * differentiation, and serialization.
 */
public class Number extends Expression {

    /**
     * The immutable integer value represented by this node.
     */
    private final int number;

    /**
     * Constructs a new numeric expression node with the specified value.
     *
     * @param number the integer value of this constant
     */
    public Number(int number) {
        this.number = number;
    }

    /**
     * Computes the derivative of this constant value with respect to a given variable.
     * According to calculus rules, the derivative of a constant is always zero.
     *
     * @param var the variable name with respect to which the differentiation is performed
     * @return a new {@code Number} expression representing the constant value 0
     */
    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    /**
     * Compares this numeric expression with the specified object for equality.
     * Two {@code Number} objects are considered equal if they hold the exact same
     * integer value.
     *
     * @param obj the object to be compared for equality with this expression
     * @return {@code true} if the specified object is equal to this expression;
     *         {@code false} otherwise
     */
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

    /**
     * Evaluates and returns the numeric value of this constant expression.
     *
     * @return the integer value stored in this node
     */
    @Override
    protected int eval() {
        return number;
    }

    /**
     * Evaluates and returns the numeric value of this constant expression.
     * Since this is a constant, the provided map of variable values is ignored.
     *
     * @param signifying a map containing variable names and their corresponding integer values
     * @return the integer value stored in this node
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return number;
    }

    /**
     * Returns a string representation of this numeric expression.
     *
     * @return the string formatted version of the stored integer
     */
    @Override
    public String toString() {
        if (number < 0) return "(" + Integer.toString(number) + ")";
        return Integer.toString(number);
    }

    /**
     * Performs algebraic simplification on this numeric expression.
     * Since a constant number is already in its simplest possible form,
     * this method simply returns the current instance.
     *
     * @return the current {@code Number} instance unchanged
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
