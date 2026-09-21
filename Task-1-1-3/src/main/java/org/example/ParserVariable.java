package org.example;

/**
 * An internal parser token representing a named variable.
 * This class extends {@link ParserExpression} and stores the symbolic name
 * of a variable parsed from the input expression string.
 */
public class ParserVariable extends ParserExpression {

    /**
     * The symbolic name of the variable (e.g., "x", "kek").
     */
    protected final String varName;

    /**
     * Constructs a new variable parser token with the specified name.
     * Automatically sets the parent token type classification to {@link ExprType#Variable}.
     *
     * @param varName the string name of the parsed variable
     */
    public ParserVariable(String varName) {
        super(ExprType.Variable);
        this.varName = varName;
    }
}
