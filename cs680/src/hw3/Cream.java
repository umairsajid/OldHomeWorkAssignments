package hw3;



public class Cream {
	
  private Flavor f;

  private Topping[] toppings;

  private float price;

      public Cream down;
      public Flavor myFlavor;
    

  public void setFlavor(Flavor fl) {
	  f = fl;
  }

  public void setToppings(Topping[] t) {
	  toppings = t;
  }

  public Cream (Flavor fl, Topping[] to, float pr)
  {
	  f = fl;
	  toppings = to;
	  price = pr;
  } 
  public Cream (Flavor fl, Topping[] to, float pr, Cream below)
  {
	  f = fl;
	  toppings = to;
	  price = pr;
	  down = below;
	  
  }
 
}