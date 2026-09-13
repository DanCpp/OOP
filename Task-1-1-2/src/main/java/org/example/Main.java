package org.example;


import java.util.Scanner;

/**
 * Main class of program.
 */
public class Main {
    /**
     * Simple main method to start game.
     * @param args - args from Player (not used now)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Engine gameEngine = new Engine();
        int roundsLimit = 3;
        gameEngine.game(roundsLimit, scanner);
    }
}