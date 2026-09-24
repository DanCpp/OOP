package org.example;

import java.util.HashMap;

/**
 * Represents the arithmetic multiplication operation of two expressions.
 */
public class Mul extends Binary {

    /**
     * Constructs a new multiplication expression.
     *
     * @param lhs the left-hand side expression (multiplier)
     * @param rhs the right-hand side expression (multiplicand)
     */
    public Mul(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Computes the derivative of the product with respect to a given variable.
     *
     * @param var the variable name with respect to which the differentiation is performed
     * @return a new expression representing the derivative of this multiplication
     */
    @Override
    public Expression derivative(String var) {
        // (uv)' = u'v + uv'
        Expression uiv = new Mul(lhs.derivative(var), rhs); // u'v
        Expression uvi = new Mul(lhs, rhs.derivative(var)); // uv'
        return new Add(uiv, uvi);
    }

    /**
     * Compares this multiplication expression with the specified object for equality.
     * Two multiplication expressions are considered equal if both their left-hand and
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

        Mul mul = (Mul) obj;
        return (lhs.equals(mul.lhs) && rhs.equals(mul.rhs));
    }

    /**
     * Evaluates and returns the numeric value of this expression when no variables are present.
     *
     * @return the result of multiplying the left-hand side and right-hand side expressions
     */
    @Override
    protected int eval() {
        return lhs.eval() * rhs.eval();
    }

    /**
     * Evaluates and returns the numeric value of this expression by substituting
     * variable values from the provided map.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values
     * @return the result of multiplying the evaluated subexpressions with variables substituted
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) * rhs.eval(signifying);
    }

    /**
     * Returns a string representation of this multiplication expression.
     * The result is formatted as a mathematical expression enclosed in parentheses.
     *
     * @return a string representation of the form "(left_expression * right_expression)"
     */
    @Override
    public String toString() {
        return "(" + lhs.toString() + " * " + rhs.toString() + ")";
    }

    /**
     * Performs algebraic simplification on this multiplication expression.
     * If the expression contains unresolved variables and cannot be fully evaluated,
     * the current multiplication object itself is returned with its operands simplified.
     *
     * @return a simplified mathematical expression
     */
    @Override
    public Expression simplify() {
        Expression left_side = lhs.simplify();
        Expression right_side = rhs.simplify();

        int left = 0;
        int right = 0;

        boolean containsVariable = false;
        try {
            left = left_side.eval();
            if (left == 0) {
                return new Number(0);
            } else if (left == 1) {
                return right_side;
            }
        } catch (Exception ignored) {
            containsVariable = true;
        }

        try {
            right = right_side.eval();
            if (right == 0) {
                return new Number(0);
            } else if (right == 1) {
                return left_side;
            }
        } catch (Exception ignored) {
            containsVariable = true;
        }

        if (containsVariable) {
            return new Mul(left_side, right_side);
        }

        return new Number(left * right);
    }
}
