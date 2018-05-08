package people;

import demo.Shipyard;
import shipments.Shipment;
import storages.Storage;

public class Distributor {
	//Fields
	private String name;
	private Storage storage;

	public Distributor(String name, Storage storage) throws InvalidDistributorDataException {
		setName(name);
		setStorage(storage);
		Thread t = new Thread(() ->{
			startWorking();
		});
		t.start();
	}
	
	
	//Methods
	
	@Override
	public String toString() {
		return String.format("Distributor %s", this.name);
	}
	
	public void startWorking() {
		while(true) {
			//While storage is empty --> wait
			while(storage.isEmpty()) {
				try {
					synchronized (storage) {
						storage.wait();
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			//Else --> remove 1 item and sleep 5 seconds
			try {
				Shipment shipment = this.storage.removeItemFromStorage();
				System.out.printf("%s removed %s from %s %n%n",this, shipment, this.storage);
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	//Setters
	public void setName(String name) throws InvalidDistributorDataException {
		if(Shipyard.validStr(name)) {
			this.name = name;
		}
		else {
			throw new InvalidDistributorDataException("Invalid distributor name.");
		}
	}
	
	//Getters
	public String getName() {
		return this.name;
	}

	public void setStorage(Storage storage) {
		if(storage != null) {
			this.storage = storage;
		}
	}
	public Storage getStorage() {
		return this.storage;
	}
}
