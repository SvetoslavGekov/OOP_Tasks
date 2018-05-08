package immigrants;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import documents.Passport;
import urban.City;
import urban.Country;
import weapons.Weapon;
import weapons.Weapon.WeaponType;

public abstract class Immigrant {
	//Fields
	private Passport passport;
	private double money;
	private City city;
	private Country country;
	private Set<Immigrant> relatives = new HashSet<>();
	private Set<Weapon> weapons = new HashSet<>();
	
	//Constr
	public Immigrant(Passport passport, double money, City city, Country country) {
		setPassport(passport);
		setMoney(money);
		setCity(city);
		setCountry(country);
	}

	@Override
	public String toString() {
		return String.format("%s    %.2f", (passport != null) ? this.passport.getName() : this.getClass().getSimpleName(), this.money);
	}
	
	public abstract boolean buyWeapon(Weapon weapon);
	
	public  void immigrate(City randomCity) {
		//Remove immigrant from city
		if(this.city == randomCity) {
			return;
		}
		this.city.removeImmigrant(this);
		this.city = null;
		//Try to go to other city
		randomCity.acceptImmigrant(this);
		
		//Relatives try go to too
		for (Immigrant immigrant : relatives) {
			if(immigrant.city != null) {
				immigrant.immigrate(randomCity);
			}
		}
	}
	
	
	//Setters
	protected void addWeapon(Weapon weapon) {
		if(weapon != null) {
			this.weapons.add(weapon);
		}
	}
	
	public void addRelative(Immigrant immigrant) {
		if(immigrant != null && immigrant != this) {
			this.relatives.add(immigrant);
		}
	}
	
	protected void setPassport(Passport passport) {
		if(passport != null) {
			this.passport = passport;
		}
	}

	protected void setMoney(double money) {
		if(money > 0d) {
			this.money = money;
		}
	}

	public void setCity(City city) {
		if(city != null) {
			this.city = city;
		}
	}

	private void setCountry(Country country) {
		if(country != null) {
			this.country = country;
		}
	}
	
	//Getters
	public Set<Immigrant> getRelatives() {
		return Collections.unmodifiableSet(this.relatives);
	}
	
	protected Set<Weapon> getWeapons() {
		return Collections.unmodifiableSet(this.weapons);
	}
	
	public double getMoney() {
		return this.money;
	}

	public City getCity() {
		return this.city;
	}
	
	public Country getCountry() {
		return this.country;
	}

	public boolean hasBomb() {
		for (Weapon w: this.weapons) {
			if(w.getType() == WeaponType.BOMB) {
				return true;
			}
		}
		return false;
	}

	public void printInfo() {
		StringBuilder sb = new StringBuilder(this.toString());
		sb.append(String.format(" 	City: %s 	HasPassport: %s 	Relatives: %s", this.city, this.passport != null, this.relatives));
		System.out.println(sb.toString());
		
	}

	public abstract void terror();
}
