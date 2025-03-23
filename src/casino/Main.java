package casino;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Person player = new Person("Govil", 100);
        boolean exit = false;

        while (!exit) {
            System.out.println("\n=== Casino Main Menu ===");
            System.out.println("1. Sports Betting");
          //System.out.println("2. Casino"); @stanchpillow I need help here, this is just to test but we can probably edit in the actual main class?
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.nextLine();

            switch(choice) {
                case "1":
                    SportsBetting sb = new SportsBetting();
                    sb.play(player);
                    System.out.println("Remaining chips: " + player.getChips());
                    break;
                case "2":
                    exit = true;
                    System.out.println("Exiting Casino. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select 1 or 2.");
                    break;
            }
        }
        sc.close();
    }
}
