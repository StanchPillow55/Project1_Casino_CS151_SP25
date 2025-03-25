// Source code is decompiled from a .class file using FernFlower decompiler.
package core;

import java.util.Scanner;

public class Main {
   public Main() {
   }

   public static void main(String[] args) {
      Scanner sc = new Scanner(System.in);
      Person player = new Person("Govil");
      boolean exit = false;

      while(true) {
         while(!exit) {
            System.out.println("\n=== Casino Main Menu ===");
            System.out.println("1. Sports Betting");
            System.out.println("2. Exit");
            System.out.print("Enter your choice: ");
            String choice = sc.next();
            switch (choice) {
               case "1":
                  SportsBetting sb = new SportsBetting();
                  sb.play(player);
                  System.out.println("Remaining chips: " + player.getChips());
                  break;
               case "2":
                  exit = true;
                  System.out.println("Exiting Casino. Goodbye!");
                  break;
            }

            System.out.println("Invalid choice. Please select 1 or 2.");
         }

         sc.close();
         return;
      }
   }
}
