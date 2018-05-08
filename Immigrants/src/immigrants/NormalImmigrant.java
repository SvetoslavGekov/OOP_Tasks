package immigrants;

import documents.Passport;
import urban.City;
import urban.Country;
import weapons.Weapon;

public class NormalImmigrant extends Immigrant {
	private static final int MAX_RELATIVES = 10;
	
	//Constr
	public NormalImmigrant(Passport passport, double money, City city, Country country) {
		super(passport, money, city, country);
	}

	@Override
	public boolean buyWeapon(Weapon weapon) {
		return false;
	}

	//Setters
	@Override
	public void addRelative(Immigrant immigrant) {
		if(super.getRelatives().size() < this.MAX_RELATIVES) {
			super.addRelative(immigrant);
		}
	}
	
	@Override
	public void terror() {
		
	}
}
