package cakes;

import demo.Demo;

public final class WeddingCake extends Cake implements Comparable<Cake> {
	
	public enum CakeSubTypes implements ICakeSubType{
		LARGE("Large"), MEDIUM("Medium"), SMALL("Small");
		
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
	private int levels;
	private static int sales = 0;
	
	//Constr
	public WeddingCake(String name, String description, double cost, int slices, int levels, ICakeSubType subType) {
		super(name, description, cost, slices);
		setLevels(levels);
		setType(Cake.Types.WED);
		setSubType(subType);
	}
	
	//Methods
	public static ICakeSubType getRNGCakeSubType() {
		ICakeSubType[] types = CakeSubTypes.values();
		return types[Demo.RNG(types.length)];
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("	Levels: %s", this.levels);
	}
	
	
	//Setters
	private void setLevels(int levels) {
		if(levels > 0) {
			this.levels = levels;
		}
	}
	
	@Override
	protected void setSubType(ICakeSubType subType) {
		if(subType instanceof WeddingCake.CakeSubTypes) {
			super.setSubType(subType);
		}
	}
	
	@Override
	public int compareTo(Cake o) {
		return (this.getSlices() - o.getSlices()) >= 0 ? 1 : -1;  
	}
	
	@Override
	public void addSale() {
		WeddingCake.sales++;
	}
	
	@Override
	public int getSales() {
		return this.sales;
	}
}
