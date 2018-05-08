package docks;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import ships.Ship;


public class Dock {
	//Fields
	private int number;
	private Queue<Ship> ships = new LinkedBlockingQueue<>();

	//Constructors
	public Dock(int number) throws InvalidDockDataException {
		setNumber(number);
		
		Thread t = new Thread(() -> {
			this.serviceShips();
		});
		t.start();
	}

	//Methods
	@Override
	public String toString() {
		return String.format("Dock %d", this.number);
	}
	
	public synchronized void serviceShips() {
		while(true) {
			//Check if there are ships waiting
			if(!this.ships.isEmpty()) {
				Ship ship = this.ships.poll();
				//Add to cranes waiting list 
				Crane.addShipToWaitingList(ship);
				
				//Wait for a crane to unload a ship
				while(!ship.isShipEmpty()) {
					try {
						wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	
	public void addShipToWaitingList(Ship ship) {
		if(ship != null) {
			this.ships.add(ship);
			System.out.printf("Added %s to %s's waiting list.%n", ship.getName(), this);
		}	
	}
	
	//Setters
	public void setNumber(int number) throws InvalidDockDataException {
		if(number > 0) {
			this.number = number;
		}
		else {
			throw new InvalidDockDataException("Invalid dock number.");
		}
	}
	
	//Getters
	public int getNumber() {
		return this.number;
	}
}
