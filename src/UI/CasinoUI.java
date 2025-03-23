package UI;

import Core.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CasinoUI {

    private Bank bank;
    private Person player;
    private Bar bar;
    private Scanner scanner;

    public CasinoUI(Person player, Scanner scanner) {
        this.player = player;
        this.bank = new Bank(player);
        this.bar = new Bar();
        this.scanner = scanner;
    }

    public void start() {
        try {
            while (true) {
                System.out.println("Welcome to the Casino! Choose a game or action:");
                System.out.println("1. Play BlackJack");
                System.out.println("2. Play Poker");
                System.out.println("3. Play Roulette");
                System.out.println("4. Play Slots");
                System.out.println("5. Sports Betting");
                System.out.println("6. Order a Drink");
                System.out.println("7. Cash Out");
                System.out.println("8. Convert Money to Chips");
                System.out.println("9. Exit");
                System.out.print("Enter your choice: ");

                String input = scanner.next().toUpperCase();
                if (input.equals("EXIT")) {
                    System.out.println("Exiting the casino... Goodbye.");
                    scanner.close();
                    System.exit(0);
                }

                try {
                    int choice = Integer.parseInt(input);
                    switch (choice) {
                        case 1 -> playBlackJack();
                        case 2 -> playPoker();
                        case 3 -> playRoulette();
                        case 4 -> playSlots();
                        case 5 -> playSportsBetting();
                        case 6 -> orderDrink();
                        case 7 -> cashOut();
                        case 8 -> convertMoneyToChips();
                        case 9 -> {
                            System.out.println("Thanks for playing! Goodbye.");
                            scanner.close();
                            return;
                        }
                        default -> System.out.println("Invalid choice. Try again.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input. Please enter a number from 1 to 9 or type 'EXIT'.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    private void playBlackJack() throws InstanceOverload {
        System.out.print("Enter your bet in dollars or chips: ");
        int betAmount = getValidBet();
        BlackJack blackJack = new BlackJack(player);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        blackJack.play(betAmount);
    }

    private void playPoker() throws InstanceOverload{
        Poker poker = new Poker(player);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        poker.play(poker.getDeck(), poker.getDecks());
    }

    private void playRoulette() throws InstanceOverload{
        Roulette roulette = new Roulette(player);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        roulette.play();
    }

    private void playSlots() throws InstanceOverload{
        System.out.print("Enter your bet in dollars or chips: ");
        int betAmount = getValidBet();
        Slots slots = new Slots(player);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        slots.playWithBetting();
    }

    private void playSportsBetting() throws InstanceOverload{
        SportsBetting sportsBetting = new SportsBetting(player);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        sportsBetting.play();
    }

    private void orderDrink() {
        try {
            bar.order(player);
            System.out.println("You ordered a drink. Enjoy!");
            player.setInebriation(player.getInebriation() + 1);
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
        player.checkInebriation();
    }

    private void cashOut() {
        bank.chipsToMoney();
        System.out.println("You've cashed out successfully! Balance: $" + player.getMoney());
        player.checkNetEarnings();
    }

    private void convertMoneyToChips() {
        System.out.print("Enter the amount of dollars to convert to chips: ");
        int amount = getValidBet();
        try {
            bank.moneyToChips(amount);
            System.out.println("Converted $" + amount + " to chips.");
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    }

    private int getValidBet() {
        int betAmount = 0;
        while (true) {
            try {
                betAmount = scanner.nextInt();
                if (betAmount <= 0) {
                    System.out.print("Invalid bet amount. Please enter a positive value: ");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.next(); // Clear buffer
            }
        }
        return betAmount;
    }

    public static void main (String[] args){
        Scanner scanner = new Scanner(System.in);
        Person player = new Person();
        CasinoUI casinoUI = new CasinoUI(player, scanner);
        casinoUI.start();
    }
}
