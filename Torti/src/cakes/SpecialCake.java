package cakes;

import demo.Demo;

public class SpecialCake extends Cake implements Comparable<Cake>{
	public enum CakeSubTypes implements ICakeSubType{
		ANNIVERSARY("Anniversary"), FIRM("Firm"), ADVERT("Advert");
		
		//Fields
		private String subType;
		
		//Constr
		private CakeSubTypes(String subType) {
			this.subType = subType;
		}
		
		@Override
		public String toString() {
			return this.subType;
		}
		

	}
	
	//Fields
	private String eventName;
	private static int sales = 0;
	
	//Constr
	public SpecialCake(String name, String description, double cost, int slices, String eventName, ICakeSubType subType) {
		super(name, description, cost, slices);
		setEventName(eventName);
		setType(Cake.Types.SP);
		setSubType(subType);
	}
	
	//Methods
	public static ICakeSubType getRNGCakeSubType() {
		ICakeSubType[] types = CakeSubTypes.values();
		return types[Demo.RNG(types.length)];
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("	EventName: %s", this.eventName);
	}
	
	//Setters
	private void setEventName(String eventName) {
		if(Demo.validStr(eventName)){
			this.eventName = eventName;
		}
	}
	
	@Override
	protected void setSubType(ICakeSubType subType) {
		if(subType instanceof SpecialCake.CakeSubTypes) {
			super.setSubType(subType);
		}
	}
	
	@Override
	public int compareTo(Cake o) {
		return Double.compare(o.getCost(), this.getCost()) >= 0 ? 1 : -1;
	}

	@Override
	public void addSale() {
		SpecialCake.sales++;
	}
	
	@Override
	public int getSales() {
		return this.sales;	
	}
}
