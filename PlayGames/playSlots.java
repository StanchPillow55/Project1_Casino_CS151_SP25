public class playSlots extends Slots {
    private int betAmount;
    private double accountBalance;

    public playSlots(int betAmount, double accountBalance) {
        super();
        this.betAmount = betAmount;
        this.accountBalance = accountBalance;
    }

    @Override
    public String play() {
        double payout = bet(betAmount);
        accountBalance += payout - betAmount;
        return accountBalance;
    }

    public String spin() {
        return play();
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

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getbetAmount() {
        return betAmount;
    }

    public void setbetAmount(int betAmount) {
        this.betAmount = betAmount;
    }
}
