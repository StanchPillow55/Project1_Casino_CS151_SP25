package core;

import java.io.PrintStream;
import java.util.*;

public class SportsBetting extends Enforcer implements Game {
    private Person player;
    private Matchup currentMatchup;
    private int betAmount = 0;
    private String selectedTeam;
    private final Map<String, List<String>> teamsMap;
    private List<Matchup> currentMatchups;
    private Scanner sc;

    public SportsBetting(Person p, Scanner scanner) throws InstanceOverload{
        this.player = p;
        this.sc = scanner;
        this.teamsMap = initializeTeams();
    }

    private Map<String, List<String>> initializeTeams() {
        Map<String, List<String>> teams = new HashMap<>();
        teams.put("mlb", Arrays.asList("New York Yankees", "Los Angeles Dodgers", "Boston Red Sox", "San Francisco Giants", "Chicago Cubs", "St. Louis Cardinals", "Atlanta Braves", "Houston Astros"));
        teams.put("tennis", Arrays.asList("Novak Djokovic", "Rafael Nadal", "Roger Federer", "Andy Murray", "Stan Wawrinka", "Dominic Thiem", "Alexander Zverev", "Stefanos Tsitsipas"));
        teams.put("nba", Arrays.asList("Los Angeles Lakers", "Boston Celtics", "Golden State Warriors", "Chicago Bulls", "Miami Heat", "San Antonio Spurs", "Toronto Raptors", "Milwaukee Bucks"));
        teams.put("nfl", Arrays.asList("Kansas City Chiefs", "New England Patriots", "Green Bay Packers", "San Francisco 49ers", "Pittsburgh Steelers", "Baltimore Ravens", "Seattle Seahawks", "New Orleans Saints"));
        return teams;
    }

    private double[] generateOdds() {
        Random rand = new Random();
        double margin = 1.05;
        double p = 0.3 + 0.4 * rand.nextDouble();
        return new double[]{roundOdds(1.0 / (p * margin)), roundOdds(1.0 / ((1.0 - p) * margin))};
    }

    private double roundOdds(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

    private List<Matchup> generateMatchups(String sport, List<String> teams) {
        List<Matchup> matchups = new ArrayList<>();
        List<String> teamsCopy = new ArrayList<>(teams);
        Collections.shuffle(teamsCopy);

        for (int i = 0; i < teamsCopy.size(); i += 2) {
            Matchup matchup = new Matchup(sport, teamsCopy.get(i), generateOdds()[0], teamsCopy.get(i + 1), generateOdds()[1]);
            matchups.add(matchup);
        }
        return matchups;
    }

    public void chooseSport() {
        System.out.print("Enter a sport to bet on (mlb, tennis, nba, nfl) or type 'menu' to return: ");
        String input = sc.next().toLowerCase();
        if (!teamsMap.containsKey(input)) {
            System.out.println("Sport not recognized. Exiting betting for this round.");
            currentMatchup = null;
            return;
        }
        currentMatchups = generateMatchups(input, teamsMap.get(input));
        displayMatchups();
        System.out.print("Select a matchup by number or type 'menu' to return: ");
        String choice = sc.next();
        if (choice.equalsIgnoreCase("menu")) {
            currentMatchup = null;
            return;
        }

        try {
            int matchupIndex = Integer.parseInt(choice) - 1;
            if (matchupIndex < 0 || matchupIndex >= currentMatchups.size()) {
                System.out.println("Invalid selection. Returning to main menu.");
                currentMatchup = null;
            } else {
                currentMatchup = currentMatchups.get(matchupIndex);
                System.out.println("You selected: " + currentMatchup.getTeam1() + " vs " + currentMatchup.getTeam2());
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to main menu.");
            currentMatchup = null;
        }
    }

    private void displayMatchups() {
        System.out.println("Available matchups:");
        for (int i = 0; i < currentMatchups.size(); i++) {
            Matchup m = currentMatchups.get(i);
            System.out.println((i + 1) + ". " + m.getTeam1() + " (odds: " + m.getOddsTeam1() + ") vs " + m.getTeam2() + " (odds: " + m.getOddsTeam2() + ")");
        }
    }

    public void chooseTeam() {
        if (currentMatchup == null) {return;}
        System.out.println("1. " + currentMatchup.getTeam1() + " (odds: " + currentMatchup.getOddsTeam1() + ")");
        System.out.println("2. " + currentMatchup.getTeam2() + " (odds: " + currentMatchup.getOddsTeam2() + ")");
        System.out.print("Enter 1 or 2: ");

        String choice = sc.next();
        selectedTeam = ("1".equals(choice)) ? currentMatchup.getTeam1() : currentMatchup.getTeam2();
        System.out.println("You have chosen to bet on " + selectedTeam);
    }

    public void bet() throws InsufficientFunds {
        if (currentMatchup == null || selectedTeam == null) {return;}
        System.out.print("How many chips would you like to bet? (or type 'menu' to return): ");

        try {
            betAmount = sc.nextInt();   
            if (betAmount <= 0 || calculateChipValue(player.getChips()) < betAmount) {
                throw new InsufficientFunds("Insufficient chips for that bet.");
            }
            deductChips(calculateChipValue(player.getChips()) - betAmount);
            System.out.println("You placed a bet of " + betAmount + " chips on " + selectedTeam);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to main menu.");
        }
    }

    public void resolveBet() {
        if (currentMatchup == null || selectedTeam == null) return;
        double winProbability = selectedTeam.equals(currentMatchup.getTeam1())
                ? currentMatchup.getProbabilityTeam1()
                : currentMatchup.getProbabilityTeam2();
        int winnings = (int) (betAmount * winProbability);
        deductChips((calculateChipValue(player.getChips()) + winnings));
        System.out.println(winnings > 0 ? "You WON!" : "You lost your bet.");
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

    @Override
    public void play() {
        chooseSport();
        chooseTeam();
        if (currentMatchup != null) {
            chooseTeam();
            try {
                bet();
                resolveBet();
            } catch (InsufficientFunds e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
