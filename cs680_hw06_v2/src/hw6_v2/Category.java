package hw6_v2;

 

import java.util.Enumeration;

public class Category implements Enumeration<String> {
	
	final static public String INSTATE ="InState"; 
	final static public String OUTSTATE ="OutState"; 
	final static public String INTL ="International"; 
	 
	private int count= 0;
	@Override
	public boolean hasMoreElements() {
		// TODO Auto-generated method stub
		if(count==3) return false;
		else return true;
	}
	@Override
	public String nextElement() {
		// TODO Auto-generated method stub
		count++; 
		switch (count){

		case 1: return Category.INSTATE;
		case 2: return Category.OUTSTATE;
		case 3: return Category.INTL;
		 
		 
		default: count=0; return Category.INSTATE;
		}
		
	
	}
	
	public Category(){
		
	}
	
}