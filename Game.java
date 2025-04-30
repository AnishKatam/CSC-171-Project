package Poker;

import java.util.Random;

public class Game {
    private Deck deck;
    private int pot;

    public Game() {
        deck = new Deck();
        pot = 0;
    }

    public void giveHands(Player[] players) {
        for (Player player : players) {
            Card[] playerHand = new Card[2];

            playerHand[0] = deck.dealCard();
            playerHand[0].setDealt(true);

            playerHand[1] = deck.dealCard();
            playerHand[1].setDealt(true);

            player.setHand(playerHand);
        }
    }

    public Card[] giveFlop() {
        Card[] cards = new Card[3];

        // Deal 3 cards for the flop
        for (int i = 0; i < 3; i++) {
            cards[i] = deck.dealCard();
        }

        return cards;
    }

    public Card giveTurn() {
        return deck.dealCard();
    }

    public static int countActivePlayers(Player[] players) {
        int count = 0;
        for (Player player : players) {
            if (!player.isFolded() && !player.isAllIn()) {
                count++;
            }
        }
        return count;
    }

    public String declareWinner(Player[] players, String winnerName) {
        for (Player player : players) {
            if (player.getName().equals(winnerName)) {
                player.addBalance(pot);
                return player.getName() + " wins the pot!";
            }
        }
        return "No winner found";
    }


    public int calculatePot() {
        return pot;
    }

    public void addToPot(int amount) {
        pot += amount;
    }

    public void resetPot() {
        pot = 0;
    }
}