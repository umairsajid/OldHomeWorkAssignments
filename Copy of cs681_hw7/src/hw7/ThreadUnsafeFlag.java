package hw7

public class ThreadUnsafeFlag
{
	private boolean done = false;
	
	public void setDone()
	{
		done = true;
	}
	
	public void work()
	{
		while( !done )
		{
			System.out.println("#");
		}
		System.out.println("Flag state changed.");
		done = false;
	}
	
	public static void main(String[] args)
	{
		ThreadUnsafeFlag flagObject = new ThreadUnsafeFlag();
		new Thread(flagObject.new Interrupter(flagObject)).start();
		flagObject.work();
	}
	
	public class Interrupter implements Runnable
	{
		private ThreadUnsafeFlag target;
		
		Interrupter(ThreadUnsafeFlag target)
		{
			this.target = target;
		}
		
		public void run()
		{
			while(true)
			{
				try
				{
					Thread.sleep(100);
					target.setDone();
				} catch (InterruptedException e){}
			}
		}
	}
}
