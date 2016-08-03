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
public class UnoCardProjectNew_E0015073 {

   public static final int COLOR_GREEN = 1;
    public static final int COLOR_YELLOW = 2;
     public static final int COLOR_RED = 3;
     public static final int COLOR_BLUE = 4;
     public static final int COLOR_WILD = 5;
     public static final int COLOR_WILD4 = 6;
     public static final int ACTION_D2 =14;
     public static final int ACTION_SKIP =13;
     public static final int ACTION_REV =12;
     public static final int NUM_CARD_HAND=7;
     public static UnoCardDeck deck;
     private String status;
     private int gameId;
     public static ArrayList<UnoCardPlayer> players;
     public static UnoCard prevCard;
    
    // private int cardOnHand;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        UnoCardProjectNew_E0015073 test = new UnoCardProjectNew_E0015073(0,"started");
    }
//   public static void makeDeck(){
//      //  public static int makeDeck(){
//        deck = new UnoCardDeck();
//        System.out.println("A new deck is made");
//        addCardsToDeck(COLOR_GREEN);
//        addCardsToDeck(COLOR_YELLOW );
//        addCardsToDeck(COLOR_RED);
//        addCardsToDeck(COLOR_BLUE);
//        for(int n = 0; n< 4; n++){ 
//         deck.addCard(new UnoCard(COLOR_WILD));
//         deck.addCard(new UnoCard(COLOR_WILD4));
//        }
//        deck.shuffle();
//        
//        
//    }
//    public static void addCardsToDeck(int c){
//        for (int i = 0; i<2; i++){
//            for(int n = 0; n<10;n++){
//                deck.addCard(new UnoNumberCard(c,n));
//            }
//            deck.addCard(new UnoActionCard(c,ACTION_D2));
//            deck.addCard(new UnoActionCard(c,ACTION_SKIP));
//            deck.addCard(new UnoActionCard(c,ACTION_REV));
//        }
//    }
    public static void makeDeck(){
      //  public static int makeDeck(){
        deck = new UnoCardDeck();
        System.out.println("A new deck is made");
        addCardsToDeck(COLOR_GREEN);
        addCardsToDeck(COLOR_YELLOW );
        addCardsToDeck(COLOR_RED);
        addCardsToDeck(COLOR_BLUE);
        deck.addCard(new UnoNumberCard(COLOR_GREEN,0,0));
        deck.addCard(new UnoNumberCard(COLOR_YELLOW,0,0));
        deck.addCard(new UnoNumberCard(COLOR_RED,0,0));
        deck.addCard(new UnoNumberCard(COLOR_BLUE,0,0));
        for(int n = 0; n< 4; n++){ 
         deck.addCard(new UnoCard(COLOR_WILD,50));
         deck.addCard(new UnoCard(COLOR_WILD4,50));
         
        }
        deck.shuffle();
        System.out.println(deck.getSize());
        
    }
    public static void addCardsToDeck(int c){
        for (int i = 0; i<2; i++){
            for(int n = 1; n<10;n++){
                deck.addCard(new UnoNumberCard(c,n,n));
            }
            deck.addCard(new UnoActionCard(c,ACTION_D2,20));
            deck.addCard(new UnoActionCard(c,ACTION_SKIP,20));
            deck.addCard(new UnoActionCard(c,ACTION_REV,20));
        }
    }
    public UnoCardProjectNew_E0015073(int id, String status ){
        this.status = status;
        this.gameId = id;
        //this.freeDeck = 108;
        System.out.println("Game id:" + gameId + " "+ "Game status: " + this.status );
        makeDeck();
        makePlayers(5);
        prevCard = deck.drawCard();
        System.out.println("Discard: " + prevCard);
        System.out.println("Cards remaining in deck: " + (deck.getSize()));
    }
    
//    public void makePlayers(int p){
//        players = new ArrayList<UnoCardPlayer>(p);
//        
//        UnoCardPlayer c = null;
//       
//        for(int j = 0; j< p;j++){
//             c = new UnoCardPlayer(j);
//        for (int i = 0; i<NUM_CARD_HAND;i++ ){
//            c.addCard(deck.drawCard());
//        
//        }
//    players.add(c);
//        }   
//        for(UnoCardPlayer ply:players){
//         System.out.println(ply);
//         System.out.println(ply.getHand());
//         //cardOnHand += ply.gethandSize();
//        }
//    }
     public void makePlayers(int p){
        players = new ArrayList<UnoCardPlayer>(p);
        
        UnoCardPlayer c = null;
       
        for(int j = 0; j< p;j++){
             c = new UnoCardPlayer(j);
        for (int i = 0; i<NUM_CARD_HAND;i++ ){
            c.addCard(deck.drawCard());
        
        }
    players.add(c);
        }   
        for(UnoCardPlayer ply:players){
         System.out.println(ply);
         System.out.println(ply.getHand());
         //cardOnHand += ply.gethandSize();
        }
    }
            
}
