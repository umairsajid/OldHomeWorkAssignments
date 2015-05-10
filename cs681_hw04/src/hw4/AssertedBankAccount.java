package hw4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
 

public class AssertedBankAccount {
	 
	private Condition sufficientFundsCondition, belowUpperLimitFundsCondition;
	private AssertedBankAccount account;
	private static final double MIN_BALANCE = 0;
	private static final double PENALTY = 5;
	private double balance;
	private ReentrantLock lock;

	public AssertedBankAccount()
	{
		lock = new ReentrantLock();
		sufficientFundsCondition = lock.newCondition();
		belowUpperLimitFundsCondition = lock.newCondition();
		account =  this;
	}
	
	public void deposit(double amount) {
		lock.lock();	
		try {	
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
				" (d): current balance: " + balance);
			balance += amount;
			if (balance < MIN_BALANCE)
				subractPenaltyFee();
			System.out.println(Thread.currentThread().getId() + 
					" (d): new balance: " + balance);
			 
		}
	 	finally {
	 
			lock.unlock();
			assert lock.getHoldCount()==0: "ERROR";
			System.out.println("Lock released");
			} 
		
	}
	public void subractPenaltyFee() {
		balance -= PENALTY; 
	}
	
	public void withdraw(double amount)
	{
		lock.lock();
		try
		{
			System.out.println("Lock obtained");
			System.out.println(Thread.currentThread().getId() + 
					" (w): current balance: " + balance);
			while(balance < 0)
			{
				System.out.println(Thread.currentThread().getId() + 
						" (w): await(): Insufficient funds");
				sufficientFundsCondition.await();
			}
			balance -= amount;
			System.out.println(Thread.currentThread().getId() + 
					" (w): new balance: " + balance);
			belowUpperLimitFundsCondition.signalAll();
		}
		catch (InterruptedException exception)
		{
			exception.printStackTrace();
		}
		finally
		{
			lock.unlock();
			System.out.println("Lock released");
		}
	}
	

	public static void main(String[] args)
	{
		AssertedBankAccount bankAccount = new AssertedBankAccount();
		for(int i = 0; i < 5; i++)
		{
			new Thread( bankAccount.new DepositRunnable() ).start();
		}
		new Thread( bankAccount.new WithdrawRunnable() ).start();
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
	}