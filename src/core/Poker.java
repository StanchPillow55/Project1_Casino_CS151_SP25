package core;

import java.util.*;

public class Poker extends Enforcer implements Game {
    private Person player;
    private String[] Deck = new String[52];
    private String[][] Decks = new String[6][52];
    private final String[] Faces = {"J", "Q", "K", "A"};
    private int pot;  // Pot to hold the total bet
    private int initialBet;
    private Scanner scnr;

    public Poker(Person p, Scanner scanner) throws InputMismatchException, InsufficientFunds, InstanceOverload{
        this.player = p;
        this.scnr = scanner;
        this.pot = 0;
        try{
            System.out.print("Enter your initial bet (int): ");
            initialBet = scanner.nextInt();
        }
        catch(InputMismatchException e){
            throw new InputMismatchException("Enter a valid integer!");
        }
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
        for (int i = 36; i < 52; i++) { //fills remaining with face cards
            Deck[i] = "" + Faces[faceCt] + "D";
            Deck[i + 1] = "" + Faces[faceCt] + "C";
            Deck[i + 2] = "" + Faces[faceCt] + "H";
            Deck[i + 3] = "" + Faces[faceCt] + "S";
            i += 3;
            faceCt++; 
        }

        // Fill Decks for multiple decks
        for (int i = 0; i < 6; i++) {
            Decks[i] = Arrays.copyOf(Deck, 52);
        }
    }

    // Start the game by creating a playPoker object
    public void play() {
        int betAmount = initialBet;

        try {
            if (betAmount <= player.getMoney()) {
                player.setMoney(player.getMoney() - betAmount);
            } else if (betAmount <= calculateChipValue(player.getChips())) {
                deductChips(betAmount);
            } else {
                throw new InsufficientFunds("Insufficient funds or chips.");
            }

            pot += betAmount*2; //2x as the dealer or house also has to contribute
            System.out.println("Starting Poker with pot: $" + pot);
            playPoker pokerGame = new playPoker(player, scnr);
            pokerGame.playHand();
        } catch (InsufficientFunds e) {
            System.out.println(e.getMessage());
        } catch (InstanceOverload i){
            System.out.println(i.getMessage());
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

    public Scanner getScnr(){
        return scnr;
    }

    public void setScnr(Scanner scanner){
        scnr = scanner;
    }
}
