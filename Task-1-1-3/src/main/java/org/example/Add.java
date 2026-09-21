package org.example;

import java.util.HashMap;

/**
 * Represents the arithmetic addition operation of two expressions.
 */
public class Add extends Binary {

    /**
     * Constructs a new addition expression.
     *
     * @param lhs the left-hand side expression (summand)
     * @param rhs the right-hand side expression (summand)
     */
    public Add(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Computes the derivative of the sum with respect to a given variable.
     * According to the sum rule in calculus, the derivative of a sum is
     * the sum of its derivatives: (f + g)' = f' + g'.
     *
     * @param var the variable name with respect to which the differentiation is performed
     * @return a new expression representing the derivative of this addition
     */
    @Override
    public Expression derivative(String var) {
        return new Add(lhs.derivative(var), rhs.derivative(var));
    }

    /**
     * Compares this addition expression with the specified object for equality.
     * Two addition expressions are considered equal if both their left-hand and
     * right-hand components are respectively equal.
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

        Add add = (Add) obj;
        return (lhs.equals(add.lhs) && rhs.equals(add.rhs));
    }

    /**
     * Evaluates and returns the numeric value of this expression when no variables are present.
     *
     * @return the result of adding the left-hand side and right-hand side expressions
     */
    @Override
    protected int eval() {
        return lhs.eval() + rhs.eval();
    }

    /**
     * Evaluates and returns the numeric value of this expression by substituting
     * variable values from the provided map.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values
     * @return the result of adding the evaluated subexpressions with variables substituted
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) + rhs.eval(signifying);
    }

    /**
     * Returns a string representation of this addition expression.
     * The result is formatted as a mathematical expression enclosed in parentheses.
     *
     * @return a string representation of the form "(left_expression + right_expression)"
     */
    @Override
    public String toString() {
        return "(" + lhs.toString() + " + " + rhs.toString() + ")";
    }

    /**
     * Performs algebraic simplification on this addition expression.
     * If the expression contains unresolved variables and cannot be fully evaluated,
     * the current addition object itself is returned.
     *
     * @return a simplified mathematical expression
     */
    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();

        boolean containsVariables = false;
        int left = 0;
        int right = 0;
        try {
            left = lhs.eval();
            if (left == 0) {
                return rhs.simplify();
            }
        } catch (Exception ignored) {
            // Ignored because eval() can throw an exception if lhs contains a variable
            containsVariables = true;
        }

        try {
            right = rhs.eval();
            if (right == 0) {
                return lhs.simplify();
            }
        }  catch (Exception ignored) {
            // Ignored because eval() can throw an exception if rhs contains a variable
            containsVariables = true;
        }

        if (containsVariables) {
            return this;
        }

        return new Number(left + right);
    }
}
