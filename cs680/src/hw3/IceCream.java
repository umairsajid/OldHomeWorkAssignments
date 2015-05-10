package hw3;


public class IceCream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Flavor scoop1Flavor = new Flavor();
		Topping []toppings ;
		
		System.out.println("The flavor for scoop 1 is " + scoop1Flavor.nextElement());
		
		toppings = new Topping[2];
		
		toppings[0] = new Topping();
		toppings[1] = new Topping();
		
		String Scoop1Topping1= new String(toppings[0].nextElement());
		
		String Scoop1Topping2= new String(toppings[1].nextElement());
		
		Scoop1Topping2=new String(toppings[1].nextElement());
		
		System.out.println("The toppings for scoop 1 are " + Scoop1Topping1 + " and " + Scoop1Topping2 );
		
		System.out.println("The price for scoop 1 is 2.50" );
	
		Cream scoop1 = new Cream(scoop1Flavor,toppings,(float) 2.50);
		
		
		Cream scoop2 = new Cream(scoop1Flavor,toppings,(float) 2.50,scoop1);
		
		System.out.println("The same scoop was added on top" );

		
	}

}
