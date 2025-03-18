package casino;

import java.util.*;

public class SportsBetting {
    private Matchup currentMatchup;
    private int betAmount;
    private String selectedTeam; // The team the player chooses to bet on

    // Map of sport to list of teams/players (8 per sport)
    private Map<String, List<String>> teamsMap;
    
    // List of current matchups (4 matchups) generated for a sport
    private List<Matchup> currentMatchups;


    public SportsBetting() {
        this.betAmount = 0;
        teamsMap = new HashMap<>();

        teamsMap.put("mlb", Arrays.asList(
            "New York Yankees", "Los Angeles Dodgers", "Boston Red Sox",
            "San Francisco Giants", "Chicago Cubs", "St. Louis Cardinals",
            "Atlanta Braves", "Houston Astros"
        ));
        
        teamsMap.put("tennis", Arrays.asList(
            "Novak Djokovic", "Rafael Nadal", "Roger Federer",
            "Andy Murray", "Stan Wawrinka", "Dominic Thiem",
            "Alexander Zverev", "Stefanos Tsitsipas"
        ));
        
        teamsMap.put("nba", Arrays.asList(
            "Los Angeles Lakers", "Boston Celtics", "Golden State Warriors",
            "Chicago Bulls", "Miami Heat", "San Antonio Spurs",
            "Toronto Raptors", "Milwaukee Bucks"
        ));
        
        teamsMap.put("nfl", Arrays.asList(
            "Kansas City Chiefs", "New England Patriots", "Green Bay Packers",
            "San Francisco 49ers", "Pittsburgh Steelers", "Baltimore Ravens",
            "Seattle Seahawks", "New Orleans Saints"
        ));
    }
    
    // Generating KINDA realistic odds using a random probability for team1 (between 30% and 70%)
    // and applying a 5% vig (margin for our casino). This creates odds such that even matchups yield roughly 1.90 each.
    
    // Also I am using decimal odds instead of american because it's easier to work with, this shouldn't matter much just mentioning it
    private double[] generateOdds() {
        double margin = 1.05; // 
        Random rand = new Random();
        double p = 0.3 + 0.4 * rand.nextDouble(); // team1's probability between 0.3 and 0.7
        double oddsTeam1 = Math.round((1 / (p * margin)) * 100.0) / 100.0;
        double oddsTeam2 = Math.round((1 / ((1 - p) * margin)) * 100.0) / 100.0;
        return new double[] { oddsTeam1, oddsTeam2 };
    }
    
    // Generating 4 matchups from a list of 8 teams by shuffling and pairing them
    private List<Matchup> generateMatchups(String sport, List<String> teams) {
        List<Matchup> matchups = new ArrayList<>();
        List<String> teamsCopy = new ArrayList<>(teams);
        Collections.shuffle(teamsCopy);
        for (int i = 0; i < teamsCopy.size(); i += 2) {
            String team1 = teamsCopy.get(i);
            String team2 = teamsCopy.get(i + 1);
            double[] odds = generateOdds();
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
            currentMatchup = null;
            return;
        }
        if (teamsMap.containsKey(input)) {
            List<String> teams = teamsMap.get(input);
            currentMatchups = generateMatchups(input, teams);
            
            // Displaying the 4 matchups
            System.out.println("Available matchups for " + input + ":");
            for (int i = 0; i < currentMatchups.size(); i++) {
                Matchup m = currentMatchups.get(i);
                System.out.println((i + 1) + ". " + m.getTeam1() + " (odds: " + m.getOddsTeam1() + ") vs " +
                    m.getTeam2() + " (odds: " + m.getOddsTeam2() + ")");
            }
            System.out.print("Enter the number of the matchup you want to bet on (1-4) or type 'menu' to return: ");
            String choiceInput = sc.nextLine();
            if (choiceInput.toLowerCase().equals("menu")) {
                System.out.println("Returning to main menu...");
                currentMatchup = null;
                return;
            }
            int choice;
            try {
                choice = Integer.parseInt(choiceInput);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Returning to main menu.");
                currentMatchup = null;
                return;
            }
            if (choice < 1 || choice > currentMatchups.size()) {
                System.out.println("Invalid matchup choice. Exiting betting for this round.");
                currentMatchup = null;
                return;
            }
            currentMatchup = currentMatchups.get(choice - 1);
        } else {
            System.out.println("Sport not recognized. Exiting betting for this round.");
            currentMatchup = null;
        }
    }
    
    public void chooseTeam() {
        if (currentMatchup == null) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Which team do you want to bet on? (Type 'menu' to return)");
        System.out.println("1. " + currentMatchup.getTeam1() + " (odds: " + currentMatchup.getOddsTeam1() + ")");
        System.out.println("2. " + currentMatchup.getTeam2() + " (odds: " + currentMatchup.getOddsTeam2() + ")");
        System.out.print("Enter 1 or 2: ");
        String choice = sc.nextLine();
        if (choice.toLowerCase().equals("menu")) {
            System.out.println("Returning to main menu...");
            selectedTeam = null;
            currentMatchup = null;
            return;
        }
        if (choice.equals("1")) {
            selectedTeam = currentMatchup.getTeam1();
        } else if (choice.equals("2")) {
            selectedTeam = currentMatchup.getTeam2();
        } else {
            System.out.println("Invalid choice. Defaulting to " + currentMatchup.getTeam1());
            selectedTeam = currentMatchup.getTeam1();
        }
        System.out.println("You have chosen to bet on " + selectedTeam);
    }
    
    public void placeBet(Person player) throws InsufficientFunds {
        if (currentMatchup == null || selectedTeam == null) {
            return;
        }
        Scanner sc = new Scanner(System.in);
        System.out.print("How many chips would you like to bet? (or type 'menu' to return): ");
        String input = sc.nextLine();
        if (input.toLowerCase().equals("menu")) {
            System.out.println("Returning to main menu...");
            return;
        }
        int bet;
        try {
            bet = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Returning to main menu.");
            return;
        }
        if (bet <= 0) {
            System.out.println("Bet must be greater than zero.");
            return;
        }
        if (player.getChips() < bet) {
            throw new InsufficientFunds("You do not have enough chips to place that bet!");
        }
        player.setChips(player.getChips() - bet);
        this.betAmount = bet;
        System.out.println("You placed a bet of " + bet + " chips on " + selectedTeam);
    }
    
    public void resolveBet(Person player) {
        if (currentMatchup == null || selectedTeam == null) {
            return;
        }
        Random rand = new Random();
        double winProbability;
        if (selectedTeam.equals(currentMatchup.getTeam1())) {
            winProbability = currentMatchup.getProbabilityTeam1();
        } else {
            winProbability = currentMatchup.getProbabilityTeam2();
        }
        double outcome = rand.nextDouble();
        if (outcome < winProbability) {
            double odds = selectedTeam.equals(currentMatchup.getTeam1()) ? currentMatchup.getOddsTeam1() : currentMatchup.getOddsTeam2();
            int winnings = (int) (betAmount * odds);
            player.setChips(player.getChips() + winnings);
            System.out.println("You WON! " + selectedTeam + " won! You earned " + winnings + " chips!");
        } else {
            System.out.println("You lost your bet of " + betAmount + " chips. " + selectedTeam + " lost.");
        }
    }
    
    public void play(Person player) {
        chooseSport();
        if (currentMatchup == null) {
            return;
        }
        chooseTeam();
        if (selectedTeam == null) {
            return;
        }
        try {
            placeBet(player);
            resolveBet(player);
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    }
}
