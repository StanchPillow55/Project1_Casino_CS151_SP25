package StartGames;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Roulette extends Enforcer implements Game {
    private int pot; // based on total bet, represents initialBet but pot cuz can spread out across table
    private int[] numbers; // roulette numbers are 0-36 
    private boolean colors; // red or black 

    public void play() {
        System.out.print("Enter your bet in dollars or chips: ");
        int betAmount = new java.util.Scanner(System.in).nextInt();

        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            pot += betAmount;
            System.out.println("Starting Roulette with pot: $" + pot);
            playRoulette rouletteGame = new playRoulette(betAmount);
            rouletteGame.play();
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    // pot 
    public int getPot() {
        return pot; 
    }

    public void setPot(int pot) {
        this.pot = pot; 
    }

    // numbers , use setters and getter 
    public int[] getNumbers() {
        return numbers; 
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers; 
    }

    // colors , use setters and getters 
    public boolean getColors() {
        return colors; 
    }

    public void setColors(boolean colors) {
        this.colors = colors; 
    }
}
