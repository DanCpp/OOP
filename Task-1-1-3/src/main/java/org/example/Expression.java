package org.example;

import java.util.HashMap;

/**
 * The abstract base class representing a mathematical expression tree node.
 * Provides the core interface for evaluating, differentiating, simplifying,
 * and stringifying mathematical expressions.
 */
public abstract class Expression {

    /**
     * Computes the mathematical derivative of this expression with respect to the specified variable.
     *
     * @param var the name of the variable with respect to which the differentiation is performed
     * @return a new {@code Expression} representing the derivative
     */
    public abstract Expression derivative(String var);

    /**
     * Compares the specified object with this expression for equality.
     * Subclasses should implement this to ensure structural equality of the expression trees.
     *
     * @param obj the object to be compared for equality with this expression
     * @return {@code true} if the specified object is structurally equal to this expression;
     *         {@code false} otherwise
     */
    public abstract boolean equals(Object obj);

    /**
     * Returns a string representation of this mathematical expression.
     *
     * @return the string formatted version of the expression
     */
    public abstract String toString();

    /**
     * Performs algebraic simplification on this expression tree.
     *
     * @return a new, simplified {@code Expression}, or the same expression if no
     *         simplifications could be applied
     */
    public abstract Expression simplify();

    /**
     * Evaluates the expression by substituting variable values from the provided map.
     * This method must be implemented by subclasses to handle variables and operations.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values
     * @return the integer result of the evaluation
     */
    protected abstract int eval(HashMap<String, Integer> signifying);

    /**
     * Evaluates the expression assuming it contains no variables.
     * This method must be implemented by subclasses to calculate constant values.
     *
     * @return the integer result of the evaluation
     * @throws RuntimeException if the expression contains unresolved variables
     */
    protected abstract int eval();

    /**
     * Evaluates the expression using a string that specifies variable assignments.
     *
     * @param signifyingString a string containing variable definitions, e.g., {@code "x = 2; y = 3"}
     * @return the integer result of the evaluation after parsing and applying the variables
     * @throws ArrayIndexOutOfBoundsException if the input string format is invalid
     * @throws NumberFormatException if a variable value cannot be parsed into an integer
     */
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
