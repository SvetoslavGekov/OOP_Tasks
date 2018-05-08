package cakes;

import demo.Demo;

public abstract class Cake {
	
	public enum Types implements ICakeType{
		ST("Standard"), WED("Wedding"), SP("Special"), CH("Child");
		
		//Fields
		private String type;
		
		//Constr
		private Types(String type) {
			this.type = type;
		}
		
		@Override
		public String toString() {
			return this.type;
		}
		
	}
	
	//Fields
	private String name;
	private String description;
	private double cost;
	private int slices;
	private ICakeType type;
	private ICakeSubType subType;
	
	//Constr
	public Cake(String name, String description, double cost, int slices) {
		setName(name);
		setDescription(description);
		setCost(cost);
		setSlices(slices);
	}
	
	//Methods
	@Override
	public String toString() {
		return String.format("Type: %s	SubType: %s	Cost: %.2f	Slices: %d", this.type, this.subType, this.cost, this.slices);
	}
	
	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}

	private void setDescription(String description) {
		if(Demo.validStr(description)) {
			this.description = description;
		}
	}

	private void setCost(double cost) {
		this.cost = cost;
	}

	private void setSlices(int slices) {
		if(slices > 1) {
			this.slices = slices;
		}
	}

	protected void setType(ICakeType type) {
		if(type != null) {
			this.type = type;
		}
	}

	protected void setSubType(ICakeSubType subType) {
		if(subType != null) {
			this.subType = subType;
		}
	}
	
	//Getters
	public ICakeType getType() {
		return this.type;
	}
	
	public ICakeSubType getSubType() {
		return this.subType;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	protected int getSlices() {
		return this.slices;
	}
	
	public static ICakeType getRNGCakeType() {
		ICakeType[] types = Types.values();
		return types[Demo.RNG(types.length)];
	}

	public abstract void addSale();
	public abstract int getSales();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + slices;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cake other = (Cake) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (slices != other.slices)
			return false;
		return true;
	}
	
	
}
