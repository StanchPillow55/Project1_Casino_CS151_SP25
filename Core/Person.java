package Core;
public class Person extends Enforcer{
    private String name;
    private int money;
    private int netEarnings; //winnings on the night, only updates on cashout
    private int inebriation; //# drinks delivered to this person throughout session, can add functionality for sobering up
    private int[] chips = new int[8]; //array of chips ordered w/ amounts of respective colors, can implement sorting helper method in bank
    private String[] chipColors = {"W", "Y", "R", "B", "Gry", "Grn", "O", "B"}; //matching array of colors to convert color input to matching chip output
    /*White chips – $1.
    Yellow chips – $2 (rarely used nowadays)
    Red chips – $5.
    Blue chips – $10.
    Gray chips – $20.
    Green chips – $25.
    Orange chips – $50.
    Black chips – $100. */
    private int amountBet;

    public Person(){
        money = 1000; //must go to bank
        netEarnings = 0; //1,000,000+ net earnings = kicked out for cheating
        inebration = 0; //+1 for every drink, all drinks assumed to be standard, 6+ = kicked out
        for(int i = 0; i < chips.length; i++){
            chips[i] = 0;
        }
    }

    public void chooseGame(String gameName) {
        try {
            Game selectedGame = (Game) Class.forName("StartGames." + gameName).getDeclaredConstructor().newInstance();
            selectedGame.play();
        } catch (Exception e) {
            System.out.println("Invalid game selection. Please try again.");
        }
    }

    @Override
    public boolean checkExit(String input) {
        if (input.toUpperCase().equals("EXIT")) {
            System.out.println("Exiting the casino... Goodbye!");
            System.exit(0);
        }
        return false;
    }

    @Override
    public void countInstances() throws InstanceOverload {
        if (getInstanceCount() > 100) {
            throw new InstanceOverload("Too many game instances. Terminating...");
        }
    }

    @Override
    public void checkNetEarnings() {
        if (getNetEarnings() > 1000000) {
            System.out.println("Net earnings exceed the limit. Suspected cheating detected. Exiting...");
            System.exit(0);
        }
    }

    @Override
    public void checkInebriation() {
        if (getInebriation() > 5) {
            System.out.println("You are too inebriated to continue. Exiting the casino...");
            System.exit(0);
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { //make a read that calls this
        this.name = name;
    }
    
    public int getNetEarnings() {
        return netEarnings;
    }

    public void setNetEarnings(int netEarnings) {
        this.netEarnings = netEarnings;
    }

    public int getInebriation() {
        return inebriation;
    }

    public void setInebriation(int inebriation) {
        this.inebriation = inebriation;
    }

    public int[] getChips() {
        return chips;
    }

    public void setChips(int[] chips) {
        this.chips = chips;
    }

    public String[] getChipColors() {
        return chipColors;
    }

    public void setChipColors(String[] chipColors) {
        this.chipColors = chipColors;
    }

    public int getAmountBet() {
        return amountBet;
    }

    public void setAmountBet(int amountBet) {
        this.amountBet = amountBet;
    }

    public int getMoney(){
        return money;
    }

    public void setMoney(int x){
        money = x;
    }
}
