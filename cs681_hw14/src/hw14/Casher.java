package hw14;
 

public class Casher
{
	public Pizza order()
	{
		System.out.println("An order is made.");
		final Future future = new Future();
		new Thread(future.new KitchenRunnable()).start();
		return future;
	}

	public static void main(String[] args)
	{
		Casher casher = new Casher();
		
		System.out.println("Ordering pizzas at a casher counter.");
		Pizza p1 = casher.order();
		Pizza p2 = casher.order();
		Pizza p3 = casher.order();
		
		System.out.println( "Is the Pizza ready? "+((Future) p1).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p2).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p3).isReady() );

		System.out.println("Doing something, reading newspapers, magazines, etc., " +
				"until pizzas are ready to pick up...");
		try
		{
			Thread.sleep(2000);
		}
		catch(InterruptedException e){}

		System.out.println( "Is the Pizza ready? "+((Future) p1).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p2).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p3).isReady() );
		Long sec = new Long(Long.parseLong("2000"));
		
		System.out.println("Let's see if pizzas are ready to pick up...");
		System.out.println( ((Future)p1).getPizza( sec) );

		System.out.println( "Is the Pizza ready? "+((Future) p1).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p2).isReady() );
		System.out.println( "Is the Pizza ready? "+((Future) p3).isReady() );
		
		System.out.println( p2.getPizza() );
		System.out.println( p3.getPizza() );
	}
	
}
