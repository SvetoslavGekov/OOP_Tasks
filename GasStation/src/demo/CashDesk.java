package demo;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingDeque;

import cars.Car;
import people.Cashier;
import people.InvalidPersonDataException;

public class CashDesk {
	//Fields
	private static int currentId = 1;
	private int id;
	private Queue<Car> waitingCars = new LinkedBlockingDeque<>();
	private Cashier cashier;
	
	//Constructors
	public CashDesk() throws InvalidPersonDataException {
		this.id = currentId++;
		this.cashier = new Cashier(this);
		System.out.println(this);
	}

	//Methods
	@Override
	public String toString() {
		return String.format("Cash desk %d", this.id);
	}
	
	public void enterCashDeskWaitingList(Car car) {
		this.waitingCars.offer(car);
		System.out.printf("%s entered %s's waiting list.%n", car, this);
		synchronized (this) {
			notifyAll();
		}
	}
	
	public boolean hasWaitingCars() {
		return !this.waitingCars.isEmpty();
	}

	public Car getNextCar() {
		return this.waitingCars.poll();
	}
}
