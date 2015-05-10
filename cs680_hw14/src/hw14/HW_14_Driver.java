package hw14;

public class HW_14_Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String ticker = "DOW";
		double quote = 11500.00;	
		System.out.println("Current Stock Quote for "+ticker +" is " + quote);
		
		StockQuoteObservable o = new StockQuoteObservable();
 
		o.addEventListener(new PieChartObserver());
		o.addEventListener(new TableObserver());
		o.addEventListener(new _3DObserver());
		quote =  11140.32;
		System.out.println(" Stock Quote for "+ticker +" has changed to " + quote);
		o.changeQuote(ticker, quote);
		
	}

}
