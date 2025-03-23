package StartGames;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Roulette extends Enforcer implements Game {
    private int pot; // based on total bet 
    private int[] numbers; // roulette numbers are 0-36 
    private boolean colors; // red or black 

    public void play() {
        playRoulette game = new playRoulette(100); // Initial bet is 100
        game.placeBet(7, 20); // Betting 20 on number 7
        game.placeBet(22, 30); // Betting 30 on number 22
        game.play();
    }

    // pot 
    public int getPot() {
        return pot; 
    }

    public void setPot(int pot) {
        this.pot = pot; 
    }

    // numbers , use setters and getter 
    public int[] getNumbers() {
        return numbers; 
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers; 
    }

    // colors , use setters and getters 
    public boolean getColors() {
        return colors; 
    }

    public void setColors(boolean colors) {
        this.colors = colors; 
    }
}
