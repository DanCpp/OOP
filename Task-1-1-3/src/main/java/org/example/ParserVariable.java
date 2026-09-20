package org.example;

public class ParserVariable extends ParserExpression {
    protected final String varName;

    public ParserVariable(String varName) {
        super(ExprType.Variable);
        this.varName = varName;
    }
}
