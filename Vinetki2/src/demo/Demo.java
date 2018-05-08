package demo;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import people.Driver;
import vehicles.Vehicle;
import vignettes.VignetteDurations;

public class Demo {

	public static final int RNG(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static  final int RNG(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min + 1;
	}
	
	public static final boolean validStr(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static void main(String[] args) {
		
		//1. Create gas station
		System.out.println("=========== CERATE GAS STATION ===========");
		GasStation gStation = new GasStation("Kalata GAZ");
		
		//2. Create 20 random drivers
		System.out.println("\n========== DRIVERS CREATION =========");
		ArrayList<Driver> drivers = new ArrayList<>();
		
		for (int i = 0; i < 20; i++) {
			Driver d = new Driver("Driver " + (i+1), Demo.RNG(5_000, 15_000), gStation);
			drivers.add(d);
		}
		
		//3. Create 200 random vehicles
		System.out.println("\n========== VEHICLES CREATION =========");
		ArrayList<Vehicle> vehicles = new ArrayList<>();
		ArrayList<Vehicle> trucks = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			VehicleTypes[] vehTypes = VehicleTypes.values();
			VehicleTypes type = vehTypes[Demo.RNG(vehTypes.length)];
			
			Vehicle veh = new Vehicle("M " + (i+1), Demo.RNG(2000, 2018), type);
			vehicles.add(veh);
			if(veh.getType().equals(VehicleTypes.TRUCK)) {
				trucks.add(veh);
			}
		}
		
		//Assign vehicles to drivers
		Iterator<Driver> it = drivers.iterator();
		while(it.hasNext()) {
			Driver dr = it.next();
			
			for (int i = 0; i < 10; i++) {
				Vehicle veh = vehicles.get(Demo.RNG(vehicles.size()));
				dr.addVehicle(veh);
				vehicles.remove(veh);
			}
		}
		
		//4. Drivers buy vignettes
		for (int i = 0; i < drivers.size(); i++) {
			Driver dr = drivers.get(i);
			if((i+1) % 3 == 0) {
				for (int j = 0; j < 5; j++) {
					int vehicleNumber = Demo.RNG(dr.getVehicles().size()) + 1;
					VignetteDurations[] vDurations = VignetteDurations.values(); 
					VignetteDurations duration = vDurations[Demo.RNG(vDurations.length)];
					
					dr.buyVignetteForVehicle(vehicleNumber, duration);
				}
			}
			else {
				dr.buyVignettesForAllVehicles();
			}
		}
		
		//5. Print driver info
		for (int i = 0; i < drivers.size(); i++) {
			Driver dr = drivers.get(i);
			System.out.println(dr);
			dr.printAllCars();
			dr.printVehiclesWithoutVignettesForDate(LocalDate.now().plusDays(Demo.RNG(1, 30)));
			System.out.println();
		}
		//6.Print gasstation
		System.out.println(gStation);
		gStation.printVignettes();
		
		//7. Print all trucks
		for (int i = 0; i < trucks.size(); i++) {
			Vehicle veh = trucks.get(i);
			if(veh.getType().equals(VehicleTypes.TRUCK) && veh.hasVignetteForDate(LocalDate.now().plusDays(Demo.RNG(1,5)))) {
				System.out.println(veh);
			}
		}
	}
}
