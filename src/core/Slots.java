package core;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

public class Slots extends Enforcer implements Game {
    protected HashMap<String, Double> winningCombos; // hashmap of winning combos
    private Person player; 
    private Random random;
    private char[] winningSymbols;
    private double[] probabilities;

    public Slots() {
        winningCombos = new HashMap<>();
        initializeWinningCombos();
        random = new Random();
        winningSymbols = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        probabilities = new double[] {0.05, 0.1, 0.15, 0.2, 0.25, 0.1, 0.15}; // probabilities for each symbol
    }

    private void initializeWinningCombos() {
        winningCombos.put("AAA", 1000.0);
        winningCombos.put("BBB", 500.0);
        winningCombos.put("CCC", 250.0);
        winningCombos.put("DDD", 100.0);
        winningCombos.put("EEE", 50.0);
        winningCombos.put("FFF", 25.0);
        winningCombos.put("GGG", 10.0);
    }

    private char randomSymbol() {
        double random = this.random.nextDouble(); // Generate a random number between 0.0 and 1.0
        double totalProbabilities = 0.0; 
        for (int i = 0; i < winningSymbols.length; i++) {
            totalProbabilities += probabilities[i];
            if (random <= totalProbabilities) {
                return winningSymbols[i];
            }
        }
    }

    public void play(){
        StringBuilder comboString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            comboString.append(randomSymbol());
        }
        String result = comboString.toString();
        System.out.println(result);
    }

    public double bet(double betAmount) {
        String comboResult = play();
        System.out.println("Result: " + comboResult);
        if (winningCombos.containsKey(comboResult)) {
            double payout = winningCombos.get(comboResult) * betAmount;
            System.out.println("You win: " + payout);
            return payout;
        } else {
            System.out.println("You lose.");
            return 0;
        }
    }

    //alternate betting to support using chips isntead of dollars
    public void playWithBetting() {
        System.out.print("Enter your bet in dollars or chips: ");
        Scanner scanner = new java.util.Scanner(System.in);
        int betAmount = scanner.nextInt();
        scanner.close();
        
        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            playSlots slotsGame = new playSlots(betAmount, player.getMoney());
            slotsGame.play();
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    }

private int calculateChipValue(int[] chips) {
    int[] chipValues = {1, 2, 5, 10, 20, 25, 50, 100};
    int totalValue = 0;
    for (int i = 0; i < chips.length; i++) {
        totalValue += chips[i] * chipValues[i];
    }
    return totalValue;
}

private void deductChips(int amount) {
    int[] chipValues = {1, 2, 5, 10, 20, 25, 50, 100};
    int[] chips = player.getChips();
    int remainingAmount = amount;

    for (int i = chips.length - 1; i >= 0 && remainingAmount > 0; i--) {
        int chipsNeeded = remainingAmount / chipValues[i];
        if (chips[i] >= chipsNeeded) {
            chips[i] -= chipsNeeded;
            remainingAmount -= chipsNeeded * chipValues[i];
        }
    }
    player.setChips(chips);
}

    @Override
    public String toString(){
        return result;  
    }

    public HashMap<String, Double> getWinningCombos() {
        return winningCombos;
    }

    public void setWinningCombos(HashMap<String, Double> winningCombos) {
        this.winningCombos = winningCombos;
    }

    public char[] getWinningSymbols() {
        return winningSymbols;
    }

    public void setWinningSymbols(char[] winningSymbols) {
        this.winningSymbols = winningSymbols;
    }

    public double[] getProbabilities() {
        return probabilities;
    }

    public void setProbabilities(double[] probabilities) {
        this.probabilities = probabilities;
    }
}

