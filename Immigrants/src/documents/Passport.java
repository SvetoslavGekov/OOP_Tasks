package documents;

import demo.Demo;
import urban.City;
import urban.Country;

public class Passport {
	//Fields
	private String name;
	private int age;
	private Country birthCountry;
	private City birthCity;
	
	//Constr
	public Passport(String name, int age, Country birthCountry, City birthCity) {
		setName(name);
		setAge(age);
		setBirthCity(birthCity);
		setBirthCountry(birthCountry);
	}

	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}


	private void setAge(int age) {
		if(age > 0) {
			this.age = age;
		}
	}


	private void setBirthCountry(Country birthCountry) {
		if(birthCountry != null) {
			this.birthCountry = birthCountry;
		}
	}


	private void setBirthCity(City birthCity) {
		if(birthCity != null) {
			this.birthCity = birthCity;
		}
	}
	
	public String getName() {
		return this.name;
	}
	
}
