/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unocardprojectnew_e0015073;

/**
 *
 * @author jocelyn
 */
public class UnoActionCard extends UnoCard {
    int action;
    public UnoActionCard(int c, int a, int u){
        super(c,u);
        action = a;
    }
    public int getAction(){
        return action;
    }
     public boolean equals(Object o){
      if(o instanceof UnoActionCard){
          UnoActionCard c = (UnoActionCard) o;
          if(color == c.getColor() && action == c.getAction()){
              return true;
          }
      }
      return false;
  }
     public String toString(){
         String value = " ";
         switch(action){
             case UnoCardProjectNew_E0015073.ACTION_D2:
                 value = "Draw 2";
             case UnoCardProjectNew_E0015073.ACTION_SKIP:
                 value = "Skip";   
             case UnoCardProjectNew_E0015073.ACTION_REV:
                 value = "Reverse";  
                 break;
         }
         return getColorString()+ " " + value + "ActionCard" + "Uno Card Value of: " + getUnoValue();
    }
}