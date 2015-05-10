package hw12;

 
 



 

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.Condition;
import java.util.ArrayList;

public class ThreadSafeBankAccount4
{
	public CyclicBarrier barrier = null;
	private double balance = 0;
	private ReentrantReadWriteLock lock;
	private Condition sufficientFundsCondition;
	private Condition belowUpperLimitFundsCondition;
	private ThreadSafeBankAccount4 account; 
	public ArrayList<ReadRunnable> list;
	
	public ThreadSafeBankAccount4()
	{
		list = new ArrayList<ReadRunnable>();
		lock = new ReentrantReadWriteLock();
		sufficientFundsCondition = lock.writeLock().newCondition();
		belowUpperLimitFundsCondition = lock.writeLock().newCondition();
		barrier = new CyclicBarrier( 6, new BarrierAction() );
		account = this;
	}
	
	public void deposit(double amount)
	{
		lock.writeLock().lock();
		System.out.println("Lock obtained");
		try
		{
			System.out.println(Thread.currentThread().getId() + " (d): current balance: " + balance);
			while(balance >= 300)
			{
				System.out.println(Thread.currentThread().getId() + " (d): await(): Balance exceeds the upper limit.");
				belowUpperLimitFundsCondition.await();
			}
			balance += amount;
			System.out.println(Thread.currentThread().getId() + " (d): new balance: " + balance);
			sufficientFundsCondition.signalAll();
		}
		catch (InterruptedException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			lock.writeLock().unlock();
			System.out.println("Lock released");
		}
	}
	
	public void withdraw(double amount)
	{
		lock.writeLock().lock();
		System.out.println("Lock obtained");
		try
		{
			System.out.println(Thread.currentThread().getId() + " (w): current balance: " + balance);
			while(balance < amount)
			{
				System.out.println(Thread.currentThread().getId() + " (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + " (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			lock.writeLock().unlock();
			System.out.println("Lock released");
		}
	}
	
	public double getBalance()
	{
		lock.readLock().lock();
		System.out.println("Lock obtained");
		try{
			System.out.println(Thread.currentThread().getId() + " (b): current balance: " + balance);
			return balance;
		}
		finally
		{
			lock.readLock().unlock();
			System.out.println("Lock released");
		}
	}
	
	public static void main(String[] args)
	{
		ThreadSafeBankAccount4 bankAccount = new ThreadSafeBankAccount4();
		ArrayList<Thread> list = new ArrayList<Thread>();
		
		for(int i = 0; i < 2; i++)
		{
			list.add( new Thread( bankAccount.new DepositRunnable() ) );
			list.add( new Thread( bankAccount.new WithdrawRunnable() ) );
		}
		for(int i = 0; i < 6; i++)
		{
			bankAccount.list.add( bankAccount.new ReadRunnable() );
		}
		for(int i = 0; i < 6; i++)
		{
			list.add( new Thread(bankAccount.list.get(i) ) );
		}
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < 10; i++)
		{
			list.get(i).start();
		}
		try {
			for(int i = 4; i < 10; i++)
			{
				list.get(i).join();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println( endTime - startTime );
	}
	
	private class DepositRunnable implements Runnable
	{
		public void run()
		{
			try
			{
				for(int i = 0; i < 10; i++)
				{
					account.deposit(100);
					Thread.sleep(2);
				}
			}catch(InterruptedException exception){}
		}
		
	}	

	private class WithdrawRunnable implements Runnable
	{
		public void run()
		{
			try
			{
				for(int i = 0; i < 10; i++)
				{
					account.withdraw(100);
					Thread.sleep(2);
				}
			}catch(InterruptedException exception){}
		}
		
	}

	private class ReadRunnable implements Runnable
	{
		private long timeElapsed = 0;
		public void run()
		{
			try
			{
				long startTime = System.currentTimeMillis();
				for(int i = 0; i < 10; i++)
				{
					account.getBalance();
					Thread.sleep(2);
				}
				long endTime = System.currentTimeMillis();
				timeElapsed = endTime - startTime;
				barrier.await();
			}catch(InterruptedException exception){}
			catch (BrokenBarrierException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		public int getTimeElapsed(){
			return (int) timeElapsed;
			}
	}
	private class BarrierAction implements Runnable{
		private int finalResult = 0;
		public void run(){
			for(Runnable r: list)
				finalResult += ((ReadRunnable)r).getTimeElapsed();
			
			System.out.println("Barrier Ended, the final result is:" + finalResult);
			} 
		}
	
}
