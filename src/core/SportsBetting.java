// Source code is decompiled from a .class file using FernFlower decompiler.
package core;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class SportsBetting extends Enforcer implements Game{
   private Person player;
   private Matchup currentMatchup;
   private int betAmount = 0;
   private String selectedTeam;
   private Map<String, List<String>> teamsMap = new HashMap();
   private List<Matchup> currentMatchups;

   public SportsBetting(Person p) {
      player = p;
      this.teamsMap.put("mlb", Arrays.asList("New York Yankees", "Los Angeles Dodgers", "Boston Red Sox", "San Francisco Giants", "Chicago Cubs", "St. Louis Cardinals", "Atlanta Braves", "Houston Astros"));
      this.teamsMap.put("tennis", Arrays.asList("Novak Djokovic", "Rafael Nadal", "Roger Federer", "Andy Murray", "Stan Wawrinka", "Dominic Thiem", "Alexander Zverev", "Stefanos Tsitsipas"));
      this.teamsMap.put("nba", Arrays.asList("Los Angeles Lakers", "Boston Celtics", "Golden State Warriors", "Chicago Bulls", "Miami Heat", "San Antonio Spurs", "Toronto Raptors", "Milwaukee Bucks"));
      this.teamsMap.put("nfl", Arrays.asList("Kansas City Chiefs", "New England Patriots", "Green Bay Packers", "San Francisco 49ers", "Pittsburgh Steelers", "Baltimore Ravens", "Seattle Seahawks", "New Orleans Saints"));
   }

   private double[] generateOdds() {
      double margin = 1.05;
      Random rand = new Random();
      double p = 0.3 + 0.4 * rand.nextDouble();
      double oddsTeam1 = (double)Math.round(1.0 / (p * margin) * 100.0) / 100.0;
      double oddsTeam2 = (double)Math.round(1.0 / ((1.0 - p) * margin) * 100.0) / 100.0;
      return new double[]{oddsTeam1, oddsTeam2};
   }

   private List<Matchup> generateMatchups(String sport, List<String> teams) {
      List<Matchup> matchups = new ArrayList();
      List<String> teamsCopy = new ArrayList(teams);
      Collections.shuffle(teamsCopy);

      for(int i = 0; i < teamsCopy.size(); i += 2) {
         String team1 = (String)teamsCopy.get(i);
         String team2 = (String)teamsCopy.get(i + 1);
         double[] odds = this.generateOdds();
         Matchup matchup = new Matchup(sport, team1, odds[0], team2, odds[1]);
         matchups.add(matchup);
      }

      return matchups;
   }

   public void chooseSport() {
      Scanner sc = new Scanner(System.in);
      System.out.print("Enter a sport to bet on (mlb, tennis, nba, nfl) or type 'menu' to return: ");
      String input = sc.nextLine().toLowerCase();
      if (input.equals("menu")) {
         System.out.println("Returning to main menu...");
         this.currentMatchup = null;
      } else {
         if (!this.teamsMap.containsKey(input)) {
            System.out.println("Sport not recognized. Exiting betting for this round.");
            this.currentMatchup = null;
         } else {
            List<String> teams = (List)this.teamsMap.get(input);
            this.currentMatchups = this.generateMatchups(input, teams);
            System.out.println("Available matchups for " + input + ":");

            for(int i = 0; i < this.currentMatchups.size(); ++i) {
               Matchup m = (Matchup)this.currentMatchups.get(i);
               System.out.println(i + 1 + ". " + m.getTeam1() + " (odds: " + m.getOddsTeam1() + ") vs " + m.getTeam2() + " (odds: " + m.getOddsTeam2() + ")");
            }

            System.out.print("Enter the number of the matchup you want to bet on (1-4) or type 'menu' to return: ");
            String choiceInput = sc.nextLine();
            if (choiceInput.toLowerCase().equals("menu")) {
               System.out.println("Returning to main menu...");
               this.currentMatchup = null;
               return;
            }

            int choice;
            try {
               choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException var7) {
               System.out.println("Invalid input. Returning to main menu.");
               this.currentMatchup = null;
               return;
            }

            if (choice < 1 || choice > this.currentMatchups.size()) {
               System.out.println("Invalid matchup choice. Exiting betting for this round.");
               this.currentMatchup = null;
               return;
            }

            this.currentMatchup = (Matchup)this.currentMatchups.get(choice - 1);
         }

      }
   }

   public void chooseTeam() {
      if (this.currentMatchup != null) {
         Scanner sc = new Scanner(System.in);
         System.out.println("Which team do you want to bet on? (Type 'menu' to return)");
         PrintStream var10000 = System.out;
         String var10001 = this.currentMatchup.getTeam1();
         var10000.println("1. " + var10001 + " (odds: " + this.currentMatchup.getOddsTeam1() + ")");
         var10000 = System.out;
         var10001 = this.currentMatchup.getTeam2();
         var10000.println("2. " + var10001 + " (odds: " + this.currentMatchup.getOddsTeam2() + ")");
         System.out.print("Enter 1 or 2: ");
         String choice = sc.nextLine();
         if (choice.toLowerCase().equals("menu")) {
            System.out.println("Returning to main menu...");
            this.selectedTeam = null;
            this.currentMatchup = null;
         } else {
            if (choice.equals("1")) {
               this.selectedTeam = this.currentMatchup.getTeam1();
            } else if (choice.equals("2")) {
               this.selectedTeam = this.currentMatchup.getTeam2();
            } else {
               System.out.println("Invalid choice. Defaulting to " + this.currentMatchup.getTeam1());
               this.selectedTeam = this.currentMatchup.getTeam1();
            }

            System.out.println("You have chosen to bet on " + this.selectedTeam);
         }
      }
   }

   public void bet(int number, int amount) throws InsufficientFunds {
      if (this.currentMatchup != null && this.selectedTeam != null) {
         Scanner sc = new Scanner(System.in);
         System.out.print("How many chips would you like to bet? (or type 'menu' to return): ");
         String input = sc.nextLine();
         if (input.toLowerCase().equals("menu")) {
            System.out.println("Returning to main menu...");
         } else {
            int bet;
            try {
               bet = Integer.parseInt(input);
            } catch (NumberFormatException var6) {
               System.out.println("Invalid input. Returning to main menu.");
               return;
            }

            if (bet <= 0) {
               System.out.println("Bet must be greater than zero.");
            } else if (player.getChips() < bet) {
               throw new InsufficientFunds("You do not have enough chips to place that bet!");
            } else {
               player.setChips(player.getChips() - bet);
               this.betAmount = bet;
               System.out.println("You placed a bet of " + bet + " chips on " + this.selectedTeam);
            }
         }
      }
   }

   public void resolveBet(Person player) {
      if (this.currentMatchup != null && this.selectedTeam != null) {
         Random rand = new Random();
         double winProbability;
         if (this.selectedTeam.equals(this.currentMatchup.getTeam1())) {
            winProbability = this.currentMatchup.getProbabilityTeam1();
         } else {
            winProbability = this.currentMatchup.getProbabilityTeam2();
         }

         double outcome = rand.nextDouble();
         if (outcome < winProbability) {
            double odds = this.selectedTeam.equals(this.currentMatchup.getTeam1()) ? this.currentMatchup.getOddsTeam1() : this.currentMatchup.getOddsTeam2();
            int winnings = (int)((double)this.betAmount * odds);
            player.setChips(player.getChips() + winnings);
            System.out.println("You WON! " + this.selectedTeam + " won! You earned " + winnings + " chips!");
         } else {
            System.out.println("You lost your bet of " + this.betAmount + " chips. " + this.selectedTeam + " lost.");
         }

      }
   }

   public void play() {
        System.out.print("Enter your bet in dollars or chips: ");
        int betAmount = new Scanner(System.in).nextInt();
        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            this.betAmount = betAmount;
            System.out.println("Starting Sports Betting with bet: $" + betAmount);
            super.play(player);

        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
      this.chooseSport();
      if (this.currentMatchup != null) {
         this.chooseTeam();
         if (this.selectedTeam != null) {
            try {
               this.placeBet(player);
               this.resolveBet(player);
            } catch (InsufficientFunds var3) {
               System.out.println(var3.getMessage());
            }

         }
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
}
