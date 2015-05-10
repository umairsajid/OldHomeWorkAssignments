package hw3;
 

public class HelloWorldTest2
{
	public static void main(String[] args)
	{
		GreetingRunnable runnable1 = new GreetingRunnable("Hello World");
		GreetingRunnable runnable2 = new GreetingRunnable("Goodbye World");
		runnable1.run();
		runnable2.run();
		/*
		Thread thread1 = new Thread(runnable1);	
		Thread thread2 = new Thread(runnable2);	
		thread1.start();
		thread2.start();*/
	    System.out.println("The results of the program has changed because we are no longer using " +
	    "threads. We are now calling a block of code one object than doing the " +
	    "same for another, there is no concurrency");
	}
}
