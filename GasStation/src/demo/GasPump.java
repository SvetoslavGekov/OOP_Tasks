package demo;

import java.util.Map.Entry;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import cars.Car;
import cars.FuelType;

public class GasPump {
	//Fields
	private static int currentId = 1;
	private int id;
	private Queue<Car> waitingCars = new LinkedBlockingDeque<>();
	
	//Constructors
	public GasPump() {
		this.id = currentId++;
		System.out.println(this);
	}
	
	//Methods
	
	
	@Override
	public String toString() {
		return String.format("Gas Pump %d", this.id);
	}

	public void enterPumpWaitingList(Car car) {
		this.waitingCars.offer(car);
		System.out.printf("%s entered %s's waiting list.%n", car, this);
		synchronized (GasStation.pumps) {
			GasStation.pumps.notifyAll();
		}
	}

	public boolean hasWaitingCars() {
		return !this.waitingCars.isEmpty();
	}

	public Car getNextCar() {
		return this.waitingCars.poll();
	}
	
	public int getId() {
		return this.id;
	}
	
}
