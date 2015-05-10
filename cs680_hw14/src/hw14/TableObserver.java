package hw14;

 
public class TableObserver implements StockEventObserver {

	 
	public void updateStock(StockEvent args) {
		// TODO Auto-generated method stub
		System.out.println("Drawing Table of Stock with values: "+args.getTicker() +"="+args.getQuote());
	}
}
