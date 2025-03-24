package core;

import java.util.*;

public class Poker extends Enforcer implements Game {
    private Person player;
    private String[] Deck = new String[52];
    private String[][] Decks = new String[6][52];
    private final String[] Faces = {"J", "Q", "K", "A"};
    private int pot;  // Pot to hold the total bet

    public Poker(Person p) {
        player = p;
        initializeDeck();
    }

    private void initializeDeck() {
        int num = 2;
        for (int i = 0; i <= 36; i++) {
            Deck[i] = "" + num + "D";
            Deck[i + 1] = "" + num + "C";
            Deck[i + 2] = "" + num + "H";
            Deck[i + 3] = "" + num + "S";
            i += 3;
            num++;
        }

        int faceCt = 0;
        for (int i = 37; i <= 52; i++) {
            Deck[i] = "" + Faces[faceCt] + "D";
            Deck[i + 1] = "" + Faces[faceCt] + "C";
            Deck[i + 2] = "" + Faces[faceCt] + "H";
            Deck[i + 3] = "" + Faces[faceCt] + "S";
            i += 3;
            faceCt++; // Fill with face cards
        }

        // Fill Decks for multiple decks
        for (int i = 0; i < 6; i++) {
            Decks[i] = Deck;
        }
    }

    // Start the game by creating a playPoker object
    public void play(String[] d, String[][] ds) {
        System.out.print("Enter your bet in dollars or chips: ");
        Scanner scanner = new java.util.Scanner(System.in);
        int betAmount = scanner.nextInt();
        scanner.close();

        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            pot += betAmount;
            System.out.println("Starting Poker with pot: $" + pot);
            playPoker pokerGame = new playPoker(d, ds);
            pokerGame.playHand();
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
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

    // Getter and setter methods for Deck and Pot
    public String[] getDeck() {
        return Deck;
    }

    public void setDeck(String[] deck) {
        this.Deck = deck;
    }

    public String[][] getDecks() {
        return Decks;
    }

    public void setDecks(String[][] decks) {
        this.Decks = decks;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }
}
