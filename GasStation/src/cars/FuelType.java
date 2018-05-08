package cars;

import demo.GasStation;

public enum FuelType {
	GASOLINE(2.0d), DIESEL(2.40d), GAS(1.60d);
	
	//Fields
	private double cost;
	
	FuelType(double cost){
		this.cost = cost;
	}
	
	public static FuelType getRandomFuelType() {
		FuelType[] fuels = FuelType.values();
		return fuels[GasStation.RNG(fuels.length)];
	}
	
	public double getCost() {
		return this.cost;
	}
}
