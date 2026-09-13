package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;
class PlayerTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        Vector<Card> setupCards = new Vector<>();
        setupCards.add(new Card("Queen of Spades", 10));
        setupCards.add(new Card("Jack of Clubs", 10));

        deck = new Deck(setupCards);
    }


    @Test
    void initialState() {
        Player player = new Player();

        assertEquals(0, player.getScore());
        assertEquals(0, player.hand.size());
    }


    @Test
    void takeCardAndGetHigherScore() {
        Player player = new Player();

        player.takeCardFromDeck(deck);
        assertEquals(10, player.getScore());
        assertEquals("[Jack of Clubs (10)] => 10", player.toString());

        player.takeCardFromDeck(deck);
        assertEquals(20, player.getScore());
        assertEquals("[Jack of Clubs (10), Queen of Spades (10)] => 20", player.toString());
    }

    @Test
    void takeAndClearState() {
        Player player = new Player();

        player.takeCardFromDeck(deck);
        assertEquals(10, player.getScore());
        assertEquals("[Jack of Clubs (10)] => 10", player.toString());

        player.clearState();
        assertEquals(0, player.getScore());
        assertEquals(0, player.hand.size());
        assertEquals("[] => 0", player.toString());
    }
}