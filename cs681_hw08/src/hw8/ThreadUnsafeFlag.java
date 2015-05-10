package hw8;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ThreadUnsafeFlag
{
	private boolean done = false;
	private  ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	
	public void setDone()
	{
		lock.writeLock().lock();
		System.out.println("Lock obtained");
		done = true;
		lock.writeLock().unlock();
		System.out.println("Lock released");
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
					Thread.sleep(1000);
					target.setDone();
				} catch (InterruptedException e){}
			}
		}
	}
}
