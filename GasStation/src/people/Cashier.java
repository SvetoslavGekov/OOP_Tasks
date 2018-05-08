package people;

import cars.Car;
import demo.CashDesk;
import demo.GasStation;
import fuelingRecord.FuelingRecord;
import fuelingRecord.InvalidFuelingRecordDataException;

public class Cashier extends Person {
	private static int currentId = 1;
	private int id;
	private CashDesk cashDesk;

	// Constructors
	public Cashier(CashDesk cashDesk) throws InvalidPersonDataException {
		super("Cashier " + currentId++);
		setCashDesk(cashDesk);
		Thread t = new Thread(()->{
			this.serviceCars();
		});
		t.start();
	}

	// Methods
	public void serviceCars() {
		// If no cars need servicing --> go take a break
		while (true) {
			while (!this.cashDesk.hasWaitingCars()) {
				synchronized (this.cashDesk) {
					try {
						this.cashDesk.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			// If cars need servicing --> service next car
			// Get next car
			Car car = this.cashDesk.getNextCar();
			try {
				Thread.sleep(5000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			//Create fueling record and add it to gasstation
			
			try {
				FuelingRecord record = new FuelingRecord(car.getPump(), car.getFuel(), car.getLitres());
				//Add record to gasStation
				GasStation.addFuelingRecord(record);
			}
			catch (InvalidFuelingRecordDataException e) {
				e.printStackTrace();
			}
			
			System.out.printf("						%s payed fuel at %s with cashier %s %n", car,this.cashDesk, this);
			//Release car from gasStation
			car.setHasPayed(true);
			synchronized (car.getPump()) {
				car.getPump().notifyAll();
			}
		}
	}

	// Setters
	public void setCashDesk(CashDesk cashDesk) {
		if (cashDesk != null) {
			this.cashDesk = cashDesk;
		}
	}
}
