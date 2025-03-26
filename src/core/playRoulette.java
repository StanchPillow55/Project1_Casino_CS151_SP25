package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class playRoulette {
    private int initialBet;
    private Map<Integer, Integer> bets; //stores bets
    private Random random;
    private Person player;

    public playRoulette(int initialBet, Person p) throws InstanceOverload {
        this.initialBet = initialBet;
        this.player = p;
        this.bets = new HashMap<Integer, Integer>();
        this.random = new Random();
    }

    public void play(Scanner scanner) {
        System.out.println("You have $" + initialBet + " to place bets.");

        boolean betting = true;
        while (betting && initialBet > 0) {
            try {
                System.out.print("Enter a number to bet on (0-36) or -1 to stop betting: ");
                int number = scanner.nextInt();

                if (number == -1) {
                    betting = false;
                    break;
                }

                if (number < 0 || number > 36) {
                    System.out.println("Invalid number! Please pick a number between 0 and 36.");
                    continue;
                }

                System.out.print("Enter the amount to bet on " + number + ": ");
                int betAmount = scanner.nextInt();

                if (betAmount > initialBet) {
                    throw new InsufficientFunds("You don't have enough money for that bet.");
                }

                bets.put(number, bets.getOrDefault(number, 0) + betAmount);
                initialBet -= betAmount;

                System.out.println("Bet of $" + betAmount + " placed on number " + number);
                System.out.println("Remaining money to bet: $" + initialBet);
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input
            } catch (InsufficientFunds e) {
                System.out.println(e.getMessage());
            }
        }

        // Spin the roulette wheel
        int winningNumber = random.nextInt(37); // 0-36 inclusive
        System.out.println("Roulette spun... Winning number is: " + winningNumber);

        // Calculate winnings
        int winnings = 0;
        if (bets.containsKey(winningNumber)) {
            winnings = bets.get(winningNumber) * 36;
            System.out.println("Congratulations! You won $" + winnings);
        } else {
            System.out.println("Sorry! You lost all your bets.");
        }

        // Return winnings to player's money pool
        player.setMoney(player.getMoney() + winnings);
        System.out.println("Your new balance is: $" + player.getMoney());
    }
}
