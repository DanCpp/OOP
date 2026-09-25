package org.example;

/**
 * An internal parser token representing an arithmetic operation or a parenthesis delimiter.
 * This class extends {@link ParserExpression} and holds metadata required by the
 * Shunting-yard algorithm, such as operator precedence and the specific operator character.
 */
public class ParserOperation extends ParserExpression {

    /**
     * The character symbol representing the operator or delimiter.
     */
    protected final char operand;

    /**
     * The precedence level of the operator.
     * Higher values indicate higher priority in the order of operations.
     */
    protected final int priority;

    /**
     * Constructs a new operation parser token with the specified character and precedence priority.
     * Automatically sets the parent token type classification to {@link ExprType#BinaryOperation}.
     *
     * @param operand  the character symbol of the operation
     * @param priority the precedence rank of the operation
     */
    public ParserOperation(char operand, int priority) {
        super(ExprType.BinaryOperation);
        this.operand = operand;
        this.priority = priority;
    }
}
