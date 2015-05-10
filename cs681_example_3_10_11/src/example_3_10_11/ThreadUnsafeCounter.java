package example_3_10_11;

public final class ThreadUnsafeCounter implements Runnable
{
	private int counter = 0;
	private boolean done = false;
	
	public void setDone()
	{
		done = true;
	}
	
	public void run()
	{
		try
		{
			while( !done )
			{
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
		ThreadUnsafeCounter c = new ThreadUnsafeCounter();
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
