package Poker;
public class Player {
    private final String name;
    private int[] chips = new int[5];
    //[white = 1, red = 5, green = 25, blue = 50, black = 100]
    private boolean isFolded;
    private boolean isAllIn;
    private boolean isSmallBlind;
    private boolean isBigBlind;
    private boolean hasRaised;
    private boolean hasToCall = false;
    private int balance;
    private Card[] hand = new Card[2];
    private boolean isCurrentPlayer = false;
    private int winnings = 0;
    public Player(String name, int[] chips){
        this.name = name;
        this.chips = chips;
        this.isFolded = false;
        this.isAllIn = false;
        this.isSmallBlind = false;
        this.isBigBlind = false;
        this.hasRaised = false;
        this.balance += chips[0] + chips[1] * 5 + chips[2] * 25 + chips[3] * 50 + chips[4] * 100;
        //small blind = 2, big blind = 5
        this.hand = new Card[]{null, null};
        this.isCurrentPlayer = false;
        this.winnings = 0;
    }
    public String getName(){
        return name;
    }
    public int[] getChips(){
        return chips;
    }
    public Card[] getHand(){
    return hand;}
    public void setHand(Card[] hand){
        this.hand = hand;
    }
    public void setCurrentPlayer(boolean isCurrentPlayer){
    this.isCurrentPlayer = isCurrentPlayer;}
    public boolean isCurrentPlayer(){
        return isCurrentPlayer;
    }

    public boolean isFolded(){
        return isFolded;
    }
    public boolean isAllIn(){
        return isAllIn;
    }
    public boolean isSmallBlind(){
        return isSmallBlind;
    }
    public boolean isBigBlind(){
        return isBigBlind;
    }
    public boolean hasToCall() {
        return hasToCall;
    }
    public int getBalance(){
        return balance;
    }
    
    public void setHasToCall(boolean hasToCall) {
        this.hasToCall = hasToCall;
    }
    public void setToAllIn(boolean isAllIn){
        this.isAllIn = isAllIn;
    }

    public void setToFold(boolean isFolded){
        this.isFolded = isFolded;
    }
    public void setToSmallBlind(boolean isSmallBlind){
        this.isSmallBlind = isSmallBlind;
    }
    public void setToBigBlind(boolean isBigBlind){
        this.isBigBlind = isBigBlind;
    }
    public void setToRaise(boolean hasRaised){
        this.hasRaised = hasRaised;
    }

    public int[] bestDeduction(int amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient funds");
            this.setToFold(true);
            return new int[5];
        }

        int[] chips = new int[5];
        if (amount >= 100) {
            chips[4] = amount / 100;
            amount -= chips[4] * 100;
            winnings += chips[4] * 100;
        }
        if (amount >= 50) {
            chips[3] = amount / 50;
            amount -= chips[3] * 50;
            winnings += chips[3] * 50;
        }
        if (amount >= 25) {
            chips[2] = amount / 25;
            amount -= chips[2] * 25;
            winnings += chips[2] * 25;
        }
        if (amount >= 5) {
            chips[1] = amount / 5;
            amount -= chips[1] * 5;
            winnings += chips[1] * 5;
        }
        if (amount >= 1) {
            chips[0] = amount / 1;
            amount -= chips[0] * 1;
            winnings += chips[0];
        }
        return chips;
    }
    public int getWinnings() {
        return winnings;
    }

    public void raise(int amount, Player[] players) {
        if (amount > this.balance) {
            System.out.println("You don't have enough chips to raise this amount.");
            this.setToFold(true);
            return;
        } else {
            int[] deduction = bestDeduction(amount);
            boolean hasEnoughChips = true;

            for (int i = 0; i < deduction.length; i++) {
                if (deduction[i] > this.chips[i]) {
                    hasEnoughChips = false;
                    break;
                }
            }

            if (hasEnoughChips) {
                this.balance -= amount;
                deductChips(deduction);
                this.hasRaised = true;
                this.setToAllIn(false);
                this.setHasToCall(false);
                for (int i = 0; i < players.length; i++) {
                    if (!players[i].getName().equals(this.getName())) {
                        players[i].setHasToCall(true);
                    }
                }
                System.out.println(this.getName() + " has raised " + amount + " chips.");
            } else {
                System.out.println("You don't have the right chip denominations to raise this amount.");
            }
        }
    }
    public void deductChips(int[] deduction) {
        for (int i = 0; i < 5; i++) {
            this.chips[i] -= deduction[i];
        }
    }

    public void allIn(Player[] players){
        this.setToAllIn(true);
        this.hasRaised = true;
        for(int i = 0; i < players.length; i++){
            if(players[i].getName() != this.getName()){
                players[i].setHasToCall(true);
            }
        }
        System.out.println(this.getName() + " is all in with " + this.getBalance() + " chips.");
    }
    public void newRound() {
        this.isFolded = false;
        this.isAllIn = false;
        this.isSmallBlind = false;
        this.hasRaised = false;
        this.isBigBlind = false;
        this.hasToCall = false;
        this.isCurrentPlayer = false;
        this.hand[0] = null;
        this.hand[1] = null;
    }

    public void addBalance(int amount) {
        this.balance += amount;
        int remainingAmount = amount;
        if (remainingAmount >= 100) {
            this.chips[4] += remainingAmount / 100;
            remainingAmount -= (remainingAmount / 100) * 100;
        }
        if (remainingAmount >= 50) {
            this.chips[3] += remainingAmount / 50;
            remainingAmount -= (remainingAmount / 50) * 50;
        }
        if (remainingAmount >= 25) {
            this.chips[2] += remainingAmount / 25;
            remainingAmount -= (remainingAmount / 25) * 25;
        }
        if (remainingAmount >= 5) {
            this.chips[1] += remainingAmount / 5;
            remainingAmount -= (remainingAmount / 5) * 5;
        }
        if (remainingAmount >= 1) {
            this.chips[0] += remainingAmount;
        }
    }

    
}
