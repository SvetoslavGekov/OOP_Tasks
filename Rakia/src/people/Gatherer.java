package people;

import java.util.ArrayList;

import demo.Distiller;
import demo.Village;
import products.FruitType;

public class Gatherer extends Person {
	private static final int MAX_KILOGRAMS_GATHERED = 5;
	private static final int MIN_KILOGRAMS_GATHERED = 3;

	//Fields
	private static int currentId = 1;
	private int id;
	//Constructor
	public Gatherer(String name, int age, FruitType fruit) throws InvalidPersonDataException {
		super(name, age, fruit);
		this.id = currentId++;
		Village.saveGatherer(this);
		Thread t = new Thread(()->{
			while(true) {
				gatherFruits();
			}
		}) ;
		t.start();
	}

	//Methods
	
	public void gatherFruits() {
		//Go gather for a random ammount of time
		int kilogramsGathered = Village.RNG(MIN_KILOGRAMS_GATHERED, MAX_KILOGRAMS_GATHERED + 1);
		ArrayList<FruitType> fruits = new ArrayList<>();
		for (int i = 0; i < kilogramsGathered; i++) {
			fruits.add(this.getFruit());
			//Sleep for 1/5 second after each kilogram
			try {
				Thread.sleep(500);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		//Go and place your fruits in a distiller
		while(!fruits.isEmpty()) {
			//Iterate over distillers and try to put fruit
			for (Distiller distiller : Village.distillers) {
				//If distiller can accept gatherer fruits --> put fruits
				if(distiller.canPutFruit(getFruit())) {
					synchronized (distiller) {
						//Add fruits to distiller	
						distiller.putFruits(fruits);
						//Put fruits in statistical collection
						Village.addFruit(getFruit(), fruits.size());
						System.out.printf("%s put %d %s in %s (Total fruits: %d) %n", this.getName(), fruits.size(), getFruit(), 
								distiller, distiller.getDistilerCurrentSize());
						fruits.clear();
						break;
					}
				}
			}
			if(!fruits.isEmpty()) {
				//If no distiller could accept fruits --> wait for distillers to notify gatherer for a free kazan
				synchronized (Village.distillers) {
					try {
						Village.distillers.wait();
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
