package mebeli;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import eggs.Color;
import eggs.Egg;

public class Table {
	//Fields
	private static final int MAX_EGGS_IN_JAR = 2;
	private Map<Color,LinkedList<Egg>> jars = new TreeMap<Color,LinkedList<Egg>>();
	
	//Constructor
	public Table(){
		//Create the 5 jars
		for (Color color : Color.values()) {
			this.jars.put(color, new LinkedList<Egg>());
			System.out.println("Jar with color " + color + " created");
		}
	}
	
	//Methods
	public boolean hasFreeJars() {
		for (Entry<Color,LinkedList<Egg>> jar : this.jars.entrySet()) {
			//If jar has space in it
			if(jar.getValue().size() < MAX_EGGS_IN_JAR) {
				return true;
			}
		}
		//If no free jars
		return false;
	}
	
	public boolean jarHasSpace(LinkedList<Egg> linkedList) {
		if(linkedList.size() < MAX_EGGS_IN_JAR) {
			return true;
		}
		return false;
	}
	
	public Map<Color, LinkedList<Egg>> getJars() {
		return Collections.unmodifiableMap(jars);
	}

	public void putEggInJar(LinkedList<Egg> jar, Egg egg, Color color, int jarId) {
		//Put egg in jar and notify moms
		jar.offer(egg);
		//Start coloring egg
		this.colorEgg(egg,color, jarId);
		synchronized (this) {
			notifyAll();
		}
	}

	private void colorEgg(Egg egg, Color color, int jarId) {
		synchronized (egg) {
			egg.getColored(color, jarId);
			while(!egg.isReady()) {
				try {
					egg.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			synchronized (this) {
				//When egg gets ready --> notify everyone
				notifyAll();
			}
		}
	}

	public boolean jarHasReadyEgg(LinkedList<Egg> jar) {
		for (Egg egg : jar) {
			if(egg.isReady()) {
				return true;
			}
		}
		return false;
	}
	
	public synchronized boolean hasReadyEggs() {
		for (Entry<Color,LinkedList<Egg>> jar : this.jars.entrySet()) {
			for (Egg egg : jar.getValue()) {
				if(egg.isReady()) {
					notifyAll();
					return true;
				}
			}
		}
		return false;
	}

	public Egg grabEggFromJar(LinkedList<Egg> jar) {
		Egg nextEgg = null;
		for (Egg egg : jar) {
			if(egg.isReady()) {
				nextEgg = egg;
				break;
			}
		}
		if(jar.contains(nextEgg)) {
			jar.remove(nextEgg);
		}
		return nextEgg;
	}
}
