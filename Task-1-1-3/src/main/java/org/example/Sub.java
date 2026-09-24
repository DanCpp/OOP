package org.example;

import java.util.HashMap;

/**
 * Represents the arithmetic subtraction operation of two expressions.
 * This is a binary operation that extends the base {@link Binary} class.
 */
public class Sub extends Binary {

    /**
     * Constructs a new subtraction expression.
     *
     * @param lhs the left-hand side expression (minuend)
     * @param rhs the right-hand side expression (subtrahend)
     */
    public Sub(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Computes the derivative of the difference with respect to a given variable.
     * According to calculus rules, the derivative of a difference is
     * the difference of its derivatives: (f - g)' = f' - g'.
     *
     * @param var the variable name with respect to which the differentiation is performed
     * @return a new expression representing the derivative of this subtraction
     */
    @Override
    public Expression derivative(String var) {
        return new Sub(lhs.derivative(var), rhs.derivative(var));
    }

    /**
     * Compares this subtraction expression with the specified object for equality.
     * Two subtraction expressions are considered equal if both their left-hand and
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

        Sub sub = (Sub) obj;
        return (lhs.equals(sub.lhs) && rhs.equals(sub.rhs));
    }

    /**
     * Evaluates and returns the numeric value of this expression when no variables are present.
     *
     * @return the result of subtracting the right-hand side from the left-hand side expression
     */
    @Override
    protected int eval() {
        return lhs.eval() - rhs.eval();
    }

    /**
     * Evaluates and returns the numeric value of this expression by substituting
     * variable values from the provided map.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values
     * @return the result of subtraction with variables substituted
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) - rhs.eval(signifying);
    }

    /**
     * Returns a string representation of this subtraction expression.
     * The result is formatted as a mathematical expression enclosed in parentheses.
     *
     * @return a string representation of the form "(left_expression - right_expression)"
     */
    @Override
    public String toString() {
        return "(" + lhs.toString() + " - " + rhs.toString() + ")";
    }

    /**
     * Performs algebraic simplification on this subtraction expression.
     * The simplification process includes:
     *   Simplifying both the left-hand and right-hand operands.
     *   Checking for identity structural equality (if {@code lhs} is structurally
     *       equal to {@code rhs}, it immediately returns a {@link Number} of 0,
     *       even if the expressions contain unresolved variables like {@code x - x}).
     *   Constant folding (if both operands evaluate to pure numbers without
     *       throwing exceptions, a new {@link Number} object containing their
     *       difference is returned).
     * If the expression contains unresolved variables and operands are not identical,
     * the current subtraction object itself is returned with its operands simplified.
     *
     * @return a simplified mathematical expression
     */
    @Override
    public Expression simplify() {
        Expression left_side = lhs.simplify();
        Expression right_side = rhs.simplify();
        if (left_side.equals(right_side)) {
            return new Number(0);
        }

        int left = 0;
        int right = 0;
        try {
            left = left_side.eval();
            right = right_side.eval();
        } catch (Exception ignored) {
            // ignored because eval can throw exception if lhs or rhs contains variable
            return new Sub(left_side, right_side);
        }

        return new Number(left - right);
    }
}
