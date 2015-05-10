package hw7;
 

import java.util.concurrent.locks.ReentrantLock;
 
public class Singleton{
	
	private Singleton(){
		
	}
	
	private static Singleton instance = null;
	
	private static ReentrantLock lock = new ReentrantLock();
	
	public static Singleton getInstance(){
		lock.lock();
		System.out.println("Lock obtained");
		if(instance==null){
			instance = new Singleton ();
			try {
				for(int i=0; i<5; i++)
				{
					 System.out.println("Creating Singleton ");	 
					Thread.sleep(1000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
		}
				lock.unlock();
				System.out.println("Lock released");
			
	    System.out.println("Singleton sent");	 
 		return instance; 
	} 

	public static void main(String[] args)
		{
			
		SingletonLoader sRunnable = new SingletonLoader();
		Thread thread1 = new Thread(sRunnable);
		Thread thread2 = new Thread(sRunnable);
		thread1.start();
		thread2.start();
			
	}
}
