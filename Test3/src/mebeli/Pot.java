package mebeli;

import java.util.LinkedList;
import java.util.Queue;

import eggs.Egg;
import eggs.EggType;

public class Pot {
	//Fields
	private static final int STARTING_EGGS = 50; 
	private Queue<Egg> eggs = new LinkedList<>();


	//Constructor
	public Pot() {
		//Create eggs when creating the pot
		for (int i = 0; i < STARTING_EGGS; i++) {
			eggs.add(new Egg(EggType.getRandomEggType()));
		}
	}


	public Egg getNextEgg() {
		//Grab next available egg
		return this.eggs.poll();
	}


	public boolean hasEggs() {
		return !this.eggs.isEmpty();
	}
}
