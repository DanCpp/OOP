package org.example;

public class Main {
    public static void main(String[] args) {
        Expression expr = Parser.parse("1 * kek - kek");
        Expression simple = expr.simplify();

        System.out.println(simple);
    }
}