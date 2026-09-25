package org.example;

/**
 * A base wrapper class used internally by the {@link Parser} to represent a tokenized component
 * of a mathematical expression.
 * This class serves as a common superclass for specific token types such as numbers, variables,
 * and operations before they are evaluated into the final Abstract Syntax Tree (AST).
 */
public class ParserExpression {

    /**
     * The structural type classification of this parser token.
     */
    protected final ExprType type;

    /**
     * Constructs a new parser expression token with the specified type classification.
     *
     * @param type the structural type of the expression component
     */
    public ParserExpression(ExprType type) {
        this.type = type;
    }
}
