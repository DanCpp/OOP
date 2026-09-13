package org.example;


/**
 * Dealer class to hide the last card in hand.
 */
public class Dealer extends Player {
    private boolean isCardHidden;

    /**
     * Constructor for dealer. Calls also Constructor for player.
     */
    public Dealer() {
        this.isCardHidden = true;
    }

    /**
     * Overrided version of Player:toString.
     * @return Dealer (String)
     */
    @Override
    public String toString() {
        if (!isCardHidden) { return super.toString(); }

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i = 0; i < hand.size() - 1; i++) {
            stringBuilder.append(hand.get(i).toString());
            stringBuilder.append(", ");
        }
        if (!hand.isEmpty()) { stringBuilder.append("<the card is hidden>"); }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * To view the hidden card.
     */
    public void showCard() {
        isCardHidden = false;
    }

    /**
     * Clear state of Dealer. Calls Player:clearState and change isCardHidden state.
     */
    @Override
    public void clearState() {
        super.clearState();
        isCardHidden = true;
    }
}
