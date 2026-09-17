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

    public enum MoveState {
        CONTINUE,
        LOST,
        STOP
    }


    public static final int DEALER_STOP_LOW_LIMIT = 17;
    public static final int THE_ABSOLUTE_WINNING_SCORE = 21;

    /**
     * Usual constructor (default).
     */
    public Engine(int decksCount) {
        this (new Player(), new Dealer(), new Deck(decksCount));
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
        player.clearState();
        dealer.clearState();
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
        return man.getScore() > THE_ABSOLUTE_WINNING_SCORE;
    }

    /**
     * Simulates ONE PLAYER move.
     * @param scanner - scanner for player choose
     * @return current MoveState of player
     */
    protected MoveState simulatePlayerMove(Scanner scanner) {
        int input = scanner.nextInt();

        if (input == 0) {
            return MoveState.STOP;
        }

        player.takeCardFromDeck(deck);
        if (lostOnLimits(player)) {
            return MoveState.LOST;
        } else if (player.getScore() == THE_ABSOLUTE_WINNING_SCORE) {
            return MoveState.STOP;
        }

        return MoveState.CONTINUE;
    }

    /**
     * Simulates ONE DEALER move.
     * @return current MoveState of dealer
     */
    protected MoveState simulateDealerMove() {
        if (dealer.getScore() >= DEALER_STOP_LOW_LIMIT) {
            return MoveState.STOP;
        }

        dealer.takeCardFromDeck(deck);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        if (lostOnLimits(dealer)) {
            return MoveState.LOST;
        } else if (dealer.getScore() >= DEALER_STOP_LOW_LIMIT) {
            return MoveState.STOP;
        }

        return MoveState.CONTINUE;
    }

    /**
     * simulates one round.
     * @param scanner - the input from player.
     */
    public void round(Scanner scanner) {
        setupCards();
        MoveState state;
        do {
            System.out.println("make your choice!\n1 - take a card; 0 - stay;");
            showHands();
            state = simulatePlayerMove(scanner);
        } while (state == MoveState.CONTINUE);

        if (state == MoveState.LOST) {
            showHands();
            dealerScore++;
            System.out.println("You lost(((");
            return;
        }

        dealer.showCard();
        do {
            showHands();
            state = simulateDealerMove();
        } while (state == MoveState.CONTINUE);

        showHands();
        if (state == MoveState.LOST || dealer.getScore() < player.getScore()) {
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
     * Getter for dealer hand score.
     * @return dealer.score
     */
    public int getDealerHandScore() {
        return dealer.getScore();
    }

    /**
     * Getter for player hand score.
     * @return player.score
     */
    public int getPlayerHandScore() {
        return player.getScore();
    }

    /**
     * simulates the whole game for one deck.
     * @param roundsLimit - the limit for rounds
     * @param scanner - the input from player
     */
    public void game(int roundsLimit, Scanner scanner) {
        int roundNumber = 1;

        System.out.println("Welcome to blackjack!!!\nLet it begin!!!");
        do {
            System.out.println("Round " + roundNumber);
            round(scanner);
            roundNumber++;

            System.out.printf("The score is: %d : %d (You : dealer)\n", playerScore, dealerScore);
        } while (deck.size() > 4 && (roundNumber <= roundsLimit));
    }

}
