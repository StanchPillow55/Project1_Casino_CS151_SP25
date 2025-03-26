package core;

import java.util.*;

public class BlackJack extends Enforcer implements Game {
    private Person player;
    private String[] Deck = new String[52];
    private String[][] Decks = new String[6][52];
    private final String[] Faces = {"J", "Q", "K", "A"};
    private int initialBet;
    private Scanner scnr;

    public BlackJack(Person p, Scanner scanner) throws InputMismatchException, InsufficientFunds, InstanceOverload {
        player = p;
        scnr = scanner;
        System.out.print("Enter your initial bet (int): ");
        try {
            initialBet = scanner.nextInt();
            if (initialBet <= 0) {
                throw new InsufficientFunds("Enter a valid positive bet!");
            }
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            throw new InputMismatchException("Enter a valid integer!");
        }
        initializeDeck();
    }

    // Initialize the Deck with cards
    private void initializeDeck() {
        int num = 2;
        for (int i = 0; i < 36; i += 4) {
            Deck[i] = "" + num + "D";
            Deck[i + 1] = "" + num + "C";
            Deck[i + 2] = "" + num + "H";
            Deck[i + 3] = "" + num + "S";
            num++;
        }

        int faceCt = 0;
        for (int i = 36; i < 52; i += 4) {
            Deck[i] = Faces[faceCt] + "D";
            Deck[i + 1] = Faces[faceCt] + "C";
            Deck[i + 2] = Faces[faceCt] + "H";
            Deck[i + 3] = Faces[faceCt] + "S";
            faceCt++; 
        }

        // Fill Decks for multiple decks with unique copies
        for (int i = 0; i < 6; i++) {
            Decks[i] = Arrays.copyOf(Deck, Deck.length);
        }
    }

    // Implement betting system
    public void play() {
        try {
            if (initialBet <= player.getMoney()) {
                player.setMoney(player.getMoney() - initialBet);
            } else if (initialBet <= calculateChipValue(player.getChips())) {
                deductChips(initialBet);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }
    
            System.out.println("Starting BlackJack with bet: $" + initialBet);
            new playBlackJack(player, scnr); // Pass player and scanner
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        } catch (InstanceOverload i) {
            System.out.println(i.getMessage());
        }
    }

    // Deal two random cards from the deck
    public String[] dealCards() {
        Random rand = new Random();
        String[] dealtCards = new String[2];
        for (int i = 0; i < 2; i++) {
            int randCard;
            int randDeck;

            do {
                randCard = rand.nextInt(Deck.length); 
                randDeck = rand.nextInt(6); 
            } while (Decks[randDeck][randCard] == null); 

            dealtCards[i] = Decks[randDeck][randCard]; 
            Decks[randDeck][randCard] = null; // Prevent reuse
        }
        return dealtCards;
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
    
    // Getter and setter methods
    public String[] getDeck() {
        return Deck;
    }

    public void setDeck(String[] deck) {
        Deck = deck;
    }

    public String[][] getDecks() {
        return Decks;
    }

    public void setDecks(String[][] decks) {
        Decks = decks;
    }

    public int getInitialBet() {
        return initialBet;
    }

    public void setInitialBet(int x) {
        initialBet = x;
    }

    public void setPlayer(Person p) {
        player = p;
    }

    public Person getPlayer() {
        return player;
    }
}
