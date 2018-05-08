package people;

import demo.Distiller;
import demo.Village;
import products.FruitType;
import products.Rakia;

public class Brewer extends Person {
	private static int currentId = 1;
	private int id;
	//Constructors
	public Brewer(String name, int age, FruitType fruit) throws InvalidPersonDataException {
		super(name, age, fruit);
		this.id = currentId++;
		Village.saveBrewer(this);
		Thread t = new Thread(()->{
			while(true) {
				makeRakia();
			}
		}) ;
		t.start();
	}

	
	//Methods
	
	public void makeRakia() {
		//Wait while there is no distiller ready to be brewed
		while(!Village.hasAReadyDistiller(this.getFruit())) {
			synchronized (Village.distillers) {
				try {
					Village.distillers.wait();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		

		//Iterate over distillers and try to make rakia
		for (Distiller distiller : Village.distillers) {
			//If distiller can be brewed into rakia --> go for it
			if(distiller.canMakeRakia(this.getFruit())) {
				//Lock distiller and make rakia
				synchronized (distiller) {
					try {
						Thread.sleep(1000);
						Rakia rakia = distiller.brewRakia(this);
						System.out.printf("			%s brewed %d litres of %s rakia.%n%n", this.getName(), rakia.getLitres(), rakia.getFruit());
						synchronized (Village.distillers) {
							Village.distillers.notifyAll();
						}
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
}
