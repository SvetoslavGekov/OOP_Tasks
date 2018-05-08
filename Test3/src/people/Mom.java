package people;

import java.util.LinkedList;
import java.util.Map.Entry;

import demo.Demo;
import eggs.Color;
import eggs.Egg;
import mebeli.Fridge;
import mebeli.Table;

public class Mom extends Person {
	//Fields
	private static final int SWEEP_TIME = 2; //seconds
	private static final int PAMUK_TIME = 5;
	private static int currentId = 1;
	private int id;
	private Table table;
	private Fridge fridge;
	private Father father;
	
	//Constructor
	public Mom(Table table, Fridge fridge) {
		super("Mom " + currentId);
		this.id = currentId++;
		setTable(table);
		setFridge(fridge);
		this.father = new Father(fridge);
		System.out.println(this);
		
		Thread t = new Thread(()->{
			while(true) {
				startGrabbingEggsFromTable();
			}
		});
		t.start();
	}
	
	
	
	//Methods
	
	private void startGrabbingEggsFromTable() {
		//If there are no eggs ready to be collected
		
		while(!table.hasReadyEggs()) {
			synchronized (table) {
				try {
					table.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		//If there are any eggs to be collected
		for (Entry<Color,LinkedList<Egg>> jars : this.table.getJars().entrySet()) {
			//Check every jar for a ready egg
			LinkedList<Egg> jar = jars.getValue();
			if(table.jarHasReadyEgg(jar)) {
				//Synch the jarr and grab an egg
				synchronized (jar) {
					Egg egg = table.grabEggFromJar(jar);
					System.out.println(this + " grabbed" + egg + "from jar " + jars.getKey().getId());
					//Sweep egg
					try {
						Thread.sleep(SWEEP_TIME* 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					//Attempt to make egg party colored
					partyColorEgg(egg);
					
					//Put egg in fridge
					this.fridge.putEggInFridge(egg, this);
					
					//Tell father to save egg in db and archive eggs
//					this.father.saveEggInDb(egg);
					this.father.archiveEggs();
				}
			}
		}
		
	}
	
	private void partyColorEgg(Egg egg) {
		//Roll 20% chance to make it party colored
		if(Demo.RNG(5) < 1) {
			try {
				Thread.sleep(PAMUK_TIME * 1000);
				egg.setPartyColored(true);
				
				System.out.println(this + " made " + egg + " with party colors.");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String toString() {
		return String.format("%s", this.getName());
	}
	
	public void setFridge(Fridge fridge) {
		if(fridge != null) {
			this.fridge = fridge;
		}
	}
	
	public void setTable(Table table) {
		if(table != null) {
			this.table = table;
		}
	}
	
	public Table getTable() {
		return table;
	}
}
