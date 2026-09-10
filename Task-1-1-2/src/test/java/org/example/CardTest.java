package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    @Test
    void getFieldsFromRandomDeckCard() {
        Deck deck = new Deck();

        assertEquals(Deck.MAX_CARDS, deck.size());

        Card card = deck.takeCard();
        assertEquals(card.toString(), String.format("%s (%d)", card.getName(), card.getValue()));
    }

}