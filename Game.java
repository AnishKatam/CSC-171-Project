package Poker;
import random;

public class Game {
    public Card giveHands(){
        int randomCard = random.randint(1, 13);
        String[] suits = {"Hearts", "Diamonds", "Clubs", "Spades"};
        String suit = suits[random.randint(0, 3)];
        Card card1 = new Card(randomCard, suit);
        card1.setDealt(true);
    
        int randomCard2 = random.randint(1, 13);
        String suit2 = suits[random.randint(0, 3)];
        if (suit2.equals(suit)) {
            suit2 = suits[random.randint(0, 3)];
        }
        Card card2 = new Card(randomCard2, suit2);
        card2.setDealt(true);
        return card1,card2;
    }
}
