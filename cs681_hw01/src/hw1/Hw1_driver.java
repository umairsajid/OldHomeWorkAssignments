package hw1;

public class Hw1_driver
{
	
	
	
	public static void main(String[] args)
	{
		GreetingRunnable runnable1 = new GreetingRunnable("Hello World");
		Thread thread1 = new Thread(runnable1);
		thread1.start();
		
	    MyThread thr = new MyThread();
	    thr.run();
	    
	
	}

}
