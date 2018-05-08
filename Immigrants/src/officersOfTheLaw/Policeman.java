package officersOfTheLaw;

import java.util.Random;

import demo.Demo;
import immigrants.Immigrant;
import immigrants.NormalImmigrant;
import urban.City;
import urban.Country;

public class Policeman {

	//Fields
	private String name;
	private Country workCountry;
	private City workCity;
	
	//Constr
	public Policeman(String name, Country workCountry, City workCity) {
		setName(name);
		setWorkCity(workCity);
		setWorkCountry(workCountry);
		System.out.println(this);
	}
	
	@Override
	public String toString() {
		return String.format("%s 	%s 	%s", this.name, this.workCountry, this.workCity);
	}
	
	//Methods
	public boolean checkImmigrant(Immigrant immigrant) {
		if(immigrant.hasBomb() || immigrant instanceof NormalImmigrant) {
			return true;
		}
		
		boolean isGettingcaught = new Random().nextBoolean(); 
		return !isGettingcaught;
	}
	
	
	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}

	private void setWorkCountry(Country workCountry) {
		if(workCountry != null) {
			this.workCountry = workCountry;
		}
	}

	private void setWorkCity(City workCity) {
		if(workCity != null) {
			this.workCity = workCity;
		}
	}
	
	

}
