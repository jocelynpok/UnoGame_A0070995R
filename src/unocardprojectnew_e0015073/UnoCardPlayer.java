/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unocardprojectnew_e0015073;

import java.util.ArrayList;

/**
 *
 * @author jocelyn
 */
public class UnoCardPlayer {
    int id;
    ArrayList<UnoCard>hand;
    int score;
    
    public UnoCardPlayer(int i){
        id = i;
        score = 0;
        hand = new ArrayList<UnoCard>(7);
    }
    public int getId(){
        return id;
    }
    public void addCard(UnoCard c){
        hand.add(c);
    }
    public void subtractCard(UnoCard c){    
        for (int i = 0; i< hand.size();i++){
            if(c.equals(hand.get(i))){
                hand.remove(i);
                i = hand.size();
            }
        }
    }
    public int gethandSize(){
        return hand.size();
    }
    public boolean hasInHand(UnoCard c){
        return hand.contains(c);
    }
    public ArrayList<UnoCard> getHand(){
        return hand;
    }
    public int getScore(){
        return score;
    
    }
    public void addScore(int i){
     score += i;  
    }
    public String toString(){
        return "Player Id:" + getId();
    }
}
