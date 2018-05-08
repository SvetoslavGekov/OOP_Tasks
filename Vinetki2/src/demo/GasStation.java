package demo;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import people.Driver;
import vehicles.Vehicle;
import vignettes.Vignette;
import vignettes.VignetteDurations;

public class GasStation {
	private static final int STARTING_VIGNETTES = 500;
	
	//Fields
	private String name;
	private TreeSet<Vignette> vignettes = new TreeSet<Vignette>(Vignette.CompareByCost);
	private double dailyRevenue = 0d;
	
	//Constr
	public GasStation(String name) {
		setName(name);
		createVignettes();
		printVignettes();
		System.out.println(this.toString());
	}
	
	//Methods
	public void printVignettes() {
		for (Vignette vignette : vignettes) {
			System.out.println(vignette);
		}
		System.out.println("Number of vignettes: " + this.vignettes.size());
	}
	
	private void createVignettes() {
		System.out.println("=========== CERATE VIGNETTES ===========");
		for (int i = 0; i < STARTING_VIGNETTES; i++) {
			Vignette v = Vignette.createRandomVignette();
			this.vignettes.add(v);
		}
	}
	
	@Override
	public String toString() {
		return String.format("Gas station: %s Total vignettes: %d	Daily Revenue: %.2f", this.name, this.vignettes.size(), this.dailyRevenue);
	}
	
	//Getters
	public String getName() {
		return this.name;
	}
	
	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}
	
	public void setDailyRevenue(double dailyRevenue) {
		if(this.dailyRevenue >= 0) {
			this.dailyRevenue = dailyRevenue;
		}
	}

	public Vignette sellVignette(Vehicle veh, Driver driver, VignetteDurations duration) {
		if(veh != null && driver != null) {
			for (Iterator<Vignette> it = vignettes.iterator(); it.hasNext();) {
				Vignette vig = (Vignette) it.next();
				
				if(vig.getDuration().getDuration() == duration.getDuration() && veh.getType() == vig.getVehType()) {
					it.remove();
					//Pay for vignette
					driver.setMoney(driver.getMoney() - vig.getCost());
					//Add money to gass station
					this.setDailyRevenue(this.dailyRevenue + vig.getCost());
					return vig;
				}
			}
		}
		return null;
	}
	
	
	
}
