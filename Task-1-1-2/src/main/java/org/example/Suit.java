package org.example;

public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    SPADES("Spades"),
    CLUBS("Clubs");

    private final String cardSuit;

    Suit(String cardSuit) {
        this.cardSuit = cardSuit;
    }


    @Override
    public String toString() {
        return cardSuit;
    }
}
