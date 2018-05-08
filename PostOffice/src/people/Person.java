package people;

import demo.Demo;

public abstract class Person {
	//Fields
	private String names;
	
	//Constructor
	public Person(String names) throws InvalidPersonDataException {
		setNames(names);
	}
	
	//Setters
	public void setNames(String names) throws InvalidPersonDataException {
		if(Demo.validStr(names)) {
			this.names = names;
		}
		else {
			throw new InvalidPersonDataException("Invalid person names.");
		}
	}
	
	//Getters
	public String getNames() {
		return this.names;
	}
}
