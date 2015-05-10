package hw14;

import java.util.ArrayList; 
import java.util.Observable;

public class StockQuoteObservable extends Observable     {
	String ticker;
	Double quote;
	ArrayList<StockEventObserver> eventlisteners = new ArrayList<StockEventObserver>()  ;
	
	public void changeQuote(String t, Double q){
		ticker = t.toString();
		quote = q.doubleValue();
		setChanged();
		notifyObservers(new StockEvent(this,t,q));
	}
	public void addEventListener(StockEventObserver EL){
		eventlisteners.add(EL);
	}
    public void notifyObservers(StockEvent data) {
		             int size = 0;
		             StockEventObserver[] arrays = null;
		              synchronized (this) {
		                  if (hasChanged()) {
		                      clearChanged();
		                      size = eventlisteners.size();
		                      arrays = new StockEventObserver[size];
		                      eventlisteners.toArray(arrays);
		                  }
		              }
		              if (arrays != null) {
		                  for (StockEventObserver observer : arrays) {
		                      observer.updateStock( data);
		                  }
		              }
		          }

}
