package hw11;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InterruptableTask2 implements Runnable
{	
	
	private  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	public void run()
	{
		while( !Thread.interrupted() )
		{	
		 
			lock.writeLock().lock();	
			System.out.println("Lock obtained");
			System.out.println(1);
			System.out.println("Lock released");
		    lock.writeLock().unlock();
			
		}
		System.out.println(4);
	}
	
	public static void main(String[] args)
	{
		Thread t = new Thread(new InterruptableTask2());
		t.start();
		try
		{
			Thread.sleep(2000);
			t.interrupt();
		} catch (InterruptedException e)
		{
		}
	}

}
