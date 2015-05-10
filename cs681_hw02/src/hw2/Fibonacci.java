package hw2;
import java.util.ArrayList;

public class Fibonacci implements Runnable
{
	String currentSeq = new String();
	ArrayList <String>seq = new ArrayList<String>();
	private int n;
	
	public Fibonacci(int input)
	{
		n =input;
	}
	
	 public int fibonacci(int number) {
		    if ((number == 0) || (number == 1)){
		    	return number;
		    }
		    else
		    { 
		    			    	
		        return fibonacci(number - 1) + fibonacci(number - 2);
		    }
		  }
 
	public void run()
	{
		try
		{
			int f ;
			int i = 0;
			while (i<=n){
				f = fibonacci(i);
				seq.add(new String(f+""));
			
				
				i++;
				System.out.println(seq);
			}
			Thread.sleep(5000);
			System.out.println(seq);
		
		}
		catch(InterruptedException ex)
		{
		}
	}
}
