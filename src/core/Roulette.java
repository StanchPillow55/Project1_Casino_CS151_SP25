package core;

import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Roulette extends Enforcer implements Game {
    private Person player;
    private int pot;
    private int[] numbers; 
    private boolean colors;
    private int initialBet;
    private Scanner scnr;

    public Roulette(Person p, Scanner scanner) throws InsufficientFunds, InstanceOverload, InputMismatchException {
        this.player = p;
        this.scnr = scanner;
        numbers = new int[37];
        for (int i = 0; i <= 36; i++) {
            numbers[i] = i;
        }

        System.out.print("Enter your initial bet (int): ");
        try {
            initialBet = scanner.nextInt();
            if (initialBet > player.getMoney() && initialBet > calculateChipValue(player.getChips())) {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            throw new InputMismatchException("Enter a valid integer!");
        }
    }

    @Override
    public void play(){
        try {
            if (initialBet <= player.getMoney()) {
                player.setMoney(player.getMoney() - initialBet);
            } else if (initialBet <= calculateChipValue(player.getChips())) {
                deductChips(initialBet);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            pot += initialBet; 
            System.out.println("Starting Roulette with pot: $" + pot);

            playRoulette rouletteGame = new playRoulette(initialBet, player);
            rouletteGame.play(scnr);
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
            System.out.println("Would you like to adjust your bet or exit? (A/E): ");
        } catch(InstanceOverload i){
            System.out.println("Too many objects!!");
            System.exit(0);
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

    public int getPot() { return pot; }
    public void setPot(int pot) { this.pot = pot; }

    public int[] getNumbers() { return numbers; }
    public void setNumbers(int[] numbers) { this.numbers = numbers; }

    public boolean getColors() { return colors; }
    public void setColors(boolean colors) { this.colors = colors; }

    public int getInitialBet() { return initialBet; }
    public void setInitialBet(int x) { initialBet = x; }

    public void setPlayer(Person p) { player = p; }
    public Person getPlayer() { return player; }
}
