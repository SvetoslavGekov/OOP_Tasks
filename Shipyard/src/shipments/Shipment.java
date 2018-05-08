package shipments;

public class Shipment {
	//Fields
	private static final int CRANE_TIME = 2;
	private static int currentID = 1;
	private int id;
	
	//Constructor
	public Shipment() {
		this.id = Shipment.currentID++;
	}
	
	//Methods
	@Override
	public String toString() {
		return String.format("Shipment: %s", this.id);
	}
	
	//Getters
	public int getId() {
		return this.id;
	}
	
	public static int getCraneTime() {
		return Shipment.CRANE_TIME;
	}
}
