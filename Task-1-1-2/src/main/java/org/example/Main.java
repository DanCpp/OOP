package org.example;


import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Engine gameEngine = new Engine();
        int rounds_limit = 3;
        gameEngine.game(rounds_limit, scanner);
    }
}