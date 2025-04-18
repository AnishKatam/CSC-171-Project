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
    }
    public String getName(){
        return name;
    }
    public int[] getChips(){
        return chips;
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
    public int[] bestDeduction(int amount){
        int[] chips = new int[5];
        if(amount >= 100){
            chips[4] = amount / 100;
            amount -= chips[4] * 100;
        }
        if(amount >= 50){
            chips[3] = amount / 50;
            amount -= chips[3] * 50;
        }
        if(amount >= 25){
            chips[2] = amount / 25;
            amount -= chips[2] * 25;
        }
        if(amount >= 5){
            chips[1] = amount / 5;
            amount -= chips[1] * 5;
        }
        if(amount >= 1){
            chips[0] = amount / 1;
            amount -= chips[0] * 1;
        }
        return chips;
    }
    
    public void raise(int amount, Player[] players){
        if(amount > this.balance){
            System.out.println("You don't have enough chips to raise this amount.");
        } else {
            this.balance -= amount;
            this.chips = bestDeduction(amount);
            this.hasRaised = true;
            this.setToAllIn(false);
            for(int i = 0; i < players.length; i++){
                if(players[i].getName() != this.getName()){
                    players[i].setHasToCall(true);
                }
            }
            System.out.println(this.getName() + " has raised " + amount + " chips.");
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

    


    
}
