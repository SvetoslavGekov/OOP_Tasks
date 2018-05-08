package people;

import demo.Village;
import products.FruitType;

public abstract class Person {
	//Fields
	private String name;
	private int age;
	private FruitType fruit;
	
	//Constructor
	public Person(String name, int age, FruitType fruit) throws InvalidPersonDataException {
		setName(name);
		setAge(age);
		setFruit(fruit);
		System.out.println(this);
	}
	
	//Methods
	
	@Override
	public String toString() {
		return String.format("%s	Age: %d	FruitType: %s", this.name, this.age, this.fruit);
	}

	//Setters
	private void setName(String name) throws InvalidPersonDataException {
		if(Village.validStr(name)) {
			this.name = name;
		}
		else {
			throw new InvalidPersonDataException("Invalid person name.");
		}
	}

	private void setAge(int age) throws InvalidPersonDataException {
		if(age > 0) {
			this.age = age;
		}
		else {
			throw new InvalidPersonDataException("Invalid person age.");
		}
	}

	private void setFruit(FruitType fruit) throws InvalidPersonDataException {
		if(fruit != null) {
			this.fruit = fruit;
		}
		else {
			throw new InvalidPersonDataException("Invalid person fruit.");
		}
	}

	//Getters
	public String getName() {
		return this.name;
	}

	public int getAge() {
		return this.age;
	}

	public FruitType getFruit() {
		return this.fruit;
	}

	
}
