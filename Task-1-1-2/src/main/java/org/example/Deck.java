package org.example;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Stack;

public class Deck {
    public static final int MAX_CARDS = 52;

    private final Stack<Card> cards = new Stack<>();

    public Deck() {
        loadCardsFromConfig();
        shuffle();
    }

    private void loadCardsFromConfig() {
        InputStream is = getClass().getClassLoader().getResourceAsStream("cards.txt");
        assert is != null : "Cannot access to file cards.txt in resources";

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(is,StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

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


    public void shuffle() {
        Collections.shuffle(cards);
    }

    public Card takeCard() {
        if (cards.empty()) { throw new IllegalStateException("The deck is empty"); }
        return cards.pop();
    }

    public int size() {
        return cards.size();
    }
}
