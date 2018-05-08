package immigrants;

import java.util.Random;

import documents.Passport;
import urban.City;
import urban.Country;
import weapons.Weapon;
import weapons.Weapon.WeaponType;

public class RadicalImmigrant extends Immigrant {
	private static final double PASSPORT_CHANCE = 0.35;
	private static int MAX_WEAPONS = 5;

	public RadicalImmigrant(Passport passport, double money, City city, Country country) {
		super(passport, money, city, country);
	}

	@Override
	public boolean buyWeapon(Weapon weapon) {
		//If bomb or too expensive
		if(weapon.getType() == WeaponType.BOMB || weapon.getCost() > getMoney()) {
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
		int casualties = 0;
		for (Weapon w : this.getWeapons()) {
			casualties += w.fire();
		}
		
		this.getCity().removeCasualties(casualties);
		System.out.println(this + " killed " + casualties + " people");
	}

	@Override
	protected void setPassport(Passport passport) {
		if(new Random().nextDouble() < this.PASSPORT_CHANCE) {
			super.setPassport(passport);
		}
	}
	
	@Override
	protected void addWeapon(Weapon weapon) {
		if(super.getWeapons().size() < this.MAX_WEAPONS && weapon.getType() != WeaponType.BOMB) {
			super.addWeapon(weapon);
		}
	}
}
