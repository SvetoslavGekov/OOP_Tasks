package demo;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import people.InvalidPersonDataException;
import people.citizen.Citizen;
import people.mailman.Mailman;
import postOffice.InvalidPostOfficeDataException;
import postOffice.PostOffice;

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
		//1. Create post office
		PostOffice postOffice = null;
		try {
			System.out.println("=========== POST OFFICE CREATION =============");
			postOffice = new PostOffice("IT Transfers");
		}
		catch (InvalidPostOfficeDataException e) {
			e.printStackTrace();
		}
		//Assign this post office to all citizens
		Citizen.postOffice = postOffice;
		Mailman.postOffice = postOffice;
		
		//2. Create 10 citizens
		System.out.println("\n=========== CITIZEN CREATION =============");
		ArrayList<Citizen> citizens = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			try {
				String names = "Citizen " + (i+1);
				String address = "Address " + (i+1);
				Citizen c = new Citizen(names, address);
				citizens.add(c);
			}
			catch (InvalidPersonDataException e) {
				e.printStackTrace();
			}
		}
		//Assign people in town to all citizens
		Citizen.peopleInTown = citizens;
		
		//3. Create 10 random mailman
		System.out.println("\n=========== MAILMAN CREATION =============");
		for (int i = 0; i < 10; i++) {
			try {
				String names = "Mailman " + (i+1);
				int experience = 5;
				boolean isTrusted = new Random().nextBoolean();
				
				if(!isTrusted) {
					names = "Postman " + (i+1);
					experience = 0;
				}
				
				Mailman m = new Mailman(names, experience, isTrusted);
				postOffice.hireMailman(m);
			}
			catch (InvalidPersonDataException e) {
				e.printStackTrace();
			}
		}
		
		//4. Let citizens start sending items
		System.out.println("\n============ CITIZENS START SENDING ITEMS =============");
		ExecutorService service = null;
		try {
			service = Executors.newCachedThreadPool();
			
			for (Citizen citizen : citizens) {
				service.execute(() -> {
					citizen.startSendingPostItems();
				});
			}
		}
		finally {
			
		}
	}
}
