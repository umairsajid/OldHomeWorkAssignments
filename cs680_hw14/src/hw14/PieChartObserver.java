package hw14;

 

public class PieChartObserver implements StockEventObserver {

	 
	@Override
	public void updateStock(StockEvent args) {
		// TODO Auto-generated method stub
		System.out.println("Drawing Pie Chart of Stock with values: "+args.getTicker() +"="+args.getQuote());
	}

}
