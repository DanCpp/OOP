package org.example;

import java.util.HashMap;

/**
 * Represents the arithmetic division operation of two expressions.
 */
public class Div extends Binary {

    /**
     * Constructs a new division expression.
     *
     * @param lhs the left-hand side expression (numerator / dividend)
     * @param rhs the right-hand side expression (denominator / divisor)
     */
    public Div(Expression lhs, Expression rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    /**
     * Computes the derivative of the quotient with respect to a given variable.
     *
     * @param var the variable name with respect to which the differentiation is performed
     * @return a new expression representing the derivative of this division
     */
    @Override
    public Expression derivative(String var) {
        // (u / v)' = (u'v - uv') / (v * v)
        Expression uiv = new Mul(lhs.derivative(var), rhs); // u'v
        Expression uvi = new Mul(lhs, rhs.derivative(var)); // uv'
        Expression sub = new Sub(uiv, uvi);
        Expression vv = new Mul(rhs, rhs);

        return new Div(sub, vv);
    }

    /**
     * Compares this division expression with the specified object for equality.
     * Two division expressions are considered equal if both their left-hand and
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

        Div div = (Div) obj;
        return (lhs.equals(div.lhs) && rhs.equals(div.rhs));
    }

    /**
     * Evaluates and returns the numeric value of this expression when no variables are present.
     *
     * @return the integer result of dividing the left-hand side by the right-hand side expression
     * @throws ArithmeticException if the denominator evaluates to zero
     */
    @Override
    protected int eval() {
        return lhs.eval() / rhs.eval();
    }

    /**
     * Evaluates and returns the numeric value of this expression by substituting
     * variable values from the provided map.
     *
     * @param signifying a map containing variable names as keys and their corresponding
     *                   integer values as values
     * @return the integer result of division with variables substituted
     * @throws ArithmeticException if the evaluated denominator turns out to be zero
     */
    @Override
    protected int eval(HashMap<String, Integer> signifying) {
        return lhs.eval(signifying) / rhs.eval(signifying);
    }

    /**
     * Returns a string representation of this division expression.
     * The result is formatted as a mathematical expression enclosed in parentheses.
     *
     * @return a string representation of the form "(left_expression / right_expression)"
     */
    @Override
    public String toString() {
        return "(" + lhs.toString() + " / " + rhs.toString() + ")";
    }

    /**
     * Performs algebraic simplification on this division expression.
     * If either operand contains unresolved variables and cannot be fully evaluated,
     * the current division object itself is returned with its operands simplified.
     *
     * @return a simplified mathematical expression
     */
    @Override
    public Expression simplify() {
        lhs = lhs.simplify();
        rhs = rhs.simplify();

        int left = 0;
        int right = 0;
        try {
            left = lhs.eval();
            right = rhs.eval();
        } catch (Exception ignored) {
            return this;
        }

        return new Number(left / right);
    }
}
