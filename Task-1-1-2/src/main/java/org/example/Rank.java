package org.example;

/**
 * simple enum for rank of card.
 */
public enum Rank {
    TWO(2, "Two"),
    THREE(3, "Three"),
    FOUR(4, "Four"),
    FIVE(5, "Five"),
    SIX(6, "Six"),
    SEVEN(7, "Seven"),
    EIGHT(8, "Eight"),
    NINE(9, "Nine"),
    TEN(10, "Ten"),
    JACK(10, "Jack"),
    QUEEN(10, "Queen"),
    KING(10, "King"),
    ACE(10, "Ace");

    private final int value;
    private final String displayName;


    /**
     * constructor for enum.
     * @param value - value of card spelled by rules.
     * @param displayName - name of card that would be written in console.
     */
    Rank(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    /**
     * Getter for value.
     * @return value(int)
     */
    public int getValue() {
        return value;
    }

    /**
     * Convert enum to string.
     * @return displayName(String)
     */
    @Override
    public String toString() {
        return displayName;
    }
}
