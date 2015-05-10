package hw7;


public class SingletonLoader implements Runnable
{ 
	Singleton retObj = null;

	public SingletonLoader(){}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		retObj();
	}
	public void retObj(){
		System.out.println("Thread " + Thread.currentThread().getId() + " requesting Singleton");
	 	System.out.println("Singleton retrieved "+ Singleton.getInstance().toString()+" in Thread "+Thread.currentThread().getId() );
		
		//return Singleton.getInstance();
	} 
	
}