package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        deck = new Deck();
    }

    @Test
    void getFieldsFromRandomDeckCard() {
        assertEquals(Deck.MAX_CARDS, deck.size());

        Card card = deck.takeCard();
        assertEquals(card.toString(), String.format("%s (%d)", card.getName(), card.getValue()));
    }

}