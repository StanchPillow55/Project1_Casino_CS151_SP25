package core;

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

    public void start() throws InsufficientFunds, InstanceOverload, InputMismatchException{
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
                catch (InstanceOverload e){
                    System.out.println("Too many instances.");
                }
            }
        } finally {
            scanner.close();
        }
    }

    private void playBlackJack() throws InstanceOverload, InsufficientFunds, InputMismatchException {
        BlackJack blackJack = new BlackJack(player, scanner);
        player.setInebriation(player.getInebriation() + 1); // Drinking effect for playing games
        blackJack.play();
    }

    private void playPoker() throws InstanceOverload, InsufficientFunds, InputMismatchException{
        Poker poker = new Poker(player, scanner);
        player.setInebriation(player.getInebriation() + 1); 
        poker.play();
    }

    private void playRoulette() throws InstanceOverload, InsufficientFunds, InputMismatchException{
        Roulette roulette = new Roulette(player, scanner);
        player.setInebriation(player.getInebriation() + 1); 
        roulette.play();
    }

    private void playSlots() throws InstanceOverload, InsufficientFunds, InputMismatchException{
        Slots slots = new Slots(player, scanner);
        player.setInebriation(player.getInebriation() + 1); 
        slots.playWithBetting(scanner);
    }

    private void playSportsBetting() throws InstanceOverload, InsufficientFunds, InputMismatchException{
        SportsBetting sportsBetting = new SportsBetting(player, scanner);
        player.setInebriation(player.getInebriation() + 1); 
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

    public static void main(String[] args) throws InsufficientFunds, InstanceOverload, InputMismatchException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your name: ");
        String name = scanner.next(); 
        Person player = new Person(name);
        System.out.println("Note: Chips are as follows:\n"
        + "White chips – $1.\n"
        + "Yellow chips – $2 (rarely used nowadays)\n"
        + "Red chips – $5.\n"
        + "Blue chips – $10.\n"
        + "Gray chips – $20.\n"
        + "Green chips – $25.\n"
        + "Orange chips – $50.\n"
        + "Black chips – $100.\n");
        CasinoUI casinoUI = new CasinoUI(player, scanner);
        try{
            casinoUI.start();
        }
        catch(InsufficientFunds i){
            throw new InsufficientFunds("Enter a valid bet");
        }
        catch(InstanceOverload o){
            throw new InstanceOverload("Too many objects!");
        }
        catch(InputMismatchException m){
            throw new InputMismatchException("Wrong input. Try again!");
        }
    }
}
