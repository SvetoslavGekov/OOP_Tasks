package products;

import java.time.LocalDate;

import demo.Village;
import people.Brewer;

public class Rakia {
	//Fields
	private LocalDate date;
	private FruitType fruit;
	private Brewer brewer;
	private int litres;
	
	//Constructor
	public Rakia( FruitType fruit, Brewer brewer, int litres) throws InvalidRakiaDataException {
		this.date = LocalDate.now();
		setFruit(fruit);
		setBrewer(brewer);
		setLitres(litres);
		Village.saveRakia(this);
	}

	//Setters
	private void setFruit(FruitType fruit) throws InvalidRakiaDataException {
		if(fruit != null) {
			this.fruit = fruit;
		}
		else {
			throw new InvalidRakiaDataException("Invalid rakia fruit");
		}
	}

	private void setBrewer(Brewer brewer) throws InvalidRakiaDataException {
		if(brewer != null) {
			this.brewer = brewer;
		}
		else {
			throw new InvalidRakiaDataException("Invalid rakia brewer");
		}
	}

	private void setLitres(int litres) throws InvalidRakiaDataException {
		if(litres > 0) {
			this.litres = litres;
		}
		else {
			throw new InvalidRakiaDataException("Invalid rakia litres");
		}
	}

	public LocalDate getDate() {
		return this.date;
	}

	public FruitType getFruit() {
		return this.fruit;
	}

	public Brewer getBrewer() {
		return this.brewer;
	}

	public int getLitres() {
		return this.litres;
	}	

	
}
