package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class DealerTest {

    private Deck deck;

    @BeforeEach
    void setup() {
        Vector<Card> setupCards = new Vector<>();
        setupCards.add(new Card("Queen of Spades", 10));
        setupCards.add(new Card("Jack of Clubs", 10));

        deck = new Deck(setupCards);
    }


    @Test
    void takeCardAndGetHigherScore() {
        Dealer dealer = new Dealer();
        dealer.showCard();

        dealer.takeCardFromDeck(deck);
        assertEquals(10, dealer.getScore());
        assertEquals("[Jack of Clubs (10)] => 10", dealer.toString());

        dealer.takeCardFromDeck(deck);
        assertEquals(20, dealer.getScore());
        assertEquals("[Jack of Clubs (10), Queen of Spades (10)] => 20", dealer.toString());
    }


    @Test
    void takeCardAndShowHidden() {
        Dealer dealer = new Dealer();

        dealer.takeCardFromDeck(deck);
        assertEquals(10, dealer.getScore());
        assertEquals("[<the card is hidden>]", dealer.toString());

        dealer.takeCardFromDeck(deck);
        assertEquals(20, dealer.getScore());
        assertEquals("[Jack of Clubs (10), <the card is hidden>]", dealer.toString());

        dealer.showCard();
        assertEquals(20, dealer.getScore());
        assertEquals("[Jack of Clubs (10), Queen of Spades (10)] => 20", dealer.toString());
    }

    @Test
    void showHandAndClearState() {
        Dealer dealer = new Dealer();

        dealer.takeCardFromDeck(deck);
        assertEquals(10, dealer.getScore());
        assertEquals("[<the card is hidden>]", dealer.toString());

        dealer.showCard();
        assertEquals(10, dealer.getScore());
        assertEquals("[Jack of Clubs (10)] => 10", dealer.toString());

        dealer.clearState();
        assertEquals(0, dealer.getScore());
        assertEquals(0, dealer.hand.size());
        assertEquals("[]", dealer.toString());

        dealer.takeCardFromDeck(deck);
        assertEquals(10, dealer.getScore());
        assertEquals("[<the card is hidden>]", dealer.toString());

        dealer.showCard();
        assertEquals(10, dealer.getScore());
        assertEquals("[Queen of Spades (10)] => 10", dealer.toString());
    }

}