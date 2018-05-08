package weapons;

public abstract class Weapon {
	
	public enum WeaponType{
		PISTOL, RIFLE, BOMB;
		
	}
	
	//Fields
	private WeaponType type;
	double cost;
	
	//Constr
	public Weapon(WeaponType type, double cost) {
		setType(type);
		setCost(cost);
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("Type: %s 	Cost: %.2f", this.type, this.cost);
	}
	
	//Methods
	public abstract int fire();
	
	//Setters
	private void setType(WeaponType type) {
		if(type != null) {
			this.type = type;
		}
	}

	private void setCost(double cost) {
		if(cost > 0d) {
			this.cost = cost;
		}
	};
	
	//Getters
	public WeaponType getType() {
		return this.type;
	}
	
	public double getCost() {
		return this.cost;
	}
}
