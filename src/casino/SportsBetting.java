package casino;

import java.util.Random;
import java.util.Scanner;

public class SportsBetting {
    
    private String chosenSport;
    private int betAmount;

    public SportsBetting() {
        this.chosenSport = "";
        this.betAmount = 0;
    }

    public void chooseSport() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a sport to bet on: "); // likely doing nba / nfl/ tennis
        this.chosenSport = sc.nextLine();
        System.out.println("You chose: " + this.chosenSport);
    }

    public void placeBet(Person player) throws InsufficientFunds {
        Scanner sc = new Scanner(System.in);
        System.out.print("How many chips would you like to bet? ");
        int bet = sc.nextInt();

        // checking player's chips
        if (player.getChips() < bet) {
            throw new InsufficientFunds("You do not have enough chips to place that bet!");
        }

        // deducting from player
        player.setChips(player.getChips() - bet);
        this.betAmount = bet;
        System.out.println("You placed a bet of " + bet + " chips on " + this.chosenSport);
    }

    public void resolveBet(Person player) {
        Random rand = new Random();
        boolean didWin = rand.nextBoolean(); // 50% chance / win or loss

        if (didWin) {
            int winnings = this.betAmount * 2; 
            player.setChips(player.getChips() + winnings);
            System.out.println("You WON! You earned " + winnings + " chips!");
        } else {
            System.out.println("You lost your bet of " + this.betAmount + " chips.");
        }
    }

    public void play(Person player) {
        try {
            chooseSport();
            placeBet(player);
            resolveBet(player);
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    }
    
    // still working
    // might make odds more complex and realistic, this is just a start
    // as of now it's just 50 / 50
}
