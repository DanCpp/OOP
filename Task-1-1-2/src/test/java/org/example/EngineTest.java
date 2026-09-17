package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Scanner;
import java.util.Vector;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @BeforeEach
    void setup() {
        Vector<Card> setupCards = new Vector<>();

        setupCards.add(new Card(Rank.QUEEN, Suit.SPADES));
        setupCards.add(new Card(Rank.SEVEN, Suit.SPADES));
        setupCards.add(new Card(Rank.JACK, Suit.CLUBS));
        setupCards.add(new Card(Rank.TWO, Suit.SPADES));
        setupCards.add(new Card(Rank.QUEEN, Suit.SPADES));
        setupCards.add(new Card(Rank.JACK, Suit.CLUBS));
        setupCards.add(new Card(Rank.SEVEN, Suit.SPADES));

        setupCards.add(new Card(Rank.EIGHT, Suit.SPADES));
        setupCards.add(new Card(Rank.KING, Suit.SPADES));
        setupCards.add(new Card(Rank.TEN, Suit.SPADES));
        setupCards.add(new Card(Rank.THREE, Suit.DIAMONDS));
        setupCards.add(new Card(Rank.FOUR, Suit.DIAMONDS));

        Deck deck = new Deck(setupCards);
        Player player = new Player();
        Dealer dealer = new Dealer();

        engine = new Engine(player, dealer, deck);
    }


    @Test
    void dealerTakesUntilSeventeen() {
        assertEquals(0, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.CONTINUE, engine.simulateDealerMove());
        assertEquals(4, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.CONTINUE, engine.simulateDealerMove());
        assertEquals(7, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.STOP, engine.simulateDealerMove());
        assertEquals(Engine.DEALER_STOP_LOW_LIMIT, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.STOP, engine.simulateDealerMove());
        assertEquals(Engine.DEALER_STOP_LOW_LIMIT, engine.getDealerHandScore());
    }

    @Test
    void playerLostOnLimits() {
        assertEquals(0, engine.getDealerHandScore());
        assertEquals(0, engine.getPlayerHandScore());

        assertEquals(0, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());

        Scanner scanner = new Scanner("1");
        engine.round(scanner);

        assertEquals(1, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());

        assertTrue(engine.getPlayerHandScore() > Engine.THE_ABSOLUTE_WINNING_SCORE);
    }

    @Test
    void dealerWinsWithBlackjack() {
        assertEquals(0, engine.getDealerHandScore());
        assertEquals(0, engine.getPlayerHandScore());

        assertEquals(0, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());

        Scanner scanner = new Scanner("0");
        engine.round(scanner);

        assertEquals(1, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());

        assertEquals(Engine.THE_ABSOLUTE_WINNING_SCORE, engine.getDealerHandScore());
    }

    @Test
    void playerAlwaysStopsAtTwentyOne() {
        assertEquals(0, engine.getPlayerHandScore());

        Scanner scanner = new Scanner("0 1 1 0 1");
        Engine.MoveState state = Engine.MoveState.CONTINUE;
        for (int i = 0; i < 5; i++) {
            state = engine.simulatePlayerMove(scanner);
            if (state == Engine.MoveState.STOP) {
                // that if used to skip some cards with 0 choice of player
                // in the deck that would be FOUR and KING
                engine.simulateDealerMove();
            }
        }

        assertEquals(21, engine.getPlayerHandScore());
        assertEquals(Engine.MoveState.STOP, state);
    }

    @Test
    void dealerLostOnLimits() {
        assertEquals(0, engine.getDealerHandScore());

        Scanner scanner = new Scanner("1");
        engine.simulatePlayerMove(scanner);

        assertEquals(Engine.MoveState.CONTINUE, engine.simulateDealerMove());
        assertEquals(3, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.CONTINUE, engine.simulateDealerMove());
        assertEquals(13, engine.getDealerHandScore());

        assertEquals(Engine.MoveState.LOST, engine.simulateDealerMove());
        assertEquals(23, engine.getDealerHandScore());
    }


    @Test
    void playGameWithDraw() {
        assertEquals(0, engine.getDealerScore());
        assertEquals(0, engine.getPlayerScore());

        Scanner scanner = new Scanner("0 0");
        engine.game(2, scanner);

        assertEquals(1, engine.getDealerScore());
        assertEquals(1, engine.getPlayerScore());
    }
}