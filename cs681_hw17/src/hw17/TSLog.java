package hw17;
 
public class TSLog {

	private static ThreadLocal<Integer> tss = new ThreadLocal<Integer>();

	public static void setResult( int result )
	{
		tss.set( Integer.valueOf(result) );
	}
	
	public static void printResult()
	{
		System.out.println( tss.get().intValue() );
	}
	
	public static void main(String[] args)
	{
		TSLog log = new TSLog();	
		new Thread( log.new DataGenerator() ).start();
		new Thread( log.new DataGenerator() ).start();
		new Thread( log.new DataGenerator() ).start();
		new Thread( log.new DataGenerator() ).start();
		new Thread( log.new DataGenerator() ).start();
	}
	
	private class DataGenerator implements Runnable
	{
		public void run()
		{
			for( int i=0; i<10; i++ )
			{
				TSLog.setResult(i);
				try
				{
					Thread.sleep(100);
					TSLog.printResult();
				}
				catch(InterruptedException e){}
			}
		}
	}
}
