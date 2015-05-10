package hw1;

import java.util.Date;

public class MyThread extends Thread {

 
		public void run()
		{
			try
			{
				for( int i=0; i<10; i++ )
				{
					Date now = new Date();
					System.out.println(now + " This is MyThread"  );
					Thread.sleep(1000);
				}
			}
			catch(InterruptedException ex)
			{
			}
		}
	
}
