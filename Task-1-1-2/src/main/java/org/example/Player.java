package org.example;

import java.util.Vector;

import java.util.Collections;

/**
 * Player class to collect hand and score in one structure.
 */
public class Player {
    protected final Vector<Card> hand;
    protected int score;

    /**
     * Player constructor.
     */
    public Player() {
        this.hand = new Vector<>();
        this.score = 0;
    }

    /**
     * Converts player to String for a better view.
     * @return Player (String)
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < hand.size(); i++) {
            stringBuilder.append(hand.get(i).toString());
            if (i != hand.size() - 1) { stringBuilder.append(", "); }
        }
        stringBuilder.append("] => ");
        stringBuilder.append(score);
        return stringBuilder.toString();
    }

    /**
     * Method to take card from the deck.
     * @param deck - deck for cards.
     */
    public void takeCardFromDeck(Deck deck) {
        Card given = deck.takeCard();
        score += given.getValue();
        hand.add(given);
    }

    /**
     * Getter for score.
     * @return score (int)
     */
    public int getScore() {
        return score;
    }

    /**
     * Clears state of object.
     */
    public void clearState() {
        hand.clear();
        score = 0;
    }

}
