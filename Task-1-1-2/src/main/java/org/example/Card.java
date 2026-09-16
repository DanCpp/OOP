package org.example;

/**
 * The Card class for storing card with it's name to show and value to evaluate score.
 */
public final class Card {
    private final Suit suit;
    private final Rank rank;

    /**
     * Card constructor.
     * @param name - name of card.
     * @param value - value of card by rules.
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Getter for value.
     * @return value (int)
     */
    public int getValue() {
        return rank.getValue();
    }

    /**
     * Getter for name.
     * @return name (String)
     */
    public String getName() {
        return rank.toString() + " " + suit.toString();
    }

    /**
     * To convert card in a String way.
     * @return card (String)
     */
    @Override
    public String toString() {
        return String.format("%s (%d)", getName(), rank.getValue());
    }
}
