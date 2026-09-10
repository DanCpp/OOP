package org.example;

import java.util.Vector;

public class Player {
    private Vector<Card> hand;
    private String name;

    public Player(String name) {
        this.name = name;
        this.hand = new Vector<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < hand.size(); i++) {
            stringBuilder.append(hand.get(i).toString());
            if (i != hand.size() - 1) { stringBuilder.append(", "); }
        }

        return stringBuilder.toString();
    }
}
