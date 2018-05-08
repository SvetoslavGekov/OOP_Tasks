package people;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import demo.Demo;
import demo.GasStation;
import demo.VehicleTypes;
import vehicles.Vehicle;
import vignettes.Vignette;
import vignettes.VignetteDurations;

public class Driver {
	//Fields
	private String name;
	private LinkedHashSet<Vehicle> vehicles = new LinkedHashSet<>();
	private double money;
	private GasStation gasStation;

	
	//Contr
	public Driver(String name, double money, GasStation gasStation) {
		setName(name);
		setMoney(money);
		setGasStation(gasStation);
		System.out.println(this);
	}
	
	//Methods
	public void printAllCars() {
		for (Vehicle vehicle : vehicles) {
			if(vehicle.getType().equals(VehicleTypes.CAR) && vehicle.hasVignette()) {
				System.out.println(vehicle);
			}
		}
	}
	
	@Override
	public String toString() {
		return String.format("Name: %s		Money: %.2f	GasStation: %s", this.name, this.money, this.gasStation.getName());
	}
	
	//Methods
	public void addVehicle(Vehicle veh) {
		if(veh != null) {
			this.vehicles.add(veh);
		}
		System.out.println(veh + " has been added to " + this.name + "'s garage");
	}
	
	public void buyVignettesForAllVehicles() {
		for (int i = 1; i <= this.vehicles.size(); i++) {
			VignetteDurations[] vDurations = VignetteDurations.values(); 
			VignetteDurations duration = vDurations[Demo.RNG(vDurations.length)];
			this.buyVignetteForVehicle(i, duration);
		}
	}
	
	
	public void buyVignetteForVehicle(int vehicleNumber, VignetteDurations duration) {
		//Get vehicle
		Vehicle veh = getRandomVehicle(vehicleNumber);
		if(!veh.hasVignette()) {
			//Buy vignette
			System.out.println(String.format("%s would like to buy a vignette for %s", this, veh));
			Vignette vehVignette = gasStation.sellVignette(veh, this, duration);
			//Stick vignette to vehicles
			System.out.println(this.placeVignetteOnVehicle(vehVignette, veh));
		}
		else {
			System.out.println("Sorry " + veh + " has a vignette!");
		}
	}
	
	private Vehicle getRandomVehicle(int vehicleNumber) {
		if(vehicleNumber > 0 && vehicleNumber <= this.vehicles.size()) {
			int counter = 0;
			
			Iterator<Vehicle> it = this.vehicles.iterator();
			while(it.hasNext()) {
				counter++;
				Vehicle veh = it.next();
				if(counter == vehicleNumber) {
					return veh;
				}
			}
		}
		return null;
	}

	public void printVehiclesWithoutVignettesForDate(LocalDate date) {
		System.out.println("======== VEHICLES WITH NO VIGNETTE FOR DATE " + date);
		for (Vehicle vehicle : vehicles) {
			if(!vehicle.hasVignetteForDate(date)) {
				System.out.println(vehicle);
			}
		}
	}
	
	private int placeVignetteOnVehicle(Vignette vignette, Vehicle veh) {
		veh.setVignette(vignette);
		return vignette.getPlacingDuration();
	}
	
	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}


	public void setMoney(double money) {
		if(money >= 0) {//Can be broke
			this.money = money;
		}
	}


	private void setGasStation(GasStation gasStation) {
		if(gasStation != null) {
			this.gasStation = gasStation;
		}
	}
	
	//Getters
	private String getName() {
		return name;
	}

	public double getMoney() {
		return money;
	}

	public Set<Vehicle> getVehicles() {
		return Collections.unmodifiableSet(this.vehicles);
	}

}
