package people;

import cars.Car;
import demo.CashDesk;
import demo.GasPump;
import demo.GasStation;

public class Worker extends Person {
	//Fields
	private static final int FUELING_TIME = 5;
	
	
	//Constructor
	public Worker(String name) throws InvalidPersonDataException {
		super(name);
		
		Thread t = new Thread(()->{
			this.lookForWaitingCars();
		});
		t.start();
	}

	
	//Methods
	public void lookForWaitingCars() {
		//If no cars need servicing --> go take a break
		while(true) {
			while(!GasStation.hasWaitingCars()) {
				synchronized (GasStation.pumps) {
					try {
						GasStation.pumps.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			
			//Check if you have cars waiting to be serviced
			for (GasPump pump : GasStation.pumps) {
				synchronized (pump) {
					if(pump.hasWaitingCars()) {
						//Take car and fuel it
						Car car = pump.getNextCar();
						
						//Sleep for fueling time
						try {
							Thread.sleep(FUELING_TIME * 1000);
						}
						catch (InterruptedException e) {
							e.printStackTrace();
						}
						this.refuelCar(car);
						
						//Add car to cashdesk queue
						CashDesk cashDesk = GasStation.getRandomCashDesk();
						cashDesk.enterCashDeskWaitingList(car);
					}
				}
			}
		}
	}


	private void refuelCar(Car car) {
		car.setFueled(true);
		car.setLitres(GasStation.RNG(car.MIN_LITRES, car.MAX_LITRES + 1));
		System.out.printf("		%s refueled %s with %d litres of %s%n%n", this, car, car.getLitres(), car.getFuel());
	}
}
