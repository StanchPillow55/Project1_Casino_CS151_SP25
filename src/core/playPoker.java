package core;


import java.util.*;

public class playPoker extends Poker {
    private Person player;
    private String[] deck;
    private String[][] decks;
    private ArrayList<String> playerHand;
    private ArrayList<String> dealerHand;
    private Random rand = new Random();
    private int currentBet; // Track the current bet
    private Scanner scanner;

    // Constructor for playPoker
    public playPoker(Person p, Scanner scnr) throws InstanceOverload, InsufficientFunds{
        super(p, scnr); // Pass scanner to the superclass
        scanner = scnr;
        deck = super.getDeck();
        decks = super.getDecks();
        playerHand = new ArrayList<String>();
        dealerHand = new ArrayList<String>();
        currentBet = 0; 
    }

    // Play a single hand of poker
    public void playHand() throws InstanceOverload, InsufficientFunds{
        // Ask the player for an initial bet
        System.out.println("Welcome to Poker! How much would you like to bet?");
        currentBet = getValidBet(); // Get a valid bet from the player

        // Deal 5 cards each to player and dealer
        dealCards(playerHand);
        dealCards(dealerHand);

        // Show player's hand and dealer's first card
        System.out.println("Your cards: " + playerHand);
        System.out.println("Dealer's cards: " + dealerHand.get(0) + " and a hidden card.");

        // Evaluate hands and determine winner
        String playerHandRank = evaluateHand(playerHand);
        String dealerHandRank = evaluateHand(dealerHand);

        System.out.println("Your hand rank: " + playerHandRank);

        // Ask player if they want to raise the bet
        System.out.println("Do you want to raise your bet? Your current bet is " + currentBet);
        System.out.print("Enter the amount to raise (or 0 to keep the bet): ");
        int raiseAmount = super.getScnr().nextInt();
        if (raiseAmount > 0) {
            currentBet += raiseAmount;
            System.out.println("Your new bet is " + currentBet);
        } else {
            System.out.println("You chose not to raise your bet.");
        }

        System.out.println("Dealer's hand rank: " + dealerHandRank);
        String result = compareHands(playerHandRank, dealerHandRank);
        System.out.println("Result: " + result);

        try{
            Poker pokerGame = new Poker(super.getPlayer(), super.getScnr());
            int pot = pokerGame.getPot(); // Get the pot

            if (result.equals("Player wins")) {
            System.out.println("You win! The pot had: $" + pot);
            pokerGame.getPlayer().setMoney(pokerGame.getPlayer().getMoney() + pot);
            } else if (result.equals("Dealer wins")) {
            System.out.println("Dealer wins. All money lost.");
            } else {
            System.out.println("It's a tie! No money lost.");
            pokerGame.getPlayer().setMoney(pokerGame.getPlayer().getMoney() + pot / 2);
            }

            pokerGame.setPot(0);
        }
        catch(InsufficientFunds i){
            throw new InsufficientFunds("Invalid Bet.");
        }
        catch(InstanceOverload o){
            throw new InstanceOverload("Too many objects!!");
        }
        finally{
            System.out.println("Game over!");
        }
    }

    private void dealCards(ArrayList<String> hand) {
        for (int i = 0; i < 5; i++) {
            int randDeck = rand.nextInt(decks.length); // Randomly choose a deck
            int randCard = rand.nextInt(deck.length); // Randomly pick a card from the deck

            // prevent NullPointerException
            while (decks[randDeck][randCard] == null) {
                randCard = rand.nextInt(deck.length);
                randDeck = rand.nextInt(decks.length);
            }

            hand.add(decks[randDeck][randCard]);
            decks[randDeck][randCard] = null; // Remove card from the respective deck
        }
    }

    private String evaluateHand(ArrayList<String> hand) { //separate the value and suit, check for common values and suits (if all suits, flush, # of values = # pairs or kinds)
        HashMap<String, Integer> valueCount = new HashMap<>();
        HashMap<String, Integer> suitCount = new HashMap<>();

        for (String card : hand) {
            String value = card.substring(0, card.length() - 1); // Card value (2-10, J, Q, K, A), grabs whole card so needs substring not charAt()
            String suit = card.substring(card.length() - 1); // Card suit (D, C, H, S)

            // Count the occurrences of each value and suit
            valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
            suitCount.put(suit, suitCount.getOrDefault(suit, 0) + 1);
        }

        boolean isFlush = suitCount.containsValue(5);

        List<Integer> cardValues = new ArrayList<>();
        for (String cardValue : valueCount.keySet()) {
            cardValues.add(getCardValue(cardValue));
        }
        Collections.sort(cardValues);

        boolean isStraight = isStraight(cardValues);

        int pairs = 0;
        int threeOfAKind = 0;
        int fourOfAKind = 0;
        for (int count : valueCount.values()) {
            if (count == 4) {
                fourOfAKind++;
            } else if (count == 3) {
                threeOfAKind++;
            } else if (count == 2) {
                pairs++;
            }
        }

        if (isFlush && isStraight) {
            return "Straight Flush";
        } else if (fourOfAKind == 1) {
            return "Four of a Kind";
        } else if (threeOfAKind == 1 && pairs == 1) {
            return "Full House";
        } else if (isFlush) {
            return "Flush";
        } else if (isStraight) {
            return "Straight";
        } else if (threeOfAKind == 1) {
            return "Three of a Kind";
        } else if (pairs == 2) {
            return "Two Pairs";
        } else if (pairs == 1) {
            return "One Pair";
        } else {
            return "High Card";
        }
    }

    // Helper method to get card value as an integer
    private int getCardValue(String card) {
        switch (card) {
            case "A":
                return 14; 
            case "K":
                return 13; 
            case "Q":
                return 12; 
            case "J":
                return 11; 
            case "10":
                return 10;
            case "9":
                return 9;
            case "8":
                return 8;
            case "7":
                return 7;
            case "6":
                return 6;
            case "5":
                return 5;
            case "4":
                return 4;
            case "3":
                return 3;
            case "2":
                return 2;
            default:
                return 0;
        }
    }

    // Helper method to check if a list of card values form a straight
    private boolean isStraight(List<Integer> values) {
        if (values.size() != 5) return false;

        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) != values.get(i - 1) + 1) {
                return false;
            }
        }
        return true;
    }

    // Method to compare hands based on hand ranks
    private String compareHands(String playerHandRank, String dealerHandRank) {
        // Poker hand ranking order
        List<String> handRanks = Arrays.asList("High Card", "One Pair", "Two Pairs", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush");

        int playerRank = handRanks.indexOf(playerHandRank);
        int dealerRank = handRanks.indexOf(dealerHandRank);

        if (playerRank > dealerRank) {
            return "Player wins";
        } else if (dealerRank > playerRank) {
            return "Dealer wins";
        } else {
            return "It's a tie";
        }
    }

    // Helper method to get a valid bet from the player
    private int getValidBet() {
        int bet = 0;
        while (true) {
            try {
                bet = scanner.nextInt();
                if (bet <= 0) {
                    System.out.println("Bet must be greater than 0. Try again:");
                } else {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the bet.");
                scanner.next(); // Clear the buffer
            }
        }
        return bet;
    }
}
