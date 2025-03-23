package StartGames;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class playRoulette extends Roulette {
    private int initialBet;
    private Map<Integer, Integer> bets; // Stores number-to-bet mappings

    public playRoulette(int initialBet) {
        this.initialBet = initialBet;
        this.bets = new HashMap<>();
        setPot(initialBet);  // Initialize pot with the starting bet
    }

    // Implement betting system
    public void placeBet(int number, int amount) throws new InsufficientFunds{
        if (amount <= getPot()) {
            bets.put(number, amount);
            setPot(getPot() - amount);
        } else {
            throws new InsufficientFunds("Cannot place bet");
        }
    }

    // Play logic with roulette spin
    @Override
    public void play() {
        Random rand = new Random();
        int winningNumber = rand.nextInt(37); // Roulette has numbers 0-36
        System.out.println("Winning number: " + winningNumber);

        // Check for wins
        if (bets.containsKey(winningNumber)) {
            int winnings = bets.get(winningNumber) * 36; // Standard roulette payout
            setPot(getPot() + winnings);
            System.out.println("You won! New pot: " + getPot());
        } else {
            System.out.println("No wins this round. Remaining pot: " + getPot());
        }

        // Clear bets for the next round
        bets.clear();
    }
}
