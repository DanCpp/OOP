package org.example;

import java.util.Scanner;

/**
 * The main class for this project.
 */
public class Engine {
    private final Player player;
    private final Dealer dealer;
    private final Deck deck;
    private int playerScore;
    private int dealerScore;


    /**
     * Usual constructor (default).
     */
    public Engine() {
        this (new Player(), new Dealer(), new Deck());
    }

    /**
     * Constructor with concrete values of player, dealer and deck. Makes more sense in testing.
     * @param player - concrete player.
     * @param dealer - concrete dealer.
     * @param deck - concrete deck.
     */
    public Engine(Player player, Dealer dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.playerScore = 0;
        this.dealerScore = 0;
    }

    /**
     * Helps to set up first cards in hands for player and dealer.
     * it makes like that:
     * Player gets first card.
     * Dealer gets second card.
     * Player gets third card.
     * Dealer gets fourth card.
     */
    private void setupCards() {
        for (int i = 0; i < 2; i++) {
            player.takeCardFromDeck(deck);
            dealer.takeCardFromDeck(deck);
        }
    }

    /**
     * Helps to show hands of player and dealer.
     */
    private void showHands() {
        System.out.println("Your cards: " + player);
        System.out.println("Dealer cards: " + dealer);
    }

    /**
     * Helps to understand whether player (dealer) lose or not.
     * @param man - player or dealer
     * @return isLost (boolean)
     */
    private boolean lostOnLimits(Player man) {
        return man.getScore() > 21;
    }

    /**
     * Helps to understand have player won or not.
     * @return isPlayerWins (boolean)
     */
    private boolean roundWasWon() {
        return player.getScore() > dealer.getScore();
    }

    /**
     * simulates one round.
     * @param scanner - the input from player.
     */
    public void round(Scanner scanner) {
        setupCards();
        int input = 0;
        do {
            showHands();
            System.out.println("make your choice!\n1 - take a card; 0 - stay;");

            input = scanner.nextInt();

            if (input != 0) {
                player.takeCardFromDeck(deck);
            }
            if (lostOnLimits(player)) {
                showHands();
                dealerScore++;
                System.out.println("You lost(((");
                return;
            }
        } while (input != 0);



        dealer.showCard();
        while (dealer.getScore() < 17) {
            showHands();

            dealer.takeCardFromDeck(deck);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        showHands();
        if (lostOnLimits(dealer) || roundWasWon()) {
            playerScore++;
            System.out.println("You win!!!");
        } else {
            dealerScore++;
            System.out.println("You lost(((");
        }
    }

    /**
     * Getter for playerScore.
     * @return playerScore (int)
     */
    public int getPlayerScore() {
        return playerScore;
    }

    /**
     * Getter for dealerScore.
     * @return dealerScore(int)
     */
    public int getDealerScore() {
        return dealerScore;
    }

    /**
     * simulates the whole game for one deck.
     * @param rounds_limit - the limit for rounds
     * @param scanner - the input from player
     */
    public void game(int rounds_limit, Scanner scanner) {
        int round_number = 1;

        System.out.println("Welcome to blackjack!!!\nLet it begin!!!");
        do {
            System.out.println("Round " + round_number);
            round(scanner);
            player.clearState();
            dealer.clearState();
            round_number++;

            System.out.printf("The score is: %d : %d (You : dealer)\n", playerScore, dealerScore);
        } while (deck.size() > 4 && (round_number <= rounds_limit));
    }

}
