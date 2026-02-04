package deliverable01;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
//connection check
public class GenerateCards {

    // Create a full 52-card deck using your Card enums
    private List<Card> createDeck() {
        List<Card> deck = new ArrayList<>();

        for (Card.Suit suit : Card.Suit.values()) {
            for (Card.Rank rank : Card.Rank.values()) {
                deck.add(new Card(suit, rank));
            }
        }
        return deck;
    }

    /**
     * Receives the player count from the Main class, 
     * creates the players, and deals the shuffled deck.
     */
    public List<Player> generatePlayersAndDeal(int numPlayers) {
        
        // Create players list based on the input parameter
        List<Player> players = new ArrayList<>();
        for (int i = 1; i <= numPlayers; i++) {
            // Naming players "Player 1", "Player 2", etc.
            players.add(new Player("Player " + i)); 
        }

        // Create and shuffle deck
        List<Card> deck = createDeck();
        Collections.shuffle(deck);

        // Deal cards evenly among the provided number of players
        if (numPlayers > 0) {
            int currentPlayer = 0;
            for (Card card : deck) {
                players.get(currentPlayer).addCard(card);
                currentPlayer = (currentPlayer + 1) % numPlayers;
            }
        }

        return players;
    }
}