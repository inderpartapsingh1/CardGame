/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deliverable01;

/**
 *
 * @author admin
 */
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final String name;
    private final List<Card> hand;

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Card> getHand() {
        return hand;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public boolean hasNoCards() {
        return hand.isEmpty();
    }

    public void showHand() {
        for (Card c : hand) {
            System.out.println(c);
        }
    }

    @Override
    public String toString() {
        return "Player: " + name + " (" + hand.size() + " cards)";
    }
}
