package people;

import demo.Demo;

public class Person {
	//Fields
	private String name;

	//Constructor
	public Person(String name) {
		setName(name);
	}
	
	//Getters
	public String getName() {
		return name;
	}

	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}
}
