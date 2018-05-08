package officersOfTheLaw;

import java.util.Random;

import demo.Demo;
import immigrants.Immigrant;
import immigrants.NormalImmigrant;
import urban.City;
import urban.Country;

public class Swat extends Policeman {

	public Swat(String name, Country workCountry, City workCity) {
		super(name, workCountry, workCity);
	}

	@Override
	public boolean checkImmigrant(Immigrant immigrant) {
		if(immigrant instanceof NormalImmigrant) {
			return true;
		}
		
		int isGettingcaught = Demo.RNG(101); 
		return isGettingcaught > 90;
	}
	
}
