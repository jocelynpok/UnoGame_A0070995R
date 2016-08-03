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
public class UnoNumberCard extends UnoCard {
    
    int value;
    
    public UnoNumberCard(int c, int v, int u){
        super(c,u);
        value = v;
    }
    public int getValue(){
        return value;
    }
    
    
     public boolean equals(Object o){
      if(o instanceof UnoNumberCard){
          UnoNumberCard c = (UnoNumberCard) o;
          if(color == c.getColor() && value == c.getValue()){
              return true;
          }
      }
      return false;
  }
      public String toString(){
      return getColorString()+ " "+ value + "UnoNumberCard" + "Uno Card Value of: " + getUnoValue();
      }
      
}



