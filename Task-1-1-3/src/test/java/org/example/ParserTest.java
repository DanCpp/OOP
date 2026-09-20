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
}