package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        deck = new Deck();
    }

    @Test
    void sizeChangesAfterTake() {
        assertEquals(Deck.MAX_CARDS, deck.size());

        deck.takeCard();

        assertEquals(Deck.MAX_CARDS - 1, deck.size());
    }

    @Test
    void takeCardThrowsExceptionAtZero() {
        assertEquals(Deck.MAX_CARDS, deck.size());

        for (int i = 0; i < Deck.MAX_CARDS; i++) {
            deck.takeCard();
        }

        IllegalStateException exception = assertThrows(IllegalStateException.class, deck::takeCard);

        assertEquals("The deck is empty", exception.getMessage());
    }

}