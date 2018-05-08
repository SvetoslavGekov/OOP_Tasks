package cakes;

import java.util.Enumeration;

import demo.Demo;

public final class StandardCake extends Cake implements Comparable<Cake> {
	
	public enum CakeSubTypes implements ICakeSubType{
		BIS("Biscuit"), ECCLAIR("ECCLAIR"), FRUIT("Fruit"), CHOCO("Choco");
		
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
	private boolean hasSyrup;
	private static int sales = 0;
	
	//Constr
	public StandardCake(String name, String description, double cost, int slices, boolean hasSyrup, ICakeSubType subType) {
		super(name, description, cost, slices);
		this.hasSyrup = hasSyrup;
		setType(Cake.Types.ST);
		setSubType(subType);
	}
	
	//Methods
	public static ICakeSubType getRNGCakeSubType() {
		ICakeSubType[] types = CakeSubTypes.values();
		return types[Demo.RNG(types.length)];
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("	HasSyrup: %s", this.hasSyrup);
	}
	
	@Override
	protected void setSubType(ICakeSubType subType) {
		if(subType instanceof StandardCake.CakeSubTypes) {
			super.setSubType(subType);
		}
	}

	@Override
	public int compareTo(Cake o) {
		return Double.compare(o.getCost(), this.getCost()) >= 0 ? 1 : -1;
	}
	
	@Override
	public void addSale() {
		StandardCake.sales++;
	}
	
	@Override
	public int getSales() {
		return this.sales;
	}
}
