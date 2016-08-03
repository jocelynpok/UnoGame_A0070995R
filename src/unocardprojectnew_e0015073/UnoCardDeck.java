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
public class UnoCardDeck {
    ArrayList<UnoCard> pile;
    ArrayList<UnoCard> cards;
    public UnoCardDeck(ArrayList<UnoCard>c){
        cards = c;
    }
    public UnoCardDeck(){
        cards = new ArrayList<UnoCard>(108);
    }
    public void addCard(UnoCard c){
        cards.add(c);
    }
    public void shuffle(){
        ArrayList<UnoCard>c = new ArrayList(108);
        while(cards.size()>0){
            c.add(cards.remove((int)(cards.size()* Math.random())));
        }
        cards = c;
    }
    public UnoCard drawCard(){
        if(cards.size()== 0){
            UnoCardProjectNew_E0015073 .makeDeck();
        }
        return cards.remove(0);
    }
    public UnoCard peek(){
        return cards.get(0);
    }
    public void split(){
        split((int)((Math.random()*(cards.size() - 10))+ 10));
    }
    public void split(int c){
     ArrayList<UnoCard> tmp = (ArrayList)cards.subList(0, c);
     cards.removeAll(tmp);
     cards.addAll(tmp);
    }
    public void played(UnoCard c){
        pile.add(c);
    }
    public boolean canHave(UnoCard c){
        return cards.contains(c);
    }
    public int getSize(){
        
     return cards.size();
    }
}
