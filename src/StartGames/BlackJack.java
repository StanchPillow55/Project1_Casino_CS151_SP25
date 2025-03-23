package StartGames;

import java.util.*;

public class BlackJack extends Enforcer implements Game {
    private Person player;
    private String[] Deck = new String[52];
    private String[][] Decks = new String[6][52];
    private final String[] Faces = {"J", "Q", "K", "A"};
    private int initialBet; 

    public BlackJack(Person p) {
        player = p;
        initializeDeck();
    }

    // Initialize the Deck with cards
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
        for (int i = 37; i <= 52; i++) { //fills remaining with face cards
            Deck[i] = "" + Faces[faceCt] + "D";
            Deck[i + 1] = "" + Faces[faceCt] + "C";
            Deck[i + 2] = "" + Faces[faceCt] + "H";
            Deck[i + 3] = "" + Faces[faceCt] + "S";
            i += 3;
            faceCt++; 
        }

        // Fill Decks for multiple decks
        for (int i = 0; i < 6; i++) {
            Decks[i] = Deck;
        }
    }

    // Implement betting system
    public void play(int x) {
        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - x);
            } else if (x <= calculateChipValue(player.getChips())) {
                deductChips(x);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            // Proceed with game logic
            System.out.println("Starting BlackJack with bet: $" + x);
            playBlackJack tmp = new playBlackJack();
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        }
    }

    // Deal two random cards from the deck
    public String[] dealCards() {
        Random rand = new Random();
        String[] dealtCards = new String[2];
        for (int i = 0; i < 2; i++) { // prevent NullPointerException
            int randCard = rand.nextInt(Deck.length); // pick random card
            int randDeck = rand.nextInt(6); // choose random deck (0-5)
            while (Decks[randDeck][randCard] == null) {
                randCard = rand.nextInt(Deck.length); // pick random card
                randDeck = rand.nextInt(6); // choose random deck
            }

            dealtCards[i] = Decks[randDeck][randCard]; // pick prior generated card from random Deck in Decks
            Decks[randDeck][randCard] = null; // ensure that card in that deck cannot be chosen again
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
