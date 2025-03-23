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
    public void chooseGame(String s){
        //implement using try/catch and Class.forName(className);
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
