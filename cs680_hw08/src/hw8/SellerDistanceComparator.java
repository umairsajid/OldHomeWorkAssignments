package hw8;

import java.util.Comparator;

public class SellerDistanceComparator implements Comparator<Car> {

	@Override
	public int compare(Car o1, Car o2) {
		// TODO Auto-generated method stub
		return (int) (o1.getSellerDistance() - o2.getSellerDistance());
	}

}
