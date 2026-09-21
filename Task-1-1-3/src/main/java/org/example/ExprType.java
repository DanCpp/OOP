package org.example;

/**
 * Represents the fundamental types of nodes in a mathematical expression tree.
 * This enumeration can be used for type checking, pattern matching,
 * or parsing classifications within the expression framework.
 */
public enum ExprType {

    /**
     * Represents a variable node (e.g., "x", "y") that holds a symbolic name
     * and can be evaluated dynamically using assigned values.
     */
    Variable,

    /**
     * Represents a constant numeric node (e.g., "5", "-12") that holds
     * a fixed integer value.
     */
    Number,

    /**
     * Represents a compound node containing an operation and two subexpressions
     * as operands (e.g., addition, subtraction, multiplication, division).
     */
    BinaryOperation
}
