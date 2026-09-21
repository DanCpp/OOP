package org.example;

public class Main {
    public static void main(String[] args) {
        Expression expr = Parser.parse("2 + 3 * 2 + x * 0 - 10");
        Expression simple = expr.simplify();

        System.out.println(simple);
    }
}