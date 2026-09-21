package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExpressionTest {

    @Test
    void evaluateSimpleExprAdd() {
        Expression expr = new Add(new Number(5), new Number(4));
        assertEquals(9, expr.eval());
    }

    @Test
    void evaluateSimpleExprSub() {
        Expression expr = new Sub(new Number(5), new Number(4));
        assertEquals(1, expr.eval());
    }

    @Test
    void evaluateSimpleExprMul() {
        Expression expr = new Mul(new Number(5), new Number(4));
        assertEquals(20, expr.eval());
    }

    @Test
    void evaluateSimpleExprDiv() {
        Expression expr = new Div(new Number(5), new Number(4));
        assertEquals(1, expr.eval());
    }

    @Test
    void evaluateWithEmptySignifying() {
        Expression expression = new Mul(new Number(2), new Number(3));
        assertEquals(6, expression.eval(""));
    }

    @Test
    void evaluateSimpleExprAddWithVariable() {
        Expression expr = new Add(new Number(20), new Variable("x"));
        assertEquals(31, expr.eval("x = 11"));
    }

    @Test
    void evaluateSimpleExprSubWithVariable() {
        Expression expr = new Sub(new Number(20), new Variable("x"));
        assertEquals(9, expr.eval("x = 11"));
    }

    @Test
    void evaluateSimpleExprMulWithVariable() {
        Expression expr = new Mul(new Number(20), new Variable("x"));
        assertEquals(200, expr.eval("x = 10"));
    }

    @Test
    void evaluateSimpleExprDivWithVariable() {
        Expression expr = new Div(new Number(20), new Variable("x"));
        assertEquals(2, expr.eval("x = 10"));
    }

    @Test
    void equalsReflexion() {
        final Expression add = new Add(new Number(2), new Number(3));
        assertEquals(add, add);

        final Expression sub = new Sub(new Number(2), new Number(3));
        assertEquals(sub, sub);

        final Expression mul = new Mul(new Number(2), new Number(3));
        assertEquals(mul, mul);

        final Expression div = new Div(new Number(2), new Number(3));
        assertEquals(div, div);

        final Expression variable = new Variable("x");
        assertEquals(variable, variable);

        final Expression number = new Number(5);
        assertEquals(number, number);
    }

    @Test
    void notEqualsWithNull() {
        final Expression add = new Add(new Number(2), new Number(3));
        assertFalse(add.equals(null));

        final Expression sub = new Sub(new Number(2), new Number(3));
        assertFalse(sub.equals(null));

        final Expression mul = new Mul(new Number(2), new Number(3));
        assertFalse(mul.equals(null));

        final Expression div = new Div(new Number(2), new Number(3));
        assertFalse(div.equals(null));

        final Expression variable = new Variable("x");
        assertFalse(variable.equals(null));

        final Expression number = new Number(5);
        assertFalse(number.equals(null));
    }

    @Test
    void notEqualsWithDifferentType() {
        Expression add = new Add(new Number(2), new Number(3));
        Expression sub = new Sub(new Number(2), new Number(3));
        Expression mul = new Mul(new Number(2), new Number(3));
        Expression div = new Div(new Number(2), new Number(3));
        Expression variable = new Variable("x");
        Expression number = new Number(5);

        assertFalse(add.equals(sub));
        assertFalse(sub.equals(add));
        assertFalse(mul.equals(div));
        assertFalse(div.equals(mul));
        assertFalse(variable.equals(number));
        assertFalse(number.equals(variable));
    }

    @Test
    void equalsWithSameAST() {
        Expression add = new Add(new Number(2), new Number(3));
        Expression sub = new Sub(new Number(2), new Number(3));
        Expression mul = new Mul(new Number(2), new Number(3));
        Expression div = new Div(new Number(2), new Number(3));
        Expression variable = new Variable("x");
        Expression number = new Number(5);

        Expression add1 = new Add(new Number(2), new Number(3));
        Expression sub1 = new Sub(new Number(2), new Number(3));
        Expression mul1 = new Mul(new Number(2), new Number(3));
        Expression div1 = new Div(new Number(2), new Number(3));
        Expression variable1 = new Variable("x");
        Expression number1 = new Number(5);

        assertEquals(add, add1);
        assertEquals(sub, sub1);
        assertEquals(mul, mul1);
        assertEquals(div, div1);
        assertEquals(variable, variable1);
        assertEquals(number, number1);
    }

    @Test
    void testSimpleDerivativeAdd() {
        Expression expr = new Add(new Number(5), new Variable("x"));
        Expression dexpr = expr.derivative("x");
        Expression expected = new Add(new Number(0), new Number(1));

        assertEquals(expected, dexpr);
        assertEquals(1, dexpr.eval());
    }

    @Test
    void testSimpleDerivativeSub() {
        Expression expr = new Sub(new Number(5), new Variable("x"));
        Expression dexpr = expr.derivative("x");
        Expression expected = new Sub(new Number(0), new Number(1));

        assertEquals(expected, dexpr);
        assertEquals(-1, dexpr.eval());
    }

    @Test
    void testSimpleDerivativeMul() {
        Expression expr = new Mul(new Number(5), new Variable("x"));
        Expression dexpr = expr.derivative("x");
        Expression expected = new Add(new Mul(new Number(0), new Variable("x")), new Mul(new Number(5), new Number(1)));

        assertEquals(expected, dexpr);
        assertEquals(5, dexpr.eval("x = 5"));
    }

    @Test
    void testSimpleDerivativeDiv() {
        Expression expr = new Div(new Number(5), new Variable("x"));
        Expression dexpr = expr.derivative("x");
        Expression expected = new Div(new Sub(
                new Mul(new Number(0), new Variable("x")), new Mul(new Number(5), new Number(1))),
                new Mul(new Variable("x"), new Variable("x")));

        assertEquals(expected, dexpr);
        assertEquals(-5, dexpr.eval("x = 1"));
    }

    @Test
    void testSimplifyMulWithZeroLeft() {
        Expression mul = new Mul(new Number(0), new Variable("x"));
        Expression simpleMul = mul.simplify();

        assertEquals(0, simpleMul.eval());
    }

    @Test
    void testSimplifyMulWithZeroRight() {
        Expression mul = new Mul(new Variable("x"), new Number(0));
        Expression simpleMul = mul.simplify();

        assertEquals(0, simpleMul.eval());
    }

    @Test
    void testSimplifyMulWithOneLeft() {
        Expression mul = new Mul(new Number(1), new Number(52));
        Expression simpleMul = mul.simplify();

        assertEquals("52", simpleMul.toString());
    }

    @Test
    void testSimplifyMulWithOneRight() {
        Expression mul = new Mul(new Number(52), new Number(1));
        Expression simpleMul = mul.simplify();

        assertEquals("52", simpleMul.toString());
    }

    @Test
    void testSimplifySubWithEqualsExpressions() {
        Expression sub = new Sub(new Mul(new Number(2), new Number(2)), new Mul(new Number(2), new Number(2)));
        Expression simpleSub = sub.simplify();

        assertEquals("0", simpleSub.toString());
    }

    @Test
    void testSimplifySubWithEqualsEvaluatedExpressions() {
        Expression sub = new Sub(new Mul(new Number(2), new Number(2)), new Add(new Number(2), new Number(2)));
        Expression simpleSub = sub.simplify();

        assertEquals("0", simpleSub.toString());
    }

    @Test
    void testSimplifyAddWithZeroLeft() {
        Expression add = new Add(new Number(0), new Variable("x"));
        Expression simpleAdd = add.simplify();

        assertEquals("x", simpleAdd.toString());
    }

    @Test
    void testSimplifyAddWithZeroRight() {
        Expression add = new Add(new Variable("y"), new Number(0));
        Expression simpleAdd = add.simplify();

        assertEquals("y", simpleAdd.toString());
    }
}