package hw3;

import java.util.Enumeration;

public class Topping implements Enumeration<String> {

	final static public String NUTS ="Nuts"; 
	final static public String EXTRACHOCOLATE ="ExtraChocolate"; 
	final static public String STRAWBERRYJEERRY ="StrawberryJeerry"; 
	private int count= 0;
	@Override
	public boolean hasMoreElements() {
		 
		if(count==3) return false;
		else return true;
	}

	@Override
	public String nextElement() { 
		count++; 
		switch (count){

		case 1: return Topping.NUTS;
		case 2: return Topping.EXTRACHOCOLATE;
		case 3: return Topping.STRAWBERRYJEERRY;
	 	default: count=0; return Topping.NUTS;
		}
		
	}
	
	public Topping(){
		
	}

}
