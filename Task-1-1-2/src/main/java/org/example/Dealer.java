package org.example;

public class Dealer extends Player {
    private boolean isCardHidden;

    public Dealer() {
        this.isCardHidden = true;
    }

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


    public void showCard() {
        isCardHidden = false;
    }

    @Override
    public void clearState() {
        super.clearState();
        isCardHidden = true;
    }
}
