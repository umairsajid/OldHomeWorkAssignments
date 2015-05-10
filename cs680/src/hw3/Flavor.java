package hw3;

import java.util.Enumeration;

public class Flavor implements Enumeration<String> {
	
	final static public String VANILLA ="Vanilla"; 
	final static public String CHOCOLATE ="Chocolate"; 
	final static public String STRAWBERRY ="Strawberry"; 
	final static public String LIME ="Lime"; 
	final static public String ORANGE ="Orange";
	private int count= 0;
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		if(count==5) return false;
		else return true;
	}
	@Override
	public String nextElement() {
		// TODO Auto-generated method stub
		count++; 
		switch (count){

		case 1: return Flavor.VANILLA;
		case 2: return Flavor.CHOCOLATE;
		case 3: return Flavor.STRAWBERRY;
		case 4: return Flavor.LIME;
		case 5: return Flavor.ORANGE;
		 
		default: count=0; return Flavor.VANILLA;
		}
		
	
	}
	
	public Flavor(){
		
	}
	
}
 