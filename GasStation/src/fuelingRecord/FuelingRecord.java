package fuelingRecord;

import java.time.LocalDateTime;

import cars.FuelType;
import demo.GasPump;

public class FuelingRecord {
	// Fields
	private GasPump pump;
	private FuelType fuel;
	private int litres;
	private LocalDateTime fueling_time;

	// Constructor
	public FuelingRecord(GasPump pump, FuelType fuel, int litres)
			throws InvalidFuelingRecordDataException {
		setPump(pump);
		setFuel(fuel);
		setLitres(litres);
		this.fueling_time = LocalDateTime.now();
	}

	// Setters
	private void setPump(GasPump pump) throws InvalidFuelingRecordDataException {
		if (pump != null) {
			this.pump = pump;
		}
		else {
			throw new InvalidFuelingRecordDataException("Invalid record gas pump.");
		}
	}

	private void setFuel(FuelType fuel) throws InvalidFuelingRecordDataException {
		if (fuel != null) {
			this.fuel = fuel;
		}
		else {
			throw new InvalidFuelingRecordDataException("Invalid record fuel.");
		}
	}

	private void setLitres(int litres) throws InvalidFuelingRecordDataException {
		if (litres > 0) {
			this.litres = litres;
		}
		else {
			throw new InvalidFuelingRecordDataException("Invalid record litres.");
		}
	}

	// Getters

	public GasPump getPump() {
		return this.pump;
	}

	public FuelType getFuel() {
		return this.fuel;
	}

	public int getLitres() {
		return this.litres;
	}

	public LocalDateTime getFueling_time() {
		return this.fueling_time;
	}

}
