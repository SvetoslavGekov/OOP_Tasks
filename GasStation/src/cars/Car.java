package cars;

import demo.GasPump;
import demo.GasStation;

public class Car {
	//Fields
	public static final int MIN_LITRES = 10;
	public static final int MAX_LITRES = 40;
	private static int currentId = 1;
	private int id;
	private FuelType fuel;
	private boolean isFueled = false;
	private boolean hasPayed = false;
	private GasPump pump;
	private int litres;
	
	//Constructors
	public Car() {
		this.id = currentId++;
		System.out.println(this);
		
		Thread t = new Thread(()->{
			while(true) {
				this.goForRefuel();
			}
		});
		t.start();
	}

	//Methods
	
	private void goForRefuel() {
		//Choose random gas pump
		GasPump pump = GasStation.getRandomGasPump();
		setPump(pump);
		//Choose fuel
		setFuel(FuelType.getRandomFuelType());
		//Attempt to enter gas pump waiting list
		pump.enterPumpWaitingList(this);
		
		//Wait to be serviced by worker
		while(!this.isFueled || !this.hasPayed) {
			synchronized (pump) {
				try {
					pump.wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		//Remove fuel and come again
		System.out.printf("%s left %s.%n%n", this, this.getPump());
		setFueled(false);
		setHasPayed(false);
		setPump(null);
		//Wait a bit and come again
		try {
			Thread.sleep(10000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
	
	
	
	@Override
	public String toString() {
		return String.format("Car %d", this.id);
	}
	
	//Getters
	public int getId() {
		return this.id;
	}
	
	public FuelType getFuel() {
		return this.fuel;
	}
	
	public GasPump getPump() {
		return this.pump;
	}
	
	public int getLitres() {
		return this.litres;
	}
	
	//Setters
	public void setFuel(FuelType fuel) {
		if(fuel != null) {
			this.fuel = fuel;
		}
	}
	
	public void setPump(GasPump pump) {
		this.pump = pump;
	}
	
	public void setFueled(boolean isFueled) {
		this.isFueled = isFueled;
	}
	
	public void setLitres(int litres) {
		if(litres > 0) {
			this.litres = litres;
		}
	}
	public void setHasPayed(boolean hasPayed) {
		this.hasPayed = hasPayed;
	}
}
