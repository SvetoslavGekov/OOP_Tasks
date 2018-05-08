package urban;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.omg.CORBA.PolicyErrorCodeHelper;

import demo.Demo;
import immigrants.Immigrant;
import officersOfTheLaw.Policeman;

public class City {
	//Fields
	private String name;
	private Set<Policeman> officers = new HashSet<>();
	private Set<Immigrant> immigrants = new HashSet<>();
	private int citizens;
	private int totalPopulation;
	
	//Constr
	public City(String name, int citizens) {
		setName(name);
		setCitizens(citizens);
		calculateTotalPopulation();
		System.out.println(this);
	}

	@Override
	public String toString() {
		return String.format("%s Citizens: %d Total population: %d", this.name, this.citizens, this.totalPopulation);
	}
	
	//Setters
	private void setName(String name) {
		if(Demo.validStr(name)) {
			this.name = name;
		}
	}

	public void setOfficers(Set<Policeman> officers) {
		if(officers != null) {
			this.officers = new HashSet<>(officers);
		}
	}

	private void setCitizens(int citizens) {
		if(citizens > 0) {
			this.citizens = citizens;
		}
	}

	private void calculateTotalPopulation() {
		this.totalPopulation = (this.citizens + this.officers.size() + this.immigrants.size()); 
	}

	public void addImmigrant(Immigrant imm) {
		if(imm != null) {
			this.immigrants.add(imm);
			System.out.println(String.format("%s 	joined 	%s", imm, this));
			calculateTotalPopulation();
		}
	}

	public void removeImmigrant(Immigrant imm) {
		this.immigrants.remove(imm);
	}

	public void acceptImmigrant(Immigrant immigrant) {
		//Get random officer
		Policeman policeman = getRandomPoliceman();
		
		//Policeman checks immigrant
		if(policeman.checkImmigrant(immigrant)) {
			//Add immigrant to city
			this.addImmigrant(immigrant);
			immigrant.setCity(this);
			System.out.println(String.format("%s immigrated to %s%n", immigrant, this));
		}
	}
	
	private Policeman getRandomPoliceman() {
		ArrayList<Policeman> policeForce = new ArrayList<>(this.officers);
		return policeForce.get(Demo.RNG(policeForce.size()));
	}

	public void removeCasualties(int casualties) {
		setCitizens(this.citizens - casualties);
		calculateTotalPopulation();
	}
	
	public Set<Immigrant> getImmigrants() {
		return Collections.unmodifiableSet(this.immigrants);
	}

	public void killEveryone() {
		this.citizens = 0;
		this.immigrants.clear();
		this.officers.clear();
		calculateTotalPopulation();
	}
	
	public int getTotalPopulation() {
		calculateTotalPopulation();
		return this.totalPopulation;
	}
}
