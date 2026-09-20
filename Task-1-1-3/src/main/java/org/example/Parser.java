package org.example;

import java.util.Collections;
import java.util.Stack;

public class Parser {

    private static void addOperation(Stack<ParserOperation> operations, Stack<ParserExpression> expressions, ParserOperation operation) {
        if (operations.empty()) {
            operations.push(operation);
            return;
        }

        do {
            ParserOperation op = operations.peek();
            if (operation.priority > op.priority || op.operand == '(') {
                break;
            }
            expressions.push(operations.pop());
        } while (!operations.empty());
        operations.push(operation);
    }

    private static void addRightParent(Stack<ParserOperation> operations, Stack<ParserExpression> expressions) {
        ParserOperation op = operations.pop();
        while (op.operand != '(') {
            expressions.push(op);
            op = operations.pop();
        }
    }

    private static Expression processOperation(ParserOperation op, Expression lhs, Expression rhs) {
        return switch (op.operand) {
            case '+' -> new Add(lhs, rhs);
            case '-' -> new Sub(lhs, rhs);
            case '*' -> new Mul(lhs, rhs);
            case '/' -> new Div(lhs, rhs);
            default -> throw new IllegalStateException("Unknown operation");
        };
    }

    private static Expression evalAST(Stack<ParserExpression> expressions) {
        Stack<ParserExpression> reversed = new Stack<>();
        while (!expressions.empty()) {
            reversed.push(expressions.pop());
        }

        Stack<Expression> astExpressions = new Stack<>();
        while (!reversed.empty()) {
            ParserExpression expr = reversed.pop();
            switch (expr.type) {
                case ExprType.Number:
                    ParserNumber number = (ParserNumber) expr;
                    astExpressions.push(new Number(number.number));
                    break;
                case ExprType.Variable:
                    ParserVariable variable = (ParserVariable) expr;
                    astExpressions.push(new Variable(variable.varName));
                    break;
                case ExprType.BinaryOperation:
                    ParserOperation op = (ParserOperation) expr;
                    Expression rhs = astExpressions.pop();
                    Expression lhs = astExpressions.pop();

                    astExpressions.push(processOperation(op, lhs, rhs));
                    break;
            }
        }

        return astExpressions.pop();
    }

    public static Expression parse(String expression) {
        char[] input = expression.toCharArray();
        Stack<ParserExpression> expressions = new Stack<>();
        Stack<ParserOperation> operations = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            switch(input[i]) {
                case ' ':
                    break;
                case '(':
                    operations.push(new ParserOperation('(', 1));
                    break;
                case ')':
                    addRightParent(operations, expressions);
                    break;
                case '+':
                    addOperation(operations, expressions, new ParserOperation('+', 2));
                    break;
                case '-':
                    addOperation(operations, expressions, new ParserOperation('-', 2));
                    break;
                case '*':
                    addOperation(operations, expressions, new ParserOperation('*', 3));
                    break;
                case '/':
                    addOperation(operations, expressions, new ParserOperation('/', 3));
                    break;
                default:
                {
                    if (Character.isDigit(input[i])) { // Number
                        StringBuilder numberString = new StringBuilder();
                        while (i < expression.length() && Character.isDigit(input[i])) {
                            numberString.append(input[i]);
                            i++;
                        }
                        if (i < expression.length()) {
                            i--;
                        }
                        int number = Integer.parseInt(numberString.toString());
                        expressions.push(new ParserNumber(number));
                    } else { // Variable
                        StringBuilder variable = new StringBuilder();
                        while (i < expression.length() && Character.isAlphabetic(input[i])) {
                            variable.append(input[i]);
                            i++;
                        }
                        if (i < expression.length()) {
                            i--;
                        }
                        expressions.push(new ParserVariable(variable.toString()));
                    }
                }
            }
        }


        while (!operations.empty()) {
            expressions.push(operations.pop());
        }

        return evalAST(expressions);
    }
}
