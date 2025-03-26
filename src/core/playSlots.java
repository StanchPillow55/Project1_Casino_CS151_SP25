package core;
public class playSlots extends Slots {
    private int betAmount;
    private int accountBalance;

    public playSlots(int betAmount, int accountBalance) throws InstanceOverload{
        super(betAmount, accountBalance);
        this.betAmount = betAmount;
        this.accountBalance = accountBalance;
    }

    @Override
    public void play() {
        double payout = bet(betAmount); 
        accountBalance += payout - betAmount;
        System.out.println("Your Account Balance is: " + accountBalance);
    }

    public String spin() {
        play();
        return toString();
    }

    public double bet() {
        return bet(betAmount);
    }

    public boolean checkCombo(String combo) {
        return winningCombos.containsKey(combo);
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(int accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getbetAmount() {
        return betAmount;
    }

    public void setbetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
