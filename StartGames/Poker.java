package StartGames;

public class Poker {
    String[] deck;
    String[][] decks; //An array which contains arrays of deck, poker tables often have multiple decks
    int pot; //update with bets
    int sidePot; //create a main and side pot dependent on if someone is all in, whilst others continue to bet
    //check would be if person whos all in wins sidepot, sidePot is only a multiple of how many people bet to that point 

    public void play(){//create a playPoker object

    }

    public void bet(){ //initial bet / amount brought to the table for a session (if refill, reset game)

    }

    public String[] getDeck() {
        return deck;
    }

    public void setDeck(String[] deck) {
        this.deck = deck;
    }

    public String[][] getDecks() {
        return decks;
    }

    public void setDecks(String[][] decks) {
        this.decks = decks;
    }

    public int getPot() {
        return pot;
    }

    public void setPot(int pot) {
        this.pot = pot;
    }

    public int getSidePot() {
        return sidePot;
    }

    public void setSidePot(int sidePot) {
        this.sidePot = sidePot;
    }

}
