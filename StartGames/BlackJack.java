package StartGames;

import java.util.*;

public class BlackJack extends Enforcer implements Game {
    private Person player;
    private String[] Deck = new String[52];
    private String[][] Decks = new String[6][52];
    private final String[] Faces = {"J", "Q", "K", "A"};
    private int initialBet; // Correctly declared

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

    // Start the game by constructing a playBlackJack class (separated because code was cumbersome)
    public void play(int x) {
        initialBet = x;
        try {
            bet(initialBet);
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
            return;
        }
        playBlackJack tmp = new playBlackJack();
    }

    // Implement betting system
    public void bet(int x) throws InsufficientFunds {
        if (player.getMoney() < x) {
            throw new InsufficientFunds("Cannot bet that much!");
        }
        // Deduct bet amount from player's balance
        player.setMoney(player.getMoney() - x);
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
