package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

import people.Brewer;
import products.FruitType;
import products.InvalidRakiaDataException;
import products.Rakia;

public class Distiller {

	private static final int MIN_RAKIA_FRUITS = 10;
	// Fields
	private BlockingQueue<FruitType> fruits = new LinkedBlockingDeque<>();
	private static int currentId = 1;
	private int id;

	// Constructor
	public Distiller() {
		this.id = currentId++;
	}

	// Methods
	@Override
	public String toString() {
		return String.format("Distiller: %d", this.id);
	}
	
	public synchronized boolean canMakeRakia(FruitType fruit) {
		if (this.fruits.size() >= MIN_RAKIA_FRUITS && this.fruits.contains(fruit)) {
			return true;
		}
		return false;
	}

	public synchronized boolean canPutFruit(FruitType fruit) {
		if (this.fruits.isEmpty() || this.fruits.contains(fruit)) {
			return true;
		}
		return false;
	}

	public void putFruits(ArrayList<FruitType> fruits2) {
		this.fruits.addAll(fruits2);
		if (this.fruits.size() >= MIN_RAKIA_FRUITS) {
			synchronized (Village.distillers) {
				Village.distillers.notifyAll();
			}
		}
	}

	public synchronized Rakia brewRakia(Brewer brewer) {
		//Create 1 batch of rakia
		Rakia rakia = null;
		try {
			rakia = new Rakia(brewer.getFruit(), brewer, Village.RNG(this.fruits.size()/4, this.fruits.size()/2));
		}
		catch (InvalidRakiaDataException e) {
			e.printStackTrace();
		}
				
		//Remove all fruits from distiller
		this.fruits.clear();
		//Add rakia to statistical collection
		Village.addRakia(brewer.getFruit(), rakia.getLitres());
		return rakia;
	}
	
	public int getDistilerCurrentSize() {
		return this.fruits.size();
	}
}
