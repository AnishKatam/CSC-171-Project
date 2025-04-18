package Poker;
public class Card {
    private int cardValue;
    private String suit;
    private String suitColor;
    private boolean hasBeenDealt = false;
    
    public Card(int cardValue, String suit) {
        this.cardValue = cardValue;
        this.suit = suit;

        if(this.suit.equals("Hearts") || this.suit.equals("Diamonds")){
            this.suitColor = "Red";
        } else if(this.suit.equals("Clubs") || this.suit.equals("Spades")){
            this.suitColor = "Black";
}
    this.hasBeenDealt = false;
    }

    public int getCard() {
        return cardValue;
    }

    public String getSuit() {
        return suit;
    }

    public String getSuitColor() {
        return suitColor;
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
		switch(this.cardValue) {
			case 1: return "Ace";
			case 2: return "Two";
			case 3: return "Three";
			case 4: return "Four";
			case 5: return "Five";
			case 6: return "Six";
			case 7: return "Seven";
			case 8: return "Eight";
			case 9: return "Nine";
			case 10: return "Ten";
			case 11: return "Jack";
			case 12: return "Queen";
			case 13: return "King";
			default: return "Value not Valid";
		}
	}
}
