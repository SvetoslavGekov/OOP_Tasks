package vehicles;

import java.time.LocalDate;

import demo.Demo;
import demo.VehicleTypes;
import vignettes.Vignette;

public class Vehicle {
	
	//Fields
	private String model;
	private Vignette vignette = null;
	private int year;
	private VehicleTypes type;
	
	//Constr
	public Vehicle(String model, int year, VehicleTypes type) {
		setModel(model);
		setYear(year);
		setType(type);
		System.out.println(this);
	}
	
	//Methods
	private void checkVignette() {
		if(this.vignette != null && this.vignette.getExpirationDate().isBefore(LocalDate.now())) {
			this.vignette = null;
		}
	}
	
	public boolean hasVignette() {
		checkVignette();
		return this.vignette != null;
	}
	
	public boolean hasVignetteForDate(LocalDate date) {
		if(this.vignette == null || this.vignette.getExpirationDate().isBefore(date)) {
			return false;
		}
		return true;
	}
	
	//Setters
	@Override
	public String toString() {
		return String.format("Model: %s	Type: %s	Year: %d", this.model, this.type, this.year );
	}
	
	private void setModel(String model) {
		if(Demo.validStr(model)) {
			this.model = model;
		}
	}

	public void setVignette(Vignette vignette) {
		if(vignette != null) {
			this.vignette = vignette;
		}
	}

	private void setYear(int year) {
		if(year > 0) {
			this.year = year;
		}
	}

	private void setType(VehicleTypes type) {
		if(type != null) {
			this.type = type;
		}
	}
	
	//Getters
	public VehicleTypes getType() {
		return this.type;
	}

}
