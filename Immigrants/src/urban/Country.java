package urban;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import demo.Demo;
import immigrants.Immigrant;

public class Country {
	// Fields
	private String name;
	private Set<City> cities = new HashSet<>();

	// Constr
	public Country(String name, Set<City> cities) {
		setName(name);
		setCities(cities);
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("Country: %s Number of cities: %d", this.name, this.cities.size());
	}
	
	Comparator<City> byPopulation = new Comparator<City>() {
		public int compare(City o1, City o2) {
			return (o1.getTotalPopulation() - o2.getTotalPopulation() >= 0) ? 1 : -1;
		};
	};
	
	
	public void printSurvivingImmigrants() {
		TreeSet<Immigrant> survivors = new TreeSet<>((i1, i2) -> {return (i2.getMoney() - i1.getMoney() >= 0) ? 1 : -1;});
		for (City city : this.cities) {
			survivors.addAll(city.getImmigrants());
		}
		for (Immigrant immigrant : survivors) {
			System.out.println(immigrant);
		}
	}
	
	public void printSurvivingCities() {
		TreeSet<City> surviving = new TreeSet<>((c1, c2) -> {return (c2.getTotalPopulation() - c1.getTotalPopulation() >= 0) ? 1 : -1;});
		for (City city : this.cities) {
			if(city.getTotalPopulation() >= 0) {
				surviving.add(city);
			}
		}
		System.out.println(surviving);
	}
	
	// Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}

	private void setCities(Set<City> cities) {
		if(cities != null) {
			this.cities = new HashSet<>(cities);
		}
	}
	
	public List<City> getCities() {
		return Collections.unmodifiableList(new ArrayList<>(this.cities));
	}

	public void removeCity(City city) {
		if(this.cities.contains(city)) {
			city.killEveryone();
		}
		this.cities.remove(city);
	}



}
