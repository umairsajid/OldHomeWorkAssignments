package example_3_10_11;

import java.util.concurrent.locks.ReentrantLock;

public final class VolatileLatch implements Runnable
{
	private int counter = 0;
	private volatile boolean done = false;
	private ReentrantLock lock = new ReentrantLock();
	
	public void setDone()
	{
		done = true; // No locking here.
	}
	
	public void run()
	{
		try
		{
			while( true )
			{
				try
				{
					lock.lock();
					if( done ) return;
				}
				finally
				{
					lock.unlock();
				}
				counter++;
				System.out.print( counter + " ");
				Thread.sleep(300);
			}
		}
		catch (InterruptedException e){}
		finally
		{
			shutDown();
		}
	}
	
	private void shutDown()
	{
		System.out.println("Final result: " + counter);
	}
	
	public static void main(String[] args)
	{
		VolatileLatch c = new VolatileLatch();
		Thread t = new Thread( c );
		t.start();
		try
		{
			Thread.sleep(10000);
			c.setDone();
		}
		catch (InterruptedException e){}
	}

}