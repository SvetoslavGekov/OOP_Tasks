package immigrants;

import java.util.ArrayList;

import documents.Passport;
import urban.City;
import urban.Country;
import weapons.Weapon;
import weapons.Weapon.WeaponType;

public class ExtremeImmigrant extends Immigrant {
	public static ArrayList<ExtremeImmigrant> bombers = new ArrayList<>();
	
	//Constr
	public ExtremeImmigrant(double money, City city, Country country) {
		super(null, money, city, country);
	}

	@Override
	public boolean buyWeapon(Weapon weapon) {
		//If too expensive
		if(weapon.getCost() > getMoney()) {
			return false;
		}
		
		//Add weapon
		this.addWeapon(weapon);
		//Remove cash
		this.setMoney(this.getMoney() - weapon.getCost());
		System.out.println(String.format("%s bought %s", this, weapon));
		return true;
	}

	@Override
	public void terror() {
		System.out.println(this + " is terrorizing city " + this.getCity());
		this.getCountry().removeCity(this.getCity());
		bombers.add(this);
	}
	

}
