package Poker;

public class Card {
    private int cardValue;
    private String suit;
    private String suitColor;
    private boolean hasBeenDealt = false;
    private final String[] UNICODE_SUITS = {"♥", "♦", "♣", "♠"};

    public Card(int cardValue, String suit) {
        this.cardValue = cardValue;
        this.suit = suit;
        this.hasBeenDealt = false;
        if(suit.equals("Hearts") || suit.equals("Diamonds")) {
            this.suitColor = "RED";
        } else {
            this.suitColor = "BLACK";
        }
    }

    public int getCard() {
        return cardValue;
    }

    public String getSuit() {
        return suit;
    }

    public String getUnicodeSuit() {
        switch (suit) {
            case "Hearts":
                return UNICODE_SUITS[0];
            case "Diamonds":
                return UNICODE_SUITS[1];
            case "Clubs":
                return UNICODE_SUITS[2];
            case "Spades":
                return UNICODE_SUITS[3];
            default:
                return "?";
        }
    }

    public String getSuitColor() {
        if (this.suit.equals("Hearts") || this.suit.equals("Diamonds")) {
            return "Red";
        }
        return "Black";
    }

    public boolean isDealt() {
        return hasBeenDealt;
    }

    public void setDealt(boolean dealt) {
        this.hasBeenDealt = dealt;
    }

    public void reset() {
        this.hasBeenDealt = false;
    }

    public String convertValueToName() {
        switch (this.cardValue) {
            case 1:
                return "Ace";
            case 2:
                return "Two";
            case 3:
                return "Three";
            case 4:
                return "Four";
            case 5:
                return "Five";
            case 6:
                return "Six";
            case 7:
                return "Seven";
            case 8:
                return "Eight";
            case 9:
                return "Nine";
            case 10:
                return "Ten";
            case 11:
                return "Jack";
            case 12:
                return "Queen";
            case 13:
                return "King";
            default:
                return "Value not Valid";
        }
    }

    public String convertValueToCardName() {
        switch (this.cardValue) {
            case 1:
                return "A";
            case 2:
                return "2";
            case 3:
                return "3";
            case 4:
                return "4";
            case 5:
                return "5";
            case 6:
                return "6";
            case 7:
                return "7";
            case 8:
                return "8";
            case 9:
                return "9";
            case 10:
                return "10";
            case 11:
                return "J";
            case 12:
                return "Q";
            case 13:
                return "K";
            default:
                return "Value not Valid";
        }
    }
}
