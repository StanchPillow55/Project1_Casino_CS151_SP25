package StartGames;

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
        playPoker pokerGame = new playPoker(d, ds);
        pokerGame.playHand(); 
    }

   
    public void bet(int amount) throws InsufficientFunds {
        if (player.getMoney() < amount) {
            throw new InsufficientFunds("Cannot bet that much!");
        }
        // Deduct the bet amount from the player's balance and add to the pot
        player.setMoney(player.getMoney() - amount);
        pot += amount;
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
