package ships;

import java.util.ArrayList;
import java.util.LinkedList;

import demo.Shipyard;
import docks.Dock;
import shipments.Shipment;

public class Ship {
	// Fields
	private static final int MIN_SHIPMENTS = 1;
	private static final int MAX_SHIPMENTS = 4;
	private String name;
	private LinkedList<Shipment> shipments = new LinkedList<>();
	private Dock currentDock = null;

	// Constructors
	public Ship(String name) throws InvalidShipDataException {
		setName(name);
		supplyShip();
		
		Thread t = new Thread(() -> {
			this.goToDock();
		});
		t.start();
	}

	// Methods
	private synchronized void goToDock() {
		while (true) {
			//Choose a dock
			Dock dock = getRandomDock();
			//Set current dock
			setCurrentDock(dock);
			
			//Enter dock's waiting list
			dock.addShipToWaitingList(this);
			//Wait until cranes come for your shipment
			while(!isShipEmpty()) {
				try {
					synchronized (dock) {
						dock.wait();
					}
					
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Sleep for a bit
			System.out.printf("%s was successfully unloaded.%n", this.getName());
			
			try {
				Thread.sleep(25_000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			setCurrentDock(null);
			//Ressuply and go again
			this.supplyShip();
		}
	}
	
	public boolean isShipEmpty() {
		return this.shipments.isEmpty();
	}
	
	public Shipment removeShipmentFromShip() {
		return this.shipments.poll();
	}

	private Dock getRandomDock() {
		ArrayList<Dock> docks = new ArrayList<>(Shipyard.docks);
		return docks.get(Shipyard.RNG(docks.size()));
	}

	private void supplyShip() {
		for (int i = 0; i < Shipyard.RNG(MIN_SHIPMENTS, MAX_SHIPMENTS + 1); i++) {
			this.shipments.add(new Shipment());
		}
		System.out.printf("%s was successfully supplied with %d shipments%n%n", this.getName(), this.shipments.size());
	}

	// Setters
	public void setName(String name) throws InvalidShipDataException {
		if (Shipyard.validStr(name)) {
			this.name = name;
		}
		else {
			throw new InvalidShipDataException("Invalid ship name");
		}
	}

	// Getters
	public String getName() {
		return this.name;
	}
	
	public void setCurrentDock(Dock currentDock) {
		this.currentDock = currentDock;
	}
	
	public Dock getCurrentDock() {
		return this.currentDock;
	}
}
