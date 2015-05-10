package hw7;

import java.awt.Point;
import java.util.ArrayList;

public class RectangleAreaCalc implements AreaCalculator {

	@Override
	public float getArea(ArrayList<Point> points) {
		float area = 0;
		
		if (points.get(0).getX()==points.get(1).getX()){
			if(points.get(0).getY() == points.get(2).getY()) {
				area= (float) (points.get(0).distance(points.get(1)) * points.get(0).distance(points.get(2)));
				
			 }
		else {
				area= (float) (points.get(0).distance(points.get(1)) * points.get(0).distance(points.get(3)));
			}
		}
		
		else if (points.get(0).getX() == points.get(2).getX()){
			if(points.get(0).getY() == points.get(1).getY())
				area = (float) (points.get(0).distance(points.get(2)) * points.get(0).distance(points.get(1)));
			else   
				area =(float) (points.get(0).distance(points.get(2)) * points.get(0).distance(points.get(3)));
		}
		else {
			if(points.get(0).getY() == points.get(1).getY())
				area = (float) (points.get(0).distance(points.get(3)) * points.get(0).distance(points.get(1)));
			else   
				area =(float) (points.get(0).distance(points.get(3)) * points.get(0).distance(points.get(2)));
		}
			
		return area;
	}

}

	