package hw8;

import java.util.Comparator;

public class MileageComparator implements Comparator<Car> {

	@Override
	public int compare(Car o1, Car o2) {
		// TODO Auto-generated method stub
		return (int) (o1.getMileage() - o2.getMileage());
	}

}
