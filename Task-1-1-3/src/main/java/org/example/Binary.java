package org.example;

/**
 * An abstract base class representing binary mathematical expressions.
 * A binary expression consists of two operands: a left-hand side (lhs)
 * and a right-hand side (rhs). This class serves as a foundation for concrete
 * operations such as addition, subtraction, multiplication, and division.
 */
public abstract class Binary extends Expression {

    /**
     * The left-hand side operand of the binary expression.
     */
    protected Expression lhs;

    /**
     * The right-hand side operand of the binary expression.
     */
    protected Expression rhs;
}

