package hw14;

 

public class _3DObserver implements StockEventObserver {
 
	
	public void updateStock(StockEvent args) {
		// TODO Auto-generated method stub
		System.out.println("Drawing 3D Object of Stock with values: "+args.getTicker() +"="+args.getQuote());
	}


}
