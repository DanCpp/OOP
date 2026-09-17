package org.example;

/**
 * enum for suit of card.
 */
public enum Suit {
    HEARTS("Hearts"),
    DIAMONDS("Diamonds"),
    SPADES("Spades"),
    CLUBS("Clubs");

    private final String cardSuit;

    /**
     * simple constructor for enum.
     * @param cardSuit - card suit.
     */
    Suit(String cardSuit) {
        this.cardSuit = cardSuit;
    }


    /**
     * Converts enum to string.
     * @return suit(String).
     */
    @Override
    public String toString() {
        return cardSuit;
    }
}
