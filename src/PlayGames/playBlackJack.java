import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class playBlackJack extends BlackJack {
    // Handle the main Blackjack game logic
    public playBlackJack() {
        // Dealer and player initial cards
        ArrayList<String> dealerCards = new ArrayList<String>(Arrays.asList(dealCards()));
        ArrayList<String> playerCards = new ArrayList<String>(Arrays.asList(dealCards()));

        // Display initial cards
        System.out.println("Dealer's cards: " + dealerCards.get(0) + " and hidden card.");
        System.out.println("Your cards: " + playerCards.get(0) + " and " + playerCards.get(1));

        int dealerCt = evaluateCards(dealerCards);
        int playerCt = evaluateCards(playerCards);

        String gameStatus = determineWinner(dealerCt, playerCt);

        // Player's turn (hit or stand)
        Scanner scanner = new Scanner(System.in);
        while (gameStatus.equals("C")) {
            System.out.print("Do you want to (h)it or (s)tand? ");
            String choice = scanner.nextLine();
            if (choice.equals("h")) {
                // Deal a card to the player
                String newCard = dealCards()[0];
                System.out.println("You drew: " + newCard);

                // Add the new card to the player's hand
                playerCards.add(newCard);

                System.out.println("Your cards: " + String.join(", ", playerCards));
            } else if (choice.equals("s")) {
                break;
            }
            dealerCt = evaluateCards(dealerCards);
            playerCt = evaluateCards(playerCards);
            gameStatus = determineWinner(dealerCt, playerCt);
        }

        if (dealerCt == playerCt) { // tie means return bet
            Person player = getPlayer();
            int x = player.getInitialBet();
            player.setMoney(player.getMoney() + x);
        } else if (dealerCt < playerCt) {
            while (dealerCt < playerCt && gameStatus.equals("C")) {
                String newCard = dealCards()[0];
                dealerCards.add(newCard);
                dealerCt = evaluateCards(dealerCards);
                gameStatus = determineWinner(dealerCt, playerCt); // draw till win or bust
            }
        } else {
            System.out.println("Dealer wins!");
        }

        // handle money distribution
        Person player = getPlayer();
        int x = player.getInitialBet();

        switch (gameStatus) {
            case "W":
                player.setMoney(player.getMoney() + 2 * x);
                System.out.println("You won!");
                System.out.println("Your current balance: " + player.getMoney());
                break;

            case "L":
                System.out.println("You lost!");
                System.out.println("Your current balance: " + player.getMoney());
                break;

            case "T":
                System.out.println("You drew! No money lost!");
                System.out.println("Your current balance: " + player.getMoney());
                break;
        }

        // Check if the player busted
        System.out.println("Your final hand: " + String.join(", ", playerCards));

        // Dealer's turn (draw until 17 or higher)
        System.out.println("Dealer's turn...");
        ArrayList<String> dealerFinalCards = new ArrayList<String>(Arrays.asList(dealCards()));
        System.out.println("Dealer's cards: " + dealerFinalCards.get(0) + " and " + dealerFinalCards.get(1));

        // Example comparison to declare a winner (based on card count for simplicity)
        // A more complex comparison based on actual card values can be done
        System.out.println("Game over!");
    }

    public int evaluateCards(ArrayList<String> cards) throws InputMismatchException {
        int sum = 0;
        for (String card : cards) {
            switch (card.substring(0, 1)) { // Get the first character of the card (Ace, 2, 3, etc.)
                case "A":
                    if (sum + 11 > 21) {
                        sum += 1;
                    } else {
                        sum += 11;
                    }
                    break;
                case "K":
                case "Q":
                case "J":
                case "10":
                    sum += 10;
                    break;
                case "9":
                    sum += 9;
                    break;
                case "8":
                    sum += 8;
                    break;
                case "7":
                    sum += 7;
                    break;
                case "6":
                    sum += 6;
                    break;
                case "5":
                    sum += 5;
                    break;
                case "4":
                    sum += 4;
                    break;
                case "3":
                    sum += 3;
                    break;
                case "2":
                    sum += 2;
                    break;
                default:
                    throw new InputMismatchException("Invalid Card");
            }
        }
        return sum;
    }

    public String determineWinner(int dealerCt, int playerCt) {
        if (dealerCt == playerCt && playerCt == 21) {
            return "T"; // Tie if both have 21
        } else if (dealerCt == 21) {
            return "L"; // Dealer wins with 21
        } else if (playerCt == 21) {
            return "W"; // Player wins with 21
        } else if (playerCt > 21) {
            return "L"; // Player busts
        } else if (dealerCt > 21) {
            return "W"; // Dealer busts
        } else {
            return "C"; // Continue the game
        }
    }
}