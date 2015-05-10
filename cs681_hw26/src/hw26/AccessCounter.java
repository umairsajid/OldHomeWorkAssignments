package hw26;

 


import java.util.HashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
 
public class AccessCounter{
	
	private   ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	HashMap<String, Integer> pathAccessCount =new HashMap<String, Integer>();
	private AccessCounter(){
		
	}
	 
	
	
	
	public void increment(String filePath){
		
		lock.writeLock().lock();
		System.out.println("Lock obtained");
	
		
		if( pathAccessCount.get(filePath)==null || pathAccessCount.get(filePath).intValue()<0  )
		{
			pathAccessCount.put(filePath, 1);
		//	System.out.println(filePath + " was accessed for the first time");
			
		}
		else
		{	
			pathAccessCount.put(filePath, pathAccessCount.get(filePath)+1);
			//System.out.println(filePath + " was accessed "+ (pathAccessCount.get(filePath)) + " times");
		
			
		}
		 
		lock.writeLock().unlock();
		System.out.println("Lock released");
	} 
	public int getCount(String filePath){
		lock.readLock().lock();
		int cnt =0; 
		 
		if( pathAccessCount.get(filePath)==null || pathAccessCount.get(filePath).intValue()<0  )
			cnt= 0;
		else
			cnt = pathAccessCount.get(filePath).intValue();
		lock.readLock().unlock();
		return  cnt;
		 
	} 

	public static void main(String[] args)
		{
			 
			AccessCounter test = new AccessCounter();
			new Thread(test.new FileAccessor(test,"File1")).start();
			new Thread(test.new AccessTally(test,"File1")).start();


			try {
				Thread.sleep(1000);
				new Thread(test.new FileAccessor(test,"File1")).start();
				new Thread(test.new FileAccessor(test,"File1")).start();
				test.increment("File1");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			new Thread(test.new AccessTally(test,"File1")).start();
			
	}
	public class FileAccessor implements Runnable{

		String file;
		AccessCounter acc;
		public FileAccessor(AccessCounter a, String filePath){
			this.acc= a;
			file= new String (filePath.toString());
		}
		
		public void run() {
			acc.increment(file);
			
		}
		
	}
	
	public class AccessTally implements Runnable{

		String file;
		AccessCounter acc;
		public AccessTally(AccessCounter a, String filePath){
			this.acc= a;
			file= new String (filePath.toString());
		}
		
		public void run() {
			 
			System.out.println(file + " was accessed "+ acc.getCount(file) + " times");
		}
		
	}
}
