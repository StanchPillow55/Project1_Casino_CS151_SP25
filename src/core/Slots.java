package core;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Slots extends Enforcer implements Game { 
    protected HashMap<String, Double> winningCombos;
    private Person player; 
    private Random random;
    private char[] winningSymbols;
    private double[] probabilities; 
    private int initialBet;
    private int comboResult;

    public Slots(int betAmount, int accountBalance) throws InstanceOverload{
        initialBet = betAmount;
        player.setMoney(accountBalance); //used for sanity check
    }

    public Slots(Person p, Scanner scanner) throws InsufficientFunds, InstanceOverload {
        player = p;
        System.out.print("Enter your initial bet (int): ");
        if (scanner.hasNextInt()) {
            initialBet = scanner.nextInt();
        } else {
            throw new IllegalArgumentException("Invalid bet value. Please enter an integer.");
        }

        winningCombos = new HashMap<>();
        initializeWinningCombos();
        random = new Random();
        winningSymbols = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G'};
        probabilities = new double[] {0.05, 0.1, 0.15, 0.2, 0.25, 0.1, 0.15};
        validateProbabilities();
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
        double randValue = random.nextDouble();
        double totalProbabilities = 0.0;
        for (int i = 0; i < winningSymbols.length; i++) {
            totalProbabilities += probabilities[i];
            if (randValue <= totalProbabilities) {
                return winningSymbols[i];
            }
        }
        return winningSymbols[winningSymbols.length - 1];
    }

    @Override
    public void play() {
        for (int i = 0; i < 3; i++) {
            comboResult += randomSymbol(); 
        }
    }

    public int bet(int amount) {
        play(); 
        System.out.println("Result: " + comboResult);
        if (winningCombos.containsKey(comboResult)) {
            int payout = (int)(winningCombos.get(comboResult) * amount);
            System.out.println("You win: $" + payout);
            return payout;
        } else {
            System.out.println("You lose.");
            return 0;
        }
    }

    public void playWithBetting(Scanner scanner) {
        System.out.print("Enter your bet in dollars or chips: ");
        int betAmount = scanner.nextInt();

        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            int winnings = bet(betAmount);
            player.setMoney(player.getMoney() + winnings);
            System.out.println("Your new balance is: $" + player.getMoney());
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            scanner.nextLine(); // Clear invalid input
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
            if (chips[i] > 0) {
                int usedChips = Math.min(chipsNeeded, chips[i]);
                chips[i] -= usedChips;
                remainingAmount -= usedChips * chipValues[i];
            }
        }
        player.setChips(chips);
    }

    private void validateProbabilities() {
        double total = 0;
        for (double p : probabilities) total += p;
        if (Math.abs(total - 1.0) > 0.01) {
            throw new IllegalArgumentException("Probabilities must add up to 1.0");
        }
    }

    @Override
    public String toString() {
        return "Slots Game - Winning Combos: " + winningCombos;
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
        validateProbabilities();
    }

    public int getInitialBet() {
        return initialBet;
    }

    public void setInitialBet(int x) {
        initialBet = x;
    }

    public void setPlayer(Person p) {
        player = p;
    }

    public Person getPlayer() {
        return player;
    }
}
