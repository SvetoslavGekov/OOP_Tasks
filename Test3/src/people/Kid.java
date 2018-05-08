package people;

import java.util.LinkedList;
import java.util.Map.Entry;

import eggs.Color;
import eggs.Egg;
import mebeli.Pot;
import mebeli.Table;

public class Kid extends Person {
	//Fields
	private static int currentId = 1;
	private int id;
	private Pot pot;
	private Table table;
	
	//Fields
	public Kid(Pot pot, Table table) {
		super("Kid " + currentId);
		this.id = currentId++;
		setPot(pot);
		setTable(table);
		System.out.println(this);
		
		Thread t = new Thread(()->{
			while(true) {
				startGrabbingEggs();
			}
		});
		t.start();
	}
	
	//Methods
	
	private void startGrabbingEggs() {
		//Get an egg from the pot
		Egg egg = null;
		//If pot is not empty
		if(this.pot.hasEggs()) {
			//Grab egg
			synchronized (pot) {
				egg = this.pot.getNextEgg();
				egg.setKid(this);
				System.out.println(this + " grabbed " + egg);
			}
			//Put egg in jar
			if(this.table.hasFreeJars()) {
				for (Entry<Color, LinkedList<Egg>> jars : this.table.getJars().entrySet()) {
					LinkedList<Egg> jar = jars.getValue();
					if(this.table.jarHasSpace(jar)) {
						synchronized (jars.getValue()) {
							//If jar has space --> put an egg in it
							this.table.putEggInJar(jar, egg, jars.getKey(), jars.getKey().getId());
							System.out.println(this + " put " + egg + " in " + jar);
							
							//Make the kid sleep for a second because it gets things done too fast
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							//Don't let the kid put this egg in all jars
							break;
						}
					}
				}
			}
			else {
				//wait for a jar to get free
				synchronized (table) {
					try {
						table.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}	
				}
			}
		}
		else {
			//Wait for pot to get new eggs --> not happening in this demo
			try {
				synchronized (pot) {
					pot.wait();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	
	}
	
	@Override
	public String toString() {
		return String.format("%s	Id: %d", this.getName(), this.id);
	}
	
	//Setters
	private void setPot(Pot pot) {
		if(pot != null) {
			this.pot = pot;
		}
	}
	
	private void setTable(Table table) {
		if(table != null) {
			this.table = table;
		}
	}
	
	//Getters
	public Pot getPot() {
		return pot;
	}

	public int getId() {
		return this.id;
	}
}
