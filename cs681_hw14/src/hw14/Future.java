 package hw14;
 
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Future implements Pizza
{
	private RealPizza realPizza = null;
	private String pizzaData;
	private ReentrantLock lock;
	private Condition ready;
	
	public Future()
	{
		lock = new ReentrantLock();
		ready = lock.newCondition();
	}
	
	public void setRealPizza( RealPizza real )
	{
		lock.lock();
		try
		{
			if( realPizza != null ){ return; }
			realPizza = real;
			ready.signalAll();
		}
		finally
		{
			lock.unlock();
		}
	}

	public Boolean isReady(){
		boolean retval = true;
		if(lock.isLocked()==true){
			return false;
			}
		else{
			lock.lock();
			if(realPizza==null){
				 retval=false;
				}
			lock.unlock();
		
			}
				return retval;
		
	}
	public String getPizza()
	{
		lock.lock();
		try
		{
			if( realPizza == null )
			{
				ready.await();
			}
			pizzaData = realPizza.getPizza();
		}
		catch(InterruptedException e){}
		finally
		{
			lock.unlock();
		}
		return pizzaData;
	}
	public String getPizza(long timeout)
	{
		lock.lock();
		try
		{
			if( realPizza == null )
			{
				   boolean wait;
					wait =ready.await(timeout, TimeUnit.MILLISECONDS);
					if(!wait)throw new TimeoutException();
					 
			}
			pizzaData = realPizza.getPizza();
		}
		catch(InterruptedException e){e.printStackTrace();}
		catch(TimeoutException e){e.printStackTrace();}
		finally
		{
			lock.unlock();
		}
		return pizzaData;
	}
	public class KitchenRunnable implements Runnable
	{
		
		
		public void run()
		{
			RealPizza realPizza = new RealPizza();
			setRealPizza( realPizza );
		}
		
	}
}
