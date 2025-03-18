package StartGames;

public class Roulette {
    private int pot; // based on total bet 
    private int[] numbers; // roulette numbers are 0 -36 
    private boolean colors; // red or black 

    public void play () { // create playRoulette object
        
    }
    
    public void bet () { // bet system
        
    }
    
    // pot 
    public int getPot() {
        return pot; 
    }

    public void setPot(int pot) {
        this.pot = pot; 
    }
    
    // numbers 
    public int[] getNumbers () {
        return numbers; 
    }

    public void setNumbers(int[] numbers) {
        this.numbers = numbers; 
    }
    
    // colors
    public boolean getColors() {
        return colors; 
    }

    public void setColors(boolean colors) {
        this.colors = colors; 
    }

}
