package StartGames;

public class BlackJack {
    private String[] Deck;
    private String[][] Decks;
    private int[] pot; //multiple players have diff bets, for naming convention, bets handled in game class instead of person will be referred to as pots

    public void play(){//implement by making object playBlackJack

    }

    public void bet(){//implement betting system

    }
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
    public int[] getPot() {
        return pot;
    }
    public void setPot(int[] pot) {
        this.pot = pot;
    }

    
}
