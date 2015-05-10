package hw8;

public class Car {

	private String YearModel;
	private float mileage;
	private float sellerDistance;
	private float price;

		public Car(String YrModel, float mlage, float selDis, float pr){
			this.setYearModel(YrModel.toString());
			this.setMileage(mlage);
			this.setSellerDistance(selDis);
			this.setPrice(pr);
			
		}
		public String getYearModel() {
			return YearModel;
		}

		public void setYearModel(String yearModel) {
			YearModel = yearModel;
		}

		public float getMileage() {
			return mileage;
		}

		public void setMileage(float mileage) {
			this.mileage = mileage;
		}

		public float getSellerDistance() {
			return sellerDistance;
		}

		public void setSellerDistance(float sellerDistance) {
			this.sellerDistance = sellerDistance;
		}

		public float getPrice() {
			return price;
		}

		public void setPrice(float price) {
			this.price = price;
		}	
		
}
