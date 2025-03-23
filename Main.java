public class Main {
    public static void main(String[] args){
        playSlots game = new playSlots(10, 100.0); // Set initial amounts before spinning
        System.out.println("Account Balance: " + game.getAccountBalance());
        game.play();
        System.out.println("Updated Account Balance: " + game.getAccountBalance());
    }
}