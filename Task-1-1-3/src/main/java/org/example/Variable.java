package org.example;

import java.util.HashMap;

/**
 * Represents a symbolic variable node (e.g., "x", "y") within a mathematical expression tree.
 * This node resolves its integer value dynamically at runtime using a provided look-up map.
 */
public class Variable extends Expression {

    /**
     * The unique symbolic string identifier of this variable.
     */
    private final String varName;

    /**
     * Constructs a new variable expression node with the specified name.
     *
     * @param varName the symbolic name of the variable.
     */
    public Variable(String varName) {
        this.varName = varName;
    }

    /**
     * Computes the partial derivative of this variable with respect to a target variable.
     *
     * @param var the variable name with respect to which the differentiation is performed.
     * @return a {@link Number}.
     */
    @Override
    public Expression derivative(String var) {
        if (var.equals(varName)) {
            return new Number(1);
        }
        return new Number(0);
    }

    /**
     * Compares this variable expression with the specified object for equality.
     *
     * @param obj the object to be compared for equality with this expression.
     * @return {@code true} if the specified object is equal to this variable;
     *         {@code false} otherwise.
     */
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

    /**
     * Attempts to evaluate the numeric value of this variable without providing external context.
     * Since variables require explicit assignments to yield numeric values, this call is invalid.
     *
     * @return none (always throws an exception).
     */
    @Override
    protected int eval() {
        throw new IllegalStateException("Cannot evaluate variable without signifying");
    }

    /**
     * Evaluates the numeric value assigned to this variable by looking it up in the map.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values.
     * @return the integer value associated with this variable's name.
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return signifying.get(varName);
    }

    /**
     * Returns a string representation of this variable expression.
     *
     * @return the raw symbolic name of the variable.
     */
    @Override
    public String toString() {
        return varName;
    }

    /**
     * Performs algebraic simplification on this variable expression.
     * Since a symbolic variable cannot be simplified any further on its own,
     * this method simply returns the current instance.
     *
     * @return the current {@code Variable} instance unchanged.
     */
    @Override
    public Expression simplify() {
        return this;
    }
}
