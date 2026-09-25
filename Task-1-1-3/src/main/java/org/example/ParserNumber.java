package org.example;

/**
 * An internal parser token representing a fixed numeric value.
 * This class extends {@link ParserExpression} and holds an immutable
 * integer parsed from the input expression string.
 */
public class ParserNumber extends ParserExpression {

    /**
     * The concrete integer value stored in this token.
     */
    protected final int number;

    /**
     * Constructs a new numeric parser token with the specified value.
     * Automatically sets the parent token type classification to {@link ExprType#Number}.
     *
     * @param number the integer value parsed from the mathematical expression
     */
    public ParserNumber(int number) {
        super(ExprType.Number);
        this.number = number;
    }
}
