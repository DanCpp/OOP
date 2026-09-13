package org.example;

import java.util.Vector;

import java.util.Collections;

public class Player {
    protected final Vector<Card> hand;
    protected int score;

    public Player() {
        this.hand = new Vector<>();
        this.score = 0;
    }

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

    public void takeCardFromDeck(Deck deck) {
        Card given = deck.takeCard();
        score += given.getValue();
        hand.add(given);
    }

    public int getScore() {
        return score;
    }

    public void clearState() {
        hand.clear();
        score = 0;
    }

}
