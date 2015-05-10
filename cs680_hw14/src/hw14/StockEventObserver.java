package hw14;

import java.util.EventListener;
 
 

public interface StockEventObserver extends EventListener  {
	
		public void updateStock(StockEvent args);
}
