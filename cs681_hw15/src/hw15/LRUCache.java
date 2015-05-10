package hw15;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

 
public class LRUCache implements FileCache
{
	public Condition ready;
	public Condition ready2;
	public LRUCache() {
		 ReentrantReadWriteLock LRUReadWriteLock = new ReentrantReadWriteLock();
		 ReentrantLock LRUMutex = new ReentrantLock();
		ready = LRUMutex.newCondition();
		ready2= LRUReadWriteLock.writeLock().newCondition();
	}
	private String output;
	
	
	public String getOutput() {
		/*LRUMutex.lock();
		
		 	try {
				if( output == null )
					ready.await();
				 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				LRUMutex.unlock();
			}*/
		 
	
		return output;
	}
	private ReentrantReadWriteLock LRUReadWriteLock = new ReentrantReadWriteLock();
	private ReentrantLock LRUMutex = new ReentrantLock();
	HashMap<String,String> fileCache = new HashMap<String, String>(4);

   
	@Override
	public String fetch(String targetFile) {
		String ret;
		if(fileCache.containsKey(targetFile.toString())){
			LRUMutex.lock();
			ret= new String(fileCache.get(targetFile));
			LRUMutex.unlock();
			
		}
		else
		{	
			ret= new String( cacheFile(targetFile));
			
		}
		return ret;
		
	}
	public String cacheFile(String TargetFile){
		LRUReadWriteLock.writeLock().lock();
		File f = new File(TargetFile);
		Scanner s = null;
		try {
			s = new Scanner(f);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line ="";
		while (s.hasNextLine()) { 
    		  line = line.toString() + s.nextLine(); 
		} 
	 
		fileCache.put(TargetFile,line.toString());
		LRUReadWriteLock.writeLock().unlock();
		
		return line.toString();
	}
 
	public class LRURunnable implements Runnable
	{
		private String TargetFile;
		
		public LRURunnable(String TFile)
		{
			TargetFile = TFile;
		}
		
		public void run()
		{
			 
			 output = fetch(TargetFile);
		}
	}
}
