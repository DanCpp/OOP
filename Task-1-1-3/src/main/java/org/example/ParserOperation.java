package org.example;

public class ParserOperation extends ParserExpression {
    protected final char operand;
    protected final int priority;

    public ParserOperation(char operand, int priority) {
        super(ExprType.BinaryOperation);
        this.operand = operand;
        this.priority = priority;
    }
}
