package org.example;

public class ParserNumber extends ParserExpression {
    protected final int number;

    public ParserNumber(int number) {
        super(ExprType.Number);
        this.number = number;
    }
}
