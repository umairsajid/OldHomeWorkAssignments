package hw2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Hw2_driver
{
	
	
	
	public static void main(String[] args)
	{
		
	      System.out.println("Enter number: ");

	      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	      String number = null;

	      try {
	         number = br.readLine();
	      } catch (IOException ioe) {
 	         System.exit(1);
	      }
 


		
		Fibonacci runnable1 = new Fibonacci(Integer.parseInt(number));
		Thread thread1 = new Thread(runnable1);
		thread1.start();
		
		try {
			
			thread1.join();
			System.out.println("Done");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    
	
	}

}
