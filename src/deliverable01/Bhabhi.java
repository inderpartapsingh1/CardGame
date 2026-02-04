package deliverable01;
import java.util.List;
import java.util.Scanner;
//Checking Connection
public class Bhabhi {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        GenerateCards generator = new GenerateCards();

        System.out.println("=== Welcome to the Bhabhi Card Game ===");
        
        // 1. Get Number of Players (2 to 26)
        int numPlayers = 0;
        while (numPlayers < 2 || numPlayers > 26) {
            System.out.print("Enter number of players (2-26): ");
            if (input.hasNextInt()) {
                numPlayers = input.nextInt();
                if (numPlayers < 2 || numPlayers > 26) {
                    System.out.println("Invalid number. You need at least 2 players and no more than 26.");
                }
            } else {
                System.out.println("Please enter a numeric value.");
                input.next(); // clear invalid entry
            }
        }

        // 2. Setup Players and Game
        List<Player> players = generator.generatePlayersAndDeal(numPlayers);
        Game game = new Game(players);
        
        System.out.println("\n--- Game Setup Complete ---");
        System.out.println("The player with the Ace of Spades will lead the first round.");

        int currentPlayerIndex = game.getCurrentLeadIndex();

        // 3. Main Game Loop
        while (!game.isGameOver()) {
            Player currentPlayer = players.get(currentPlayerIndex);
            
            // Skip players who have finished their cards
            if (currentPlayer.hasNoCards()) {
                currentPlayerIndex = game.getNextPlayerIndex(currentPlayerIndex);
                continue;
            }

            System.out.println("\n===========================================");
            System.out.println("TURN: " + currentPlayer.getName());
            System.out.println("Hand size: " + currentPlayer.getHand().size());
            System.out.println("-------------------------------------------");
            
            List<Card> hand = currentPlayer.getHand();
            for (int i = 0; i < hand.size(); i++) {
                System.out.println((i + 1) + ") " + hand.get(i));
            }

            // 4. Input Selection
            int choice = -1;
            while (choice < 1 || choice > hand.size()) {
                System.out.print("Select card index to play: ");
                if (input.hasNextInt()) {
                    choice = input.nextInt();
                } else {
                    input.next();
                }
            }

            Card selectedCard = hand.get(choice - 1);
            
            // 5. Execute Turn
            // The playTurn method handles the "Thoka" logic and pile management
            game.playTurn(currentPlayerIndex, selectedCard);

            // 6. Determine Next Player
            // If a Thoka happened, your Game class resets the Lead Index.
            // If not, we just move to the next person in the circle.
            currentPlayerIndex = game.getNextPlayerIndex(currentPlayerIndex);
        }

        // 7. Conclusion
        System.out.println("\n*******************************************");
        System.out.println("             GAME OVER!                   ");
        for (Player p : players) {
            if (!p.hasNoCards()) {
                System.out.println("The loser (Bhabhi) is: " + p.getName());
            }
        }
        System.out.println("*******************************************");
        
        input.close();
    }
}