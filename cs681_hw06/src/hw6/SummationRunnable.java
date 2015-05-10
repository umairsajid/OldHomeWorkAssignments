package hw6;

import java.util.concurrent.locks.ReentrantReadWriteLock;
 
 
public class SummationRunnable implements Runnable
{
	private int upperBound;
	private boolean done = false;
	private ReentrantReadWriteLock lock;
	
	public SummationRunnable(int n)
	{
		upperBound = n;
		lock = new ReentrantReadWriteLock();
	}
	
	public void setDone()
	{
		done = true;
	}
	
	public void run()
	{
		lock.writeLock().lock();
		System.out.println("Lock obtained");
		try
		{
			while( !done )
			{
				System.out.println(upperBound);
				upperBound--;
				Thread.sleep(1000);
				if( upperBound<0 )
				{
					System.out.println("print done");
					return;
				}
			}
			System.out.println("stopped by main()!");
			done = false;
		} catch (InterruptedException e)
		{
		}finally
		{
			lock.writeLock().unlock();
			System.out.println("Lock released");
		}
	}
	
	public static void main(String[] args)
	{
		SummationRunnable sRunnable = new SummationRunnable(10);
		Thread thread = new Thread(sRunnable);
		thread.start();
		
		for(int i=0; i<5; i++)
		{
			System.out.println("#");
		}
		sRunnable.setDone();
		try
		{
		 
			thread.join();
			new Thread(sRunnable).start();
		} catch (InterruptedException e)
		{
		}
	}
}
