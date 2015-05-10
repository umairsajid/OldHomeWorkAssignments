package hw15;
 
import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

 
public class LFUCache implements FileCache
{
	public Condition ready;
	public Condition ready2;
	public LFUCache() {
		 
		ready = LFUMutex.newCondition();
		ready2= LFUReadWriteLock.writeLock().newCondition();
	}
	private String output;
	
	
	public String getOutput() {
		/*LFUMutex.lock();
		
		 	try {
				if( output == null )
					ready.await();
				 
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			finally{
				LFUMutex.unlock();
			}*/
		 
	
		return output;
	}
	private ReentrantReadWriteLock LFUReadWriteLock = new ReentrantReadWriteLock();
	private ReentrantLock LFUMutex = new ReentrantLock();
	HashMap<String,String> fileCache = new HashMap<String, String>(4);

   
	@Override
	public String fetch(String targetFile) {
		String ret;
		if(fileCache.containsKey(targetFile.toString())){
			LFUMutex.lock();
			ret= new String(fileCache.get(targetFile));
			LFUMutex.unlock();
			ready.signalAll();
		}
		else
		{	
			ret= new String( cacheFile(targetFile));
			
		}
		return ret;
		
	}
	public String cacheFile(String TargetFile){
		LFUReadWriteLock.writeLock().lock();
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
		LFUReadWriteLock.writeLock().unlock();
		
		return line.toString();
	}
 
	public class LFURunnable implements Runnable
	{
		private String TargetFile;
		
		public LFURunnable(String TFile)
		{
			TargetFile = TFile;
		}
		
		public void run()
		{
			 
			 output = fetch(TargetFile);
		}
	}
}