package hw8;

import java.util.ArrayList;
import java.util.Collections;

public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Car> carInventory = new ArrayList<Car>();
		
		carInventory.add(new Car("Acura2010",5000,300,50000));
		carInventory.add(new Car("Saab2009",8000,100,20000));
		carInventory.add(new Car("Toyota2002",60000,40,7500));
		carInventory.add(new Car("Accord2005",2000,300,12000));
		carInventory.add(new Car("BMW2004",34100,30,25000));
		
		System.out.println("UnSorted");
		for(int k= 0; k < carInventory.size()-1;k++)
			System.out.println(carInventory.get(k).getYearModel() + " Mileage: " +carInventory.get(k).getMileage() 
					 + " Seller Distance: " + carInventory.get(k).getSellerDistance()
					 + " Price: " + carInventory.get(k).getPrice()  );

		Collections.sort(carInventory,new PriceComparator());
		System.out.println("Sorted by Price");
		for(int k= 0; k < carInventory.size()-1;k++)
			System.out.println(carInventory.get(k).getYearModel() + " Mileage: " +carInventory.get(k).getMileage() 
					 + " Seller Distance: " + carInventory.get(k).getSellerDistance()
					 + " Price: " + carInventory.get(k).getPrice()  );
 
		Collections.sort(carInventory,new MileageComparator());
		System.out.println("Sorted by Mileage");
		
		for(int k= 0; k < carInventory.size()-1;k++)
			System.out.println(carInventory.get(k).getYearModel() + " Mileage: " +carInventory.get(k).getMileage() 
					 + " Seller Distance: " + carInventory.get(k).getSellerDistance()
					 + " Price: " + carInventory.get(k).getPrice()  );
		Collections.sort(carInventory,new SellerDistanceComparator());
		
		System.out.println("Sorted by Seller Distance");
		for(int k= 0; k < carInventory.size()-1;k++)
			System.out.println(carInventory.get(k).getYearModel() + " Mileage: " +carInventory.get(k).getMileage() 
					 + " Seller Distance: " + carInventory.get(k).getSellerDistance()
					 + " Price: " + carInventory.get(k).getPrice()  );
	}

}
