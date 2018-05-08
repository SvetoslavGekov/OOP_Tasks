package vignettes;

import java.time.LocalDate;

import demo.VehicleTypes;

public class CarVignette extends Vignette {
	private static final double DAY_COST = 5d;
	private static final int DURATION = 5;
	private static final String COLOR = "RED";
	
	//Constr
	public CarVignette(LocalDate productionDate, VignetteDurations duration) {
		super(productionDate, duration);
		setVehType(VehicleTypes.CAR);
		setCost();
		setPlacingDuration(DURATION);
		setColor(COLOR);
//		System.out.println(this.toString());
	}

	@Override
	public String toString() {
		return String.format("Type: CAR	Cost: %.2f	Color: %s	Duration: %d	ExpDate: %s", this.getCost(), this.getColor(),
				this.getDuration().getDuration(), this.getExpirationDate());
	}
	
	protected void setCost() {
		switch(this.getDuration().getDuration()) {
			case 1: super.setCost(DAY_COST); return;
			case 30: super.setCost(DAY_COST * super.MONTH_MULT); return;
			case 365: super.setCost(DAY_COST * super.YEAR_MULT); return;
		}
	}
}
