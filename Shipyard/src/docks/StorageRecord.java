package docks;

import java.time.LocalDateTime;

import shipments.Shipment;
import ships.Ship;

public class StorageRecord {
	//Fields
	private Shipment shipment;
	private Dock dock;
	private Ship ship;
	private Crane crane;
	private LocalDateTime unloadingTime;
	
	//Constructors
	public StorageRecord(Shipment shipment, Dock dock, Ship ship, Crane crane) throws InvalidRecordDataException {
		setShipment(shipment);
		setDock(dock);
		setShip(ship);
		setCrane(crane);
		this.unloadingTime = LocalDateTime.now();
	}

	//Setters
	private void setShipment(Shipment shipment) throws InvalidRecordDataException {
		if(shipment != null) {
			this.shipment = shipment;
		}
		else {
			throw new InvalidRecordDataException("Invalid ship");
		}
	}

	private void setDock(Dock dock) throws InvalidRecordDataException {
		if(dock != null) {
			this.dock = dock;
		}
		else {
			throw new InvalidRecordDataException("Invalid dock");
		}
	}

	private void setShip(Ship ship) throws InvalidRecordDataException {
		if(ship != null) {
			this.ship = ship;
		}
		else {
			throw new InvalidRecordDataException("Invalid ship");
		}
	}

	private void setCrane(Crane crane) throws InvalidRecordDataException {
		if(crane != null) {
			this.crane = crane;
		}
		else {
			throw new InvalidRecordDataException("Invalid crane");
		}
	}

	public LocalDateTime getUnloadingTime() {
		return this.unloadingTime;
	}

	public Shipment getShipment() {
		return this.shipment;
	}

	public Dock getDock() {
		return this.dock;
	}

	public Ship getShip() {
		return this.ship;
	}

	public Crane getCrane() {
		return this.crane;
	}

}
