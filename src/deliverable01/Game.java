/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deliverable01;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class Game {

    private List<Player> players;
    private List<Card> tablePile;
    private Card.Suit currentSuit;
    private int currentLeadIndex;
    private int highestCardPlayerIndex;

    public Game(List<Player> players) {
        this.players = players;
        this.tablePile = new ArrayList<>();
        this.currentLeadIndex = findAceOfSpadesOwner();
    }

    private int findAceOfSpadesOwner() {
        for (int i = 0; i < players.size(); i++) {
            for (Card c : players.get(i).getHand()) {
                if (c.getSuit() == Card.Suit.SPADES && c.getRank() == Card.Rank.ACE) {
                    return i;
                }
            }
        }
        return 0; // Fallback
    }

    public void playTurn(int playerIndex, Card card) {
        Player player = players.get(playerIndex);
        
        // 1. First card sets the suit and starts as the highest
        if (tablePile.isEmpty()) {
            currentSuit = card.getSuit();
            highestCardPlayerIndex = playerIndex;
            System.out.println("New Round! Suit: " + currentSuit);
        } 
        
        // 2. Rule: THOKA (Wrong suit played)
        else if (card.getSuit() != currentSuit) {
            System.out.println("THOKA! " + player.getName() + " played " + card);
            applyThokaPenalty(player, card);
            return;
        } 
        
        // 3. Rule: Check if this card is the new biggest of the current suit
        else {
            Card highestOnTable = getHighestCardOfSuit(currentSuit);
            if (card.getValue() > highestOnTable.getValue()) {
                highestCardPlayerIndex = playerIndex;
            }
        }

        tablePile.add(card);
        player.removeCard(card);
    }

    // Rule: Person with biggest card gets the whole pile + the Thoka card
    private void applyThokaPenalty(Player thokaGiver, Card thokaCard) {
        Player penalizedPlayer = players.get(highestCardPlayerIndex);
        System.out.println(penalizedPlayer.getName() + " takes the penalty cards!");

        // Takes the pile
        for (Card c : tablePile) {
            penalizedPlayer.addCard(c);
        }
        // Takes the card used to give the thoka
        penalizedPlayer.addCard(thokaCard);
        thokaGiver.removeCard(thokaCard);

        tablePile.clear();
        // Rule: The person who took the cards starts the next round
        currentLeadIndex = highestCardPlayerIndex;
    }

    private Card getHighestCardOfSuit(Card.Suit suit) {
        Card highest = null;
        for (Card c : tablePile) {
            if (c.getSuit() == suit) {
                if (highest == null || c.getValue() > highest.getValue()) {
                    highest = c;
                }
            }
        }
        return highest;
    }

    // Logic to move to next player (skipping finished players)
    public int getNextPlayerIndex(int currentIndex) {
        int next = (currentIndex + 1) % players.size();
        // Skip players with no cards (they are out/safe)
        while (players.get(next).hasNoCards() && !isGameOver()) {
            next = (next + 1) % players.size();
        }
        return next;
    }

    public boolean isGameOver() {
        int activePlayers = 0;
        for (Player p : players) {
            if (!p.hasNoCards()) activePlayers++;
        }
        return activePlayers == 1; // Last one left is the Bhabhi
    }

    public int getCurrentLeadIndex() {
        return currentLeadIndex;
    }
}
