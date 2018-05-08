package demo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;

import documents.Passport;
import immigrants.ExtremeImmigrant;
import immigrants.Immigrant;
import immigrants.NormalImmigrant;
import immigrants.RadicalImmigrant;
import officersOfTheLaw.Policeman;
import officersOfTheLaw.Swat;
import urban.City;
import urban.Country;
import weapons.Bomb;
import weapons.Pistol;
import weapons.Rifle;
import weapons.Weapon;
import weapons.Weapon.WeaponType;

public class Demo {
	public static final int RNG(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static final int RNG(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}

	public static final boolean validStr(String str) {
		return str != null && !str.trim().isEmpty();
	}

	public static void main(String[] args) {
		
		//1.1 Create 5 cities
		System.out.println("========== CITIES CREATION =========");
		Set<City> cities = new HashSet<>();
		for (int i = 0; i < 5; i++) {
			
			//Create city
			String name = "City " + (i+1);
			int citizens = Demo.RNG(150_000, 2_000_000);
			City c = new City(name,citizens);
			cities.add(c);
		}
		
		//1.2 Create country
		System.out.println("\n ========== COUNTRY CREATION ==============");
		Country country = new Country("Bahashmilu", cities);
		ArrayList<City> citiesList = new ArrayList<>(cities);
		
		//1.3 Add policemen to cities
		System.out.println("\n ========== POLICE CREATION ==============");
		for (int i = 0; i < 5; i++) {
			Set<Policeman> police = new HashSet<>();
			
			for (int j = 0; j < 20; j++) {
				Policeman p = createRandomPoliceman(country, citiesList.get(i));
				police.add(p);
			}
			citiesList.get(i).setOfficers(police);
		}
		
		//2. Immigrants creation
		System.out.println("\n ========== IMMIGRANT CREATION ==============");
		ArrayList<Immigrant> immigrants = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			//Create immigrant
			City city = citiesList.get(Demo.RNG(citiesList.size()));
			
			Immigrant imm = createImmigrant(country, city);
			immigrants.add(imm);
			//Add immigrant to city
			city.addImmigrant(imm);
			
		}
		
		//2.2 Add relatives
		for (Immigrant immigrant : immigrants) {
			while(immigrant.getRelatives().size() < 2) {
				immigrant.addRelative(immigrants.get(Demo.RNG(immigrants.size())));
			}
		}
		
		//3.Create weapons
		System.out.println("\n ========== WEAPON CREATION ==============");
		ArrayList<Weapon> weapons = new ArrayList<>();
		for (int i = 0; i < 200; i++) {
			Weapon w = createWeapon();
			weapons.add(w);
		}
		
		//4. Immigrants buy weapons
		for (Iterator<Immigrant> it= immigrants.iterator(); it.hasNext();) {
			Immigrant imm = it.next();
			
			for (int i = 0; i < 5; i++) {
				//Tries to buy weapon
				Weapon w = weapons.get(RNG(weapons.size()));
				
				if(!imm.buyWeapon(w)) {
					//Check if radical and bomb
					if((imm instanceof RadicalImmigrant && w.getType() != WeaponType.BOMB) || imm instanceof ExtremeImmigrant) {
						//RIP
						System.out.println(imm + " died");
						imm.getCity().removeImmigrant(imm);
						it.remove();
						break;
					}
				}
				else {
					//Remove weapon if he bought it
					weapons.remove(w);
				}
			}
		}
		
		//5. Immigrate
		System.out.println("\n ========== IMMIGRATION ==============");
		for (Iterator<Immigrant> it= immigrants.iterator(); it.hasNext();) {
			Immigrant imm = it.next();
			
			City randomCity = country.getCities().get(RNG(country.getCities().size()));
			if(imm.getCity() != null) {
				imm.immigrate(randomCity);
			}
			else {
				it.remove();
			}
		}
		
		//Print immigrant info
		for (Immigrant immigrant : immigrants) {
			immigrant.printInfo();
		}
		
		Iterator<City> it = country.getCities().iterator();
		while(it.hasNext()) {
			City c = it.next();
			Iterator<Immigrant> immIt = c.getImmigrants().iterator();
			while(immIt.hasNext()) {
				Immigrant imm = immIt.next();
				imm.terror();
				if(imm instanceof ExtremeImmigrant) {
					break;
				}
			}	
		}
		country.printSurvivingCities();
		country.printSurvivingImmigrants();
		System.out.println();
		for (int i = 0; i < ExtremeImmigrant.bombers.size(); i++) {
			System.out.println(ExtremeImmigrant.bombers.get(i));
		}
	}
	
	public static Weapon createWeapon() {
		WeaponType type = WeaponType.values()[Demo.RNG(WeaponType.values().length)];
		
		switch (type) {
		case PISTOL: return new Pistol(Demo.RNG(100, 300));
		case RIFLE: return new Rifle(Demo.RNG(1000,	2000));
		default: return new Bomb(Demo.RNG(5000, 7500));
		}
	}
	
	public static Immigrant createImmigrant(Country country, City city) {
		int type = Demo.RNG(0,100);
		
		//Create passport
		Passport passport = new Passport("Immigrant " + Demo.RNG(0, 1000), Demo.RNG(20,30), country, city);
		Double money = (double) Demo.RNG(10_000, 20_000);
		
		
		if(type > 40) {
			return new NormalImmigrant(passport, money, city, country);
		}
		if(type <= 40 && type > 5) {
			return new RadicalImmigrant(passport, money, city, country);
		}
		
		return new ExtremeImmigrant(money, city, country);
	}
	
	public static Policeman createRandomPoliceman(Country workCountry, City workCity) {
		int type = Demo.RNG(2);
		switch (type) {
		case 0:	return new Policeman("Policeman " + Demo.RNG(0, 1000), workCountry, workCity);
		case 1: return new Swat("Swat " + Demo.RNG(0, 1000), workCountry, workCity);
		}
		return null;
	}
}
