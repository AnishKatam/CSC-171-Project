package Poker;
import java.util.Random;
public class Deck {
    private Card[] deck;
    private int currentCardIndex;
    private static final int DECK_SIZE = 52;
    private static final String[] SUITS = {"Hearts", "Diamonds", "Clubs", "Spades"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};


    public Deck() {
        deck = new Card[DECK_SIZE];
        currentCardIndex = 0;
        int index = 0;
        for (String suit : SUITS) {
            for (int value = 1; value <= RANKS.length; value++) {
                deck[index++] = new Card(value, suit);
            }
        }
    }

    public Card dealCard() {
        Random random = new Random();
        int cardIndex;
        do {
            cardIndex = random.nextInt(DECK_SIZE);
        } while (deck[cardIndex].isDealt());
        deck[cardIndex].setDealt(true);
        return deck[cardIndex];
    }
    
        
    
    



}
