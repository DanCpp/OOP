package org.example;

/**
 * The Card class for storing card with it's name to show and value to evaluate score.
 */
public final class Card {
    private final String name;
    private final int value;

    /**
     * Card constructor.
     * @param name - name of card.
     * @param value - value of card by rules.
     */
    public Card(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * Getter for value.
     * @return value (int)
     */
    public int getValue() {
        return value;
    }

    /**
     * Getter for name.
     * @return name (String)
     */
    public String getName() {
        return name;
    }

    /**
     * To convert card in a String way.
     * @return card (String)
     */
    @Override
    public String toString() {
        return String.format("%s (%d)", name, value);
    }
}
