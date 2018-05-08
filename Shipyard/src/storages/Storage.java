package storages;

import java.util.LinkedList;
import java.util.Queue;

import demo.Shipyard;
import docks.Crane;
import docks.Dock;
import docks.InvalidRecordDataException;
import docks.StorageRecord;
import people.Distributor;
import people.InvalidDistributorDataException;
import shipments.Shipment;
import ships.Ship;

public class Storage {
	//Fields
	private static int currentID = 1;
	private Queue<Shipment> shipments = new LinkedList<>();
	private int id;
	private Distributor distributor;
	
	//Constructor
	public Storage() throws InvalidDistributorDataException {
		this.id = Storage.currentID++;
		this.distributor = new Distributor("Distributor " + this.id, this);
	}
	
	//Methods
	@Override
	public String toString() {
		return String.format("Storage %d", this.id);
	}
	
	public synchronized void addToStorage(Shipment shipment, Dock dock, Ship ship, Crane crane) {
		this.shipments.offer(shipment);
		
		//Create record
		try {
			StorageRecord record = new StorageRecord(shipment, dock, ship, crane);
			//Add record to DB
			Shipyard.saveRecordToDB(record);
		}
		catch (InvalidRecordDataException e) {
			e.printStackTrace();
		}
		notifyAll();
	}

	public synchronized Shipment removeItemFromStorage() {
		return this.shipments.poll();
	}
	
	public boolean isEmpty() {
		return this.shipments.isEmpty();
	}
}
