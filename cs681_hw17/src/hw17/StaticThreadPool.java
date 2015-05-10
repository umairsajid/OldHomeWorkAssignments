package hw17;



import java.util.Vector;
import java.util.concurrent.locks.*;

public class StaticThreadPool
{
	private static StaticThreadPool INSTANCE = null;
	
	private boolean debug = false;
	private WaitingRunnableQueue queue =  null;
	private Vector<ThreadPoolThread> availableThreads = null;

	private StaticThreadPool(int maxThreadNum, boolean debug)
	{
		this.debug = debug;
		this.queue = new WaitingRunnableQueue(debug);
		availableThreads = new Vector<ThreadPoolThread>();
		for(int i = 0; i < maxThreadNum; i++)
		{
			ThreadPoolThread th = new ThreadPoolThread(debug, queue, i);
			availableThreads.add(th);
			th.start();
		}
	}
 
	public static StaticThreadPool getInstance(int maxThreadNum, boolean debug){
		if(INSTANCE==null){
			INSTANCE = new StaticThreadPool(maxThreadNum,debug);
		}
		return INSTANCE;
	}
	
	public void execute(Runnable runnable)
	{
		INSTANCE.queue.put(runnable);
	}

	public int getWaitingRunnableQueueSize()
	{
		return INSTANCE.queue.size();
	}

	public int getThreadPoolSize()
	{
		return availableThreads.size();
	}
	private void shutdown() {
		for(int i = 0; i < availableThreads.size(); i++)
		{
			
			availableThreads.get(i).setStopped(true);
			availableThreads.get(i).interrupt();
		
		}
		
	}

	private class WaitingRunnableQueue
	{
		private Vector<Runnable> runnables = new Vector<Runnable>();
		private Boolean DebugStaticThreadPool;
		private ReentrantLock queueLock;
		private Condition runnablesAvailable;

		public WaitingRunnableQueue(Boolean pool)
		{
			this.DebugStaticThreadPool = pool;
			queueLock = new ReentrantLock();
			runnablesAvailable = queueLock.newCondition();
		}

		public int size()
		{
			return runnables.size();
		}

		public void put(Runnable obj)
		{
			queueLock.lock();
			try
			{
				runnables.add(obj);
				if(DebugStaticThreadPool==true) System.out.println("A runnable queued.");
				runnablesAvailable.signalAll();
			}
			finally
			{
				queueLock.unlock();
			}
		}

		public Runnable get()
		{
			queueLock.lock();
			try
			{
				while(runnables.isEmpty())
				{
					if(DebugStaticThreadPool==true) System.out.println("Waiting for a runnable...");
					runnablesAvailable.await();
				}
				if(DebugStaticThreadPool==true) System.out.println("A runnable dequeued.");
				return runnables.remove(0);
			}
			catch(InterruptedException ex)
			{
				return null;
			}
			finally
			{
				queueLock.unlock();
			}
		}
	}


	private class ThreadPoolThread extends Thread
	{
		private ReentrantLock stoppedLock;
 
		private Boolean stopped= false; 
		public void setStopped(Boolean stopped) {
			stoppedLock.lock();
			this.stopped = stopped;
			stoppedLock.unlock();
		}

		private Boolean StaticThreadPoolDebug;
		private WaitingRunnableQueue queue;
		private int id;

		public ThreadPoolThread(Boolean pool, WaitingRunnableQueue queue)
		{
			this(pool, queue, -1);
		}

		public ThreadPoolThread(Boolean pool, WaitingRunnableQueue queue, int id)
		{
			this.StaticThreadPoolDebug = pool;
			this.queue = queue;
			this.id = id;
			stoppedLock = new ReentrantLock();
		}

		public void run()
		{
			if(StaticThreadPoolDebug==true) System.out.println("Thread " + id + " starts.");
			while(true)
			{
				Runnable runnable = queue.get();
				
				if(stopped == true)
				{
					if(StaticThreadPoolDebug==true) System.out.println("Thread " + id + " stops.");
					break;
				}
				if(runnable==null)
				{
					if(StaticThreadPoolDebug==true)
						System.out.println("Thread " + this.id + " is being stopped due to an InterruptedException.");
					continue;
				}
				else
				{
					if(StaticThreadPoolDebug==true) System.out.println("Thread " + id + " executes a runnable.");
					runnable.run();
					if(StaticThreadPoolDebug == true)System.out.println("ThreadPoolThread " + id + " finishes executing a runnable.");
				}
			}
 		
		}
	}
	
	public static void main(String[] args)
	{
		StaticThreadPool s = StaticThreadPool.getInstance(2,true);
		s.execute(new RunnableTest("a"));
		s.execute(new RunnableTest("b"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.execute(new RunnableTest("c"));
		s.execute(new RunnableTest("d"));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		s.shutdown();
		/*		
		 * 
		 * StaticThreadPool.execute(new RunnableTest("c"));
		StaticThreadPool.execute(new RunnableTest("d"));
		StaticThreadPool pool = new StaticThreadPool(2, true);
		*/
	}


}
