package demo;

public enum VehicleTypes {
	CAR("CAR"), BUS("BUS"), TRUCK("TRUCK");
	
	//Fields
	String type;

	//Constr
	private VehicleTypes(String type) {
		setType(type);
	}
	
	//Setters
	private void setType(String type) {
		if(Demo.validStr(type)) {
			this.type = type;
		}
	}

	@Override
	public String toString() {
		return this.type;
	}
}
