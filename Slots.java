import java.util.HashMap;
import java.util.Random;

public class Slots {
    protected HashMap<String, Double> winningCombos; // hashmap of winning combos 
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
        double random = random.nextDouble(); // Generate a random number between 0.0 and 1.0
        double totalProbabilities = 0.0; 
        for (int i = 0; i < winningSymbols.length; i++) {
            totalProbabilities += probabilities[i];
            if (rand <= totalProbabilities) {
                return winningSymbols[i];
            }
        }
    }

    public void play(){
        StringBuilder comboString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            comboString.append(randomSymbol());
        }
        result = comboString.toString();
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

