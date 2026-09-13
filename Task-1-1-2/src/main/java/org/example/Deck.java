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
    public static final int MAX_CARDS = 52;

    private final Stack<Card> cards;

    /**
     * Most common Deck constructor.
     */
    public Deck() {
        cards = new Stack<>();
        loadCardsFromConfig();
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
     * loads cards from resources/cards.txt config.
     */
    private void loadCardsFromConfig() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("cards.txt");
        assert is != null : "Cannot access to file cards.txt in resources";

        try (BufferedReader reader
                     = new BufferedReader(
                             new InputStreamReader(is, StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int value = Integer.parseInt(parts[1].trim());
                cards.push(new Card(name, value));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.exit(1);
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
