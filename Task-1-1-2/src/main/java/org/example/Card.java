package org.example;

public final class Card {
    private final String name;
    private final int value;


    public Card(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%d)", name, value);
    }
}
