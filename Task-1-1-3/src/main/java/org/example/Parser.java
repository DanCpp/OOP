package org.example;

import java.util.Collections;
import java.util.Stack;

/**
 * A utility class responsible for parsing mathematical expression strings into an executable expression tree.
 * The parser uses a variant of the Shunting-yard algorithm to handle operator precedence
 * (multiplication and division over addition and subtraction) and nested parentheses,
 * converting the infix notation into a structured Abstract Syntax Tree (AST).
 */
public class Parser {

    /**
     * Pushes an operation onto the operations stack according to its precedence.
     * If the current operation has a lower or equal precedence compared to the operation
     * at the top of the stack, the stack operations are popped and shifted to the expression
     * stack until a higher precedence operator or an open parenthesis is encountered.
     *
     * @param operations  the stack holding operators currently being processed
     * @param expressions the stack holding tokens and partial subexpressions (RPN sequence)
     * @param operation   the new operator being registered
     */
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

    /**
     * Resolves operations enclosed within parentheses when a closing parenthesis is encountered.
     * Pops operators from the operations stack and pushes them to the expressions stack
     * until the matching opening parenthesis {@code '('} is found and discarded.
     *
     * @param operations  the stack holding processed operators
     * @param expressions the stack holding the final processed subexpressions
     */
    private static void addRightParent(Stack<ParserOperation> operations, Stack<ParserExpression> expressions) {
        ParserOperation op = operations.pop();
        while (op.operand != '(') {
            expressions.push(op);
            op = operations.pop();
        }
    }

    /**
     * Maps an internal parser operation token to its corresponding concrete {@link Expression} subclass.
     *
     * @param op  the parser operation token containing the character operand
     * @param lhs the left-hand side expression operand
     * @param rhs the right-hand side expression operand
     * @return a concrete instantiation of a binary math operation
     * @throws IllegalStateException if an unsupported operator character is provided
     */
    private static Expression processOperation(ParserOperation op, Expression lhs, Expression rhs) {
        return switch (op.operand) {
            case '+' -> new Add(lhs, rhs);
            case '-' -> new Sub(lhs, rhs);
            case '*' -> new Mul(lhs, rhs);
            case '/' -> new Div(lhs, rhs);
            default -> throw new IllegalStateException("Unknown operation");
        };
    }

    /**
     * Evaluates a tokenized Reverse Polish Notation (RPN) stack into a nested object-oriented AST.
     *
     * @param expressions the stack containing RPN-ordered tokens
     * @return the root {@link Expression} node of the parsed math expression tree
     */
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

    /**
     * Parses a string representation of a mathematical expression into an {@link Expression} tree.
     * Supports basic arithmetic operators ({@code +}, {@code -}, {@code *}, {@code /}),
     * numeric constants, multi-character alphabetic variables, and parentheses for operation grouping.
     * Spaces within the string are automatically ignored.
     *
     * @param expression the raw mathematical expression string to parse
     * @return the root node of the constructed expression tree
     * @throws java.util.EmptyStackException if the expression string has unbalanced parentheses or invalid syntax
     * @throws NumberFormatException         if a numerical token exceeds integer bounds
     */
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
