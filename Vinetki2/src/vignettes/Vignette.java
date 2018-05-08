package vignettes;

import java.time.LocalDate;
import java.util.Comparator;

import demo.Demo;
import demo.VehicleTypes;

public abstract class Vignette {
	protected static final double MONTH_MULT = 10d;
	protected static final double YEAR_MULT = MONTH_MULT * 6d;
	
	//Fields
	private LocalDate productionDate;
	private VignetteDurations duration;
	private LocalDate expirationDate;
	private String color;
	private double cost;
	private VehicleTypes vehType;
	private int placingDuration;
	
	//Constr
	public Vignette(LocalDate productionDate, VignetteDurations duration) {
		setProductionDate(productionDate);
		setDuration(duration);
		setExpirationDate();
	}
	
	//Methods
	
	
	public static Comparator<Vignette> CompareByCost = new Comparator<Vignette>() {
		public int compare(Vignette o1, Vignette o2) {
			return Double.compare(o1.getCost(), o2.getCost()) >=0 ? 1 : -1;
		};
	};
	
	//Setters
	private void setDuration(VignetteDurations duration) {
		this.duration = duration;
	}
	
	private void setExpirationDate() {
		LocalDate expirationDate = this.productionDate.plusDays(this.duration.getDuration());
		if(expirationDate.isAfter(this.productionDate)) {
			this.expirationDate = expirationDate;
		}
	}

	private void setProductionDate(LocalDate productionDate) {
		if(productionDate != null) {
			this.productionDate = productionDate;
		}
	}

	protected void setColor(String color) {
		if(Demo.validStr(color)) {
			this.color = color;
		}
	}

	protected void setCost(double cost) {
		if(cost > 0) {
			this.cost = cost;
		}
	}

	protected void setPlacingDuration(int placingDuration) {
		if(placingDuration > 0) {
			this.placingDuration = placingDuration;
		}
	}

	public void setVehType(VehicleTypes vehType) {
		if(vehType != null) {
			this.vehType = vehType;
		}
	}
	
	//Getters
	public VehicleTypes getVehType() {
		return this.vehType;
	}
	
	protected LocalDate getProductionDate() {
		return this.productionDate;
	}

	public LocalDate getExpirationDate() {
		return this.expirationDate;
	}

	protected String getColor() {
		return this.color;
	}

	public double getCost() {
		return this.cost;
	}

	public int getPlacingDuration() {
		return this.placingDuration;
	}
	
	public VignetteDurations getDuration() {
		return this.duration;
	}
	
	
	public static Vignette createRandomVignette() {
		int vType = Demo.RNG(3);
		
		VignetteDurations[] vDurations = VignetteDurations.values(); 
		VignetteDurations duration = vDurations[Demo.RNG(vDurations.length)];
		
		switch(vType) {
			case 0: return new CarVignette(LocalDate.now(), duration);
			case 1: return new BusVignette(LocalDate.now(), duration);
			case 2: return new TruckVignette(LocalDate.now(), duration);
		}
		return null;
	}
}
