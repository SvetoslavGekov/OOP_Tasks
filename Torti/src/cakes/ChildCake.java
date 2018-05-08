package cakes;

import cakes.SpecialCake.CakeSubTypes;
import demo.Demo;

public class ChildCake extends Cake implements Comparable<Cake>{
	public enum CakeSubTypes implements ICakeSubType{
		BIRTHDAY("BIRTHDAY"), NAMING("Naming"), PRP("PRP");
		
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
	private String childName;
	private static int sales = 0;
	
	//Constr
	public ChildCake(String name, String description, double cost, int slices, String childName, ICakeSubType subType) {
		super(name, description, cost, slices);
		setChildName(childName);
		setType(Cake.Types.CH);
		setSubType(subType);
		System.out.println(this);
	}

	//Methods
	public static ICakeSubType getRNGCakeSubType() {
		ICakeSubType[] types = CakeSubTypes.values();
		return types[Demo.RNG(types.length)];
	}
	
	@Override
	public String toString() {
		return super.toString() + String.format("	ChildName: %s", this.childName);
	}
	
	//Setters
	private void setChildName(String childName) {
		if(Demo.validStr(childName)) {
			this.childName = childName;
		}
	}
	
	@Override
	protected void setSubType(ICakeSubType subType) {
		if(subType instanceof ChildCake.CakeSubTypes) {
			super.setSubType(subType);
		}
	}
	
	@Override
	public int compareTo(Cake o) {
		return (this.getSlices() - o.getSlices()) >= 0 ? 1 : -1;  
	}
	
	@Override
	public void addSale() {
		ChildCake.sales++;
	}
	
	
	@Override
	public int getSales() {
		return this.sales;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((childName == null) ? 0 : childName.hashCode());
		return result + super.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ChildCake other = (ChildCake) obj;
		if (childName == null) {
			if (other.childName != null)
				return false;
		}
		else if (!childName.equals(other.childName))
			return false;
		return true;
	}
	
	
}
