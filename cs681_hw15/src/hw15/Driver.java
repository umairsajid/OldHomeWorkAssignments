package hw15;

import java.io.File;

public class Driver {

	/**
	 * @param args
	*/
	LFUCache lf = new LFUCache();
	LRUCache lr = new LRUCache();
	public LFUCache getLFUCache(String targetFile)
	{
		
		new Thread(lf.new LFURunnable(targetFile)).start();
		return lf;
		
	}
	public LRUCache getLRUCache(String targetFile)
	{
		
		new Thread(lr.new LRURunnable(targetFile)).start();
		return lr;
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Driver d = new Driver();
	
		File dir = new File("files");
		File dir2 = new File("files2");
		String[] children = dir.list();
		File[] files;		
		String[] children2 = dir2.list();
		File[] files2;
		LRUCache c1 = null;
	 
		LFUCache lfu = null;
		if (children == null ||children2 == null  ) {
		}
		else {
			files = dir.listFiles();
			files2 = dir.listFiles();
			 for(int i=0; i<children.length; i++) {
		        File filename = files[i]; 
		      
		        lfu= d.getLFUCache(filename.getAbsolutePath());
		    
		        for(int j=0; j<children2.length;j++){
		        	File filename2 = files2[i]; 
		        	c1 = d.getLRUCache(filename2.getAbsolutePath());
		            
		            
		        } 
		        try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				 System.out.println(c1.getOutput() ); 
				 System.out.println( lfu.getOutput() );
		      }
		   }
		
	}

}
