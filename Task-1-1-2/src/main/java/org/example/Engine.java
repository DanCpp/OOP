package org.example;

import java.util.Scanner;

public class Engine {
    private final Player player;
    private final Dealer dealer;
    private final Deck deck;
    private int playerScore;
    private int dealerScore;


    public Engine() {
        this (new Player(), new Dealer(), new Deck());
    }

    public Engine(Player player, Dealer dealer, Deck deck) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        this.playerScore = 0;
        this.dealerScore = 0;
    }

    private void setupCards() {
        for (int i = 0; i < 2; i++) {
            player.takeCardFromDeck(deck);
            dealer.takeCardFromDeck(deck);
        }
    }

    private void showHands() {
        System.out.println("Your cards: " + player);
        System.out.println("Dealer cards: " + dealer);
    }

    private boolean lostOnLimits(Player man) {
        return man.getScore() > 21;
    }

    private boolean roundWasWon() {
        return player.getScore() > dealer.getScore();
    }

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

    public int getPlayerScore() {
        return playerScore;
    }

    public int getDealerScore() {
        return dealerScore;
    }

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
