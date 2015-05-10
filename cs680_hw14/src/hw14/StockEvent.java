package hw14;

import java.util.EventObject;

public class StockEvent extends EventObject {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ticker;
	private double quote;
	
	public StockEvent(Object args){
		super(args);
	}
	
	public StockEvent(Object args, String t, Double q) {
		 super(args);
		 ticker = t.toString();
		 quote = q.doubleValue();
		// TODO Auto-generated constructor stub
	}
	
	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public double getQuote() {
		return quote;
	}

	public void setQuote(double quote) {
		this.quote = quote;
	}
}
