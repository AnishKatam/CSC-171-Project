package Poker;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scan = new Scanner(System.in);
        System.out.println("How many players are playing?");
        int numPlayers = scan.nextInt();
        scan.nextLine();
        Player[] players = new Player[numPlayers];
        if (numPlayers < 2 || numPlayers > 8) {
            System.out.println("Invalid number of players.");
            return;
        } else {
            for (int i = 0; i < numPlayers; i++) {
                System.out.println("Enter player name: ");
                String name = scan.nextLine();
                System.out.println("Enter chips for player " + name + ":");
                System.out.println("White chips (1): ");
                int white = scan.nextInt();
                System.out.println("Red chips (5): ");
                int red = scan.nextInt();
                System.out.println("Green chips (25): ");
                int green = scan.nextInt();
                System.out.println("Blue chips (50): ");
                int blue = scan.nextInt();
                System.out.println("Black chips (100): ");
                int black = scan.nextInt();
                scan.nextLine();
                int[] chips = {white, red, green, blue, black};
                players[i] = new Player(name, chips);
            }
        }
        System.out.println("How many rounds do you want to play?");
        int rounds = scan.nextInt();
        scan.nextLine();

        int underTheGun = random.nextInt(numPlayers);
        int bigBlind = (underTheGun + numPlayers - 1) % numPlayers;
        int smallBlind = (bigBlind + numPlayers - 1) % numPlayers;
        players[bigBlind].setToBigBlind(true);
        players[smallBlind].setToSmallBlind(true);
        players[smallBlind].bestDeduction(2);

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Player " + players[i].getName() + " has " + players[i].getBalance() + " dollars of chips.");
        }
        System.out.println();
        System.out.println("The big blind is 5$ and small blind is 2$");
        System.out.println("The game is starting, press enter to continue.");
        scan.nextLine();
        System.out.println();
        System.out.println("The player under the gun is " + players[underTheGun].getName() + ".");
        System.out.println("The big blind is " + players[bigBlind].getName() + ".");
        System.out.println("The small blind is " + players[smallBlind].getName() + ".");
        System.out.println("Press enter to continue.");
        scan.nextLine();
        System.out.println("Dealing cards...");
        System.out.println();


        int raise = 0;
        int allIn = 0;
        String action = "";
        for (int i = 0; i < rounds; i++) {
            // PREFLOP 
            System.out.println("Preflop Round: " + i);
            Game game = new Game();
            game.giveHands(players);
            for (Player currentPlayer : players) {
                System.out.println(currentPlayer.getName() + " has " + currentPlayer.getHand()[0].convertValueToName() + " of " + currentPlayer.getHand()[0].getSuit() + " and " + currentPlayer.getHand()[1].convertValueToName() + " of " + currentPlayer.getHand()[1].getSuit());
            }
            System.out.println();

            System.out.println("The player under the gun is " + players[underTheGun].getName() + ".");
            System.out.println("The big blind is " + players[bigBlind].getName() + ".");
            players[bigBlind].bestDeduction(5);
            game.addToPot(5);
            System.out.println("The small blind is " + players[smallBlind].getName() + ".");
            players[smallBlind].bestDeduction(2);
            game.addToPot(2);

            System.out.println("Current pot: " + game.calculatePot());

            System.out.println("Player under the gun, do you want to fold, call, raise or all in?");
            action = scan.nextLine();
            if (action.equals("fold")) {
                players[underTheGun].setToFold(true);
                System.out.println(players[underTheGun].getName() + " has folded.");
            } else if (action.equals("call")) {
                players[underTheGun].setHasToCall(false);
                System.out.println(players[underTheGun].getName() + " has called.");
                players[underTheGun].bestDeduction(5);
                game.addToPot(5);
            } else if (action.equals("raise")) {
                System.out.println("How much do you want to raise?");
                raise = scan.nextInt();
                scan.nextLine();
                players[underTheGun].bestDeduction(raise);
                game.addToPot(raise);
                players[underTheGun].setToRaise(true);
            } else if (action.equals("all in")) {
                players[underTheGun].setToAllIn(true);
                System.out.println(players[underTheGun].getName() + " has gone all in.");
                allIn = players[underTheGun].getBalance();
                players[underTheGun].bestDeduction(allIn);
                game.addToPot(allIn);
            } else {
                System.out.println("Invalid action. Skipping turn (You are Folded).");
                players[underTheGun].setToFold(true);
            }

            System.out.println("Current pot: " + game.calculatePot());

            for (int p = 0; p < numPlayers; p++) {
                int currentPlayerIndex = (underTheGun + p) % numPlayers;
                Player currentPlayer = players[currentPlayerIndex];

                if (currentPlayer.isFolded()) {
                    System.out.println(currentPlayer.getName() + " has folded and their turn is skipped");
                    continue;
                }

                if (currentPlayer.isAllIn()) {
                    System.out.println(currentPlayer.getName() + " has gone all in and their turn is skipped");
                    continue;
                }

                if (currentPlayer.hasToCall()) {
                    System.out.println("\nIt's " + currentPlayer.getName() + "'s turn");
                    System.out.println("Do you want to fold, call, raise or go all in?");

                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        currentPlayer.setToFold(true);
                        System.out.println(currentPlayer.getName() + " has folded.");
                    } else if (action.equals("call")) {
                        currentPlayer.setHasToCall(false);
                        System.out.println(currentPlayer.getName() + " has called.");
                        currentPlayer.bestDeduction(raise);
                        game.addToPot(raise);
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        currentPlayer.bestDeduction(raise);
                        game.addToPot(raise);
                        currentPlayer.setToRaise(true);
                    } else if (action.equals("all in")) {
                        currentPlayer.setToAllIn(true);
                        System.out.println(currentPlayer.getName() + " has gone all in.");
                        allIn = currentPlayer.getBalance();
                        currentPlayer.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        currentPlayer.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());

                } else if (currentPlayer.isSmallBlind()) {
                    System.out.println(currentPlayer.getName() + " is the small blind and has to call 2$ or raise.");
                    System.out.println("Do you want to call or raise?");
                    action = scan.nextLine();
                    if (action.equals("call")) {
                        currentPlayer.setHasToCall(false);
                        System.out.println(currentPlayer.getName() + " has called.");
                        currentPlayer.bestDeduction(2);
                        game.addToPot(2);
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        currentPlayer.bestDeduction(raise);
                        game.addToPot(raise);
                        currentPlayer.setToRaise(true);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        currentPlayer.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());

                } else if (!currentPlayer.hasToCall()) {
                    System.out.println("\nIt's " + currentPlayer.getName() + "'s turn");
                    System.out.println("He doesn't need to call, so he is able to fold, raise or go all in.");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        currentPlayer.setToFold(true);
                        System.out.println(currentPlayer.getName() + " has folded.");
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        currentPlayer.bestDeduction(raise);
                        game.addToPot(raise);
                        currentPlayer.setToRaise(true);
                    } else if (action.equals("all in")) {
                        currentPlayer.setToAllIn(true);
                        System.out.println(currentPlayer.getName() + " has gone all in.");
                        allIn = currentPlayer.getBalance();
                        currentPlayer.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        currentPlayer.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());
                }
            }

            //FLOP
            System.out.println("Flop Round: " + i);
            System.out.println("Current pot: " + game.calculatePot());
            System.out.println("Dealing flop...");
            Card[] flop = game.giveFlop();
            System.out.println("The flop is: ");
            for (Card card : flop) {
                System.out.println(card.convertValueToName() + " of " + card.getSuit());
            }
            System.out.println();
            for (Player player : players) {
                if (!player.isFolded()) {
                    System.out.println(player.getName() + " has " + player.getHand()[0].convertValueToName() + " of " + player.getHand()[0].getSuit() + " and " + player.getHand()[1].convertValueToName() + " of " + player.getHand()[1].getSuit());
                }
            }
            System.out.println();

            for (Player player : players) {
                if (!player.isFolded() && !player.isAllIn() && !player.hasToCall()) {
                    System.out.println("It's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, check, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("check")) {
                        System.out.println(player.getName() + " has checked.");
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());

                } else if (player.hasToCall()) {
                    System.out.println("\nIt's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, call, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("call")) {
                        player.setHasToCall(false);
                        System.out.println(player.getName() + " has called.");
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());
                }
            }

            //FOURTH STREET
            System.out.println("Fourth Street: " + i);
            System.out.println("Current pot: " + game.calculatePot());
            System.out.println("Dealing turn card...");
            Card turnCard = game.giveTurn();
            System.out.println("The turn card is: ");
            System.out.println(turnCard.convertValueToName() + " of " + turnCard.getSuit());
            System.out.println();

            for (Player player : players) {
                if (!player.isFolded()) {
                    System.out.println(player.getName() + " has " + player.getHand()[0].convertValueToName() + " of " + player.getHand()[0].getSuit() + " and " + player.getHand()[1].convertValueToName() + " of " + player.getHand()[1].getSuit());
                }
            }
            System.out.println();

            for (Player player : players) {
                if (!player.isFolded() && !player.isAllIn() && !player.hasToCall()) {
                    System.out.println("It's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, check, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("check")) {
                        System.out.println(player.getName() + " has checked.");
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());

                } else if (player.hasToCall()) {
                    System.out.println("\nIt's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, call, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("call")) {
                        player.setHasToCall(false);
                        System.out.println(player.getName() + " has called.");
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());
                }
            }

            //FIFTH STREET 
            System.out.println("Fifth Street: " + i);
            System.out.println("Current pot: " + game.calculatePot());
            System.out.println("Dealing river card...");
            Card riverCard = game.giveTurn();
            System.out.println("The river card is: ");
            System.out.println(riverCard.convertValueToName() + " of " + riverCard.getSuit());
            System.out.println();

            for (Player player : players) {
                if (!player.isFolded()) {
                    System.out.println(player.getName() + " has " + player.getHand()[0].convertValueToName() + " of " + player.getHand()[0].getSuit() + " and " + player.getHand()[1].convertValueToName() + " of " + player.getHand()[1].getSuit());
                }
            }
            System.out.println();

            for (Player player : players) {
                if (!player.isFolded() && !player.isAllIn() && !player.hasToCall()) {
                    System.out.println("It's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, check, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("check")) {
                        System.out.println(player.getName() + " has checked.");
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());

                } else if (player.hasToCall()) {
                    System.out.println("\nIt's " + player.getName() + "'s turn");
                    System.out.println("Do you want to fold, call, raise or go all in?");
                    action = scan.nextLine();
                    if (action.equals("fold")) {
                        player.setToFold(true);
                        System.out.println(player.getName() + " has folded.");
                    } else if (action.equals("call")) {
                        player.setHasToCall(false);
                        System.out.println(player.getName() + " has called.");
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                    } else if (action.equals("raise")) {
                        System.out.println("How much do you want to raise?");
                        raise = scan.nextInt();
                        scan.nextLine();
                        player.bestDeduction(raise);
                        game.addToPot(raise);
                        player.setToRaise(true);
                    } else if (action.equals("all in")) {
                        player.setToAllIn(true);
                        System.out.println(player.getName() + " has gone all in.");
                        allIn = player.getBalance();
                        player.bestDeduction(allIn);
                        game.addToPot(allIn);
                    } else {
                        System.out.println("Invalid action. Skipping turn.");
                        player.setToFold(true);
                    }

                    System.out.println("Current pot: " + game.calculatePot());
                }
            }

            //SHOWDOWN  
            System.out.println("Showdown Round: " + i);
            System.out.println("Current pot: " + game.calculatePot());
            for (Player player : players) {
                if (!player.isFolded() && !player.isAllIn()) {
                    System.out.println(player.getName() + " has " + player.getHand()[0].convertValueToName() + " of " + player.getHand()[0].getSuit() + " and " + player.getHand()[1].convertValueToName() + " of " + player.getHand()[1].getSuit());
                }
            }
            System.out.println();
            System.out.println("Enter winners name: ");
            String winnerName = scan.nextLine();
            System.out.println(game.declareWinner(players, winnerName));
            game.resetPot();

            // Reset all players for the next round and change big blind small blind and under the gun
            for (Player player : players) {
                player.newRound();
            }
            underTheGun = (underTheGun + 1) % numPlayers;
            bigBlind = (underTheGun + numPlayers - 1) % numPlayers;
            smallBlind = (bigBlind + numPlayers - 1) % numPlayers;
            players[bigBlind].setToBigBlind(true);
            players[smallBlind].setToSmallBlind(true);
            players[smallBlind].bestDeduction(2);
            players[bigBlind].bestDeduction(5);

        }
    }
}