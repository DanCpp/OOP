package org.example;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Stack;
import java.util.Vector;

/**
 * The Deck class for collect a deck of cards.
 */
public class Deck {
    public static final int MAX_CARDS_IN_ONE_DECK = 52;

    private final Stack<Card> cards;

    /**
     * Most common Deck constructor.
     */
    public Deck() {
        cards = new Stack<>();
        loadFullDeckOfCards();
        shuffle();
    }

    /**
     * Deck constructor for a lot of decks in game
     * @param decksCount - how many decks would be in game
     */
    public Deck(int decksCount) {
        cards = new Stack<>();
        for (int i = 0; i < decksCount; i++) {
            loadFullDeckOfCards();
        }
        shuffle();
    }

    /**
     * Another Deck constructor if you already have cards to put in.
     * @param cards - cards that would be in Deck
     */
    public Deck(Vector<Card> cards) {
        this.cards = new Stack<>();
        this.cards.addAll(cards);
    }

    /**
     * loads cards with all variations of enum combinations.
     */
    private void loadFullDeckOfCards() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.push(new Card(rank, suit));
            }
        }
    }


    /**
     * shuffles cards.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Is used to take card from deck.
     * @return card (Card)
     */
    public Card takeCard() {
        if (cards.empty()) {
            throw new IllegalStateException("The deck is empty");
        }
        return cards.pop();
    }

    /**
     * Returns the current size of deck.
     * @return size (int)
     */
    public int size() {
        return cards.size();
    }
}
