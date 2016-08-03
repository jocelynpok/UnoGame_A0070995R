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
public class UnoCard {
//    public enum Color{RED,BLUE,GREEN,YELLOW}
//    private Color color;
//    public UnoCard ( Color color)
//  {
//   
//    this.color = color;
//  }
//     public Color getColor()
//  {
//    return color;
//  }
// 
//  public void setColor(Color c)
//  {
//    this.color = c;
//  }
    int color;
    int unoValue;
     public UnoCard ( int color, int unoValue)
  {
   
    this.color = color;
    this.unoValue = unoValue;
  }
     public int getColor()
  {
    return color;
  }
     public int getUnoValue()
  {
    return unoValue;
  }
  public boolean equals(Object o){
      if(o instanceof UnoCard){
          UnoCard c = (UnoCard) o;
          if(color == c.getColor()){
              return true;
          }
      }
      return false;
  }
  public String getColorString(){
      switch(color){
          case UnoCardProjectNew_E0015073.COLOR_GREEN:
              return "Green";
            case UnoCardProjectNew_E0015073.COLOR_RED:
              return "Red"; 
               case UnoCardProjectNew_E0015073.COLOR_BLUE:
              return "Blue";
               case UnoCardProjectNew_E0015073.COLOR_YELLOW:
              return "Yellow";
               case UnoCardProjectNew_E0015073.COLOR_WILD:
              return "Wild";
               case UnoCardProjectNew_E0015073.COLOR_WILD4:
              return "Wild Draw 4";
      }
      return "Color";
      }
  public String toString(){
      return getColorString() + "Card" + "Uno Card Value of: " + getUnoValue();
  }
}
