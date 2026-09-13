package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @BeforeEach
    void setup() {
        Vector<Card> setupCards = new Vector<>();

        setupCards.add(new Card("Queen of Spades", 10));
        setupCards.add(new Card("Seven of Spades", 7));
        setupCards.add(new Card("Jack of Clubs", 10));
        setupCards.add(new Card("Eight of Spades", 8));
        setupCards.add(new Card("Three of Diamonds", 3));
        setupCards.add(new Card("Four of Diamonds", 4));

        setupCards.add(new Card("Queen of Spades", 10));
        setupCards.add(new Card("Jack of Clubs", 10));
        setupCards.add(new Card("Seven of Spades", 7));
        setupCards.add(new Card("Eight of Spades", 8));
        setupCards.add(new Card("King of Spades", 10));
        setupCards.add(new Card("Ten of Spades", 10));

        Deck deck = new Deck(setupCards);
        Player player = new Player();
        Dealer dealer = new Dealer();

        engine = new Engine(player, dealer, deck);
    }


    @Test
    void lostOnScore() {
        Scanner testScanner = new Scanner("1");

        engine.round(testScanner);
        assertEquals(1, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());
    }

    @Test
    void winAtFirstMove() {
        Scanner testScanner = new Scanner("0");

        engine.round(testScanner);
        assertEquals(0, engine.getDealerScore());
        assertEquals(1, engine.getPlayerScore());
    }

    @Test
    void lostAndWin() {
        Scanner testScanner = new Scanner("1\n0");

        engine.game(2, testScanner);
        assertEquals(1, engine.getDealerScore());
        assertEquals(1, engine.getPlayerScore());
    }

}