package people;

import demo.GasStation;

public abstract class Person {
	//Fields
	private String name;

	//Constructor
	public Person(String name) throws InvalidPersonDataException {
		setName(name);
		System.out.println(this);
	}

	//Methods
	@Override
	public String toString() {
		return String.format("%s", this.name);
	}
	
	//Getters
	public String getName() {
		return this.name;
	}

	//Setters
	private void setName(String name) throws InvalidPersonDataException {
		if(GasStation.validStr(name)) {
			this.name = name;
		}
		else {
			throw new InvalidPersonDataException("Invalid person name.");
		}
	}
	
	

}
