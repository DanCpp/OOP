package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserTest {

    @Test
    void simpleParse() {
        Expression parsed = Parser.parse("2+3");
        Expression expected = new Add(new Number(2), new Number(3));

        assertEquals(parsed, expected);
    }

    @Test
    void complicatedParse() {
        Expression actual = Parser.parse("2 + 3 * (10 / (2 + 2))");
        Expression expected = new Add(new Number(2),
                new Mul(new Number(3),
                        new Div(new Number(10),
                                new Add(new Number(2), new Number(2)))));

        assertEquals(expected, actual);
    }

    @Test
    void parseSimpleAddAndThenToString() {
        String toParse = "(2 + 3)";
        Expression add = Parser.parse(toParse);
        assertEquals(toParse, add.toString());
    }

    @Test
    void parseSimpleSubAndThenToString() {
        String toParse = "(2 - 3)";
        Expression sub = Parser.parse(toParse);
        assertEquals(toParse, sub.toString());
    }

    @Test
    void parseSimpleMulAndThenToString() {
        String toParse = "(2 * 3)";
        Expression mul = Parser.parse(toParse);
        assertEquals(toParse, mul.toString());
    }

    @Test
    void parseSimpleDivAndThenToString() {
        String toParse = "(2 / 3)";
        Expression div = Parser.parse(toParse);
        assertEquals(toParse, div.toString());
    }

    @Test
    void parseSimpleVarAndThenToString() {
        String toParse = "x";
        Expression variable = Parser.parse(toParse);
        assertEquals(toParse, variable.toString());
    }

    @Test
    void parseSimpleNumAndThenToString() {
        String toParse = "100";
        Expression num = Parser.parse(toParse);
        assertEquals(toParse, num.toString());
    }

    @Test
    void testBasicNumbersAndVariables() {
        Expression num = Parser.parse("42");
        assertNotNull(num);

        Expression var = Parser.parse("xyz");
        assertNotNull(var);

        Expression spaceResult = Parser.parse("  123   ");
        assertNotNull(spaceResult);
    }

    @Test
    void testAllBinaryOperations() {
        assertNotNull(Parser.parse("1 + 2"));
        assertNotNull(Parser.parse("3 - 4"));
        assertNotNull(Parser.parse("5 * 6"));
        assertNotNull(Parser.parse("7 / 8"));
    }

    @Test
    void testOperatorPriorities() {
        assertNotNull(Parser.parse("1 + 2 * 3"));

        assertNotNull(Parser.parse("1 * 2 + 3"));

        assertNotNull(Parser.parse("1 - 2 + 3"));
    }

    @Test
    void testParentheses() {
        assertNotNull(Parser.parse("(1 + 2) * 3"));

        assertNotNull(Parser.parse("((a))"));
        assertNotNull(Parser.parse("a * (b - (c + d))"));
    }

    @Test
    void testCharactersAtTheEndOfTheString() {
        assertNotNull(Parser.parse("10+20"));
        assertNotNull(Parser.parse("a+b"));

        assertNotNull(Parser.parse("1 + 25"));
        assertNotNull(Parser.parse("1 + abc"));
    }

}