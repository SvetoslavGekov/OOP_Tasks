package docks;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import demo.Shipyard;
import shipments.Shipment;
import ships.Ship;
import storages.Storage;

public class Crane {
	// Fields
	private static int currentId = 1;
	private static Queue<Ship> waitingShips = new LinkedBlockingQueue<>();
	private int id;

	// Constructor
	public Crane() {
		this.id = currentId++;

		Thread t = new Thread(() -> {
			this.unloadShips();
		});
		t.start();
	}

	// Method
	@Override
	public String toString() {
		return String.format("Crane %d", this.id);
	}

	public synchronized void unloadShips() {
		while (true) {
			// Check if there are any ships to be unloaded
			if (waitingShips.isEmpty()) {
				try {
					Thread.sleep(3_000);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else {
				// Grab next ship
				Ship ship = waitingShips.poll();

				// Start unloading and putting in the storages
				while (!ship.isShipEmpty()) {
					Shipment shipment = ship.removeShipmentFromShip();

					// Get random storage and add into it
					Storage storage = Shipyard.getRandomStorage();
					try {
						Thread.sleep(shipment.getCraneTime() * 1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					storage.addToStorage(shipment, ship.getCurrentDock(), ship, this);
					System.out.printf("%s successfully added to %s%n", shipment, storage);
				}
				synchronized (ship.getCurrentDock()) {
					ship.getCurrentDock().notifyAll();
				}

			}
		}
	}

	public static void addShipToWaitingList(Ship ship) {
		if (ship != null) {
			Crane.waitingShips.add(ship);
			System.out.printf("%s added the cranes waiting list.%n", ship.getName());
		}
	}

	public int getId() {
		return this.id;
	}
}
