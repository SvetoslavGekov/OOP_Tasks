package people.citizen;

import java.util.ArrayList;
import java.util.Random;

import demo.Demo;
import people.InvalidPersonDataException;
import people.Person;
import postItems.InvalidPostItemDataException;
import postItems.letters.Letter;
import postItems.packages.PostPackage;
import postOffice.PostBox;
import postOffice.PostOffice;

public class Citizen extends Person {
	// Fields
	private String address;
	public static PostOffice postOffice;
	public static ArrayList<Citizen> peopleInTown;

	// Constructors
	public Citizen(String names, String address) throws InvalidPersonDataException {
		super(names);
		setAddress(address);
		System.out.println(this);
	}

	// Methods

	public void startSendingPostItems() {
		while (true) {
			//Have a 50% chance to send letter or package
			if(new Random().nextBoolean()) {
				// Create the random letter
				Letter letter = createRandomLetter();
				this.sendLetter(letter);
			}
			else {
				// Create random package
				PostPackage pck = createRandomPackage();
				this.sendPackage(pck);
			}
			//Sleep for 10 seconds after sending 1 postItem
			try {
				Thread.sleep(5_000);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void sendPackage(PostPackage pck ) {
		// Drop package off at post office
		postOffice.receieverLetterOrPackage(pck);
	}

	public void sendLetter(Letter letter) {
		// Have a 50% chance to drop it off in a box or in the post office
		boolean dropInOffice = new Random().nextBoolean();
		if (dropInOffice) {
			// Drop letter in the office
			postOffice.receieverLetterOrPackage(letter);
		}
		else {
			// Drop letter in random post box
			PostBox pb = postOffice.getRandomPostBox();
			pb.receiveLetter(letter);
		}
	}

	private Citizen getRandomReceiever() {
		Citizen receiver = null;
		// Select random citizen from town to receive this letter
		while (receiver == null) {
			receiver = Citizen.peopleInTown.get(Demo.RNG(peopleInTown.size()));
			if (receiver == this) {
				receiver = null;
			}
		}
		return receiver;
	}

	private postItems.packages.PostPackage createRandomPackage() {
		postItems.packages.PostPackage pck = null;
		Citizen receiver = getRandomReceiever();

		// Create package
		try {
			// Assign random size to package
			int length = Demo.RNG(10, 100);
			int width = Demo.RNG(10, 100);
			int height = Demo.RNG(10, 100);
			boolean isFragile = new Random().nextBoolean();
			pck = new postItems.packages.PostPackage(this, receiver, length, width, height, isFragile);
		}
		catch (Exception e) {
		}
		return pck;
	}

	private Letter createRandomLetter() {
		Letter letter = null;
		Citizen receiver = getRandomReceiever();

		// Create letter
		try {
			letter = new Letter(this, receiver);
		}
		catch (InvalidPostItemDataException e) {
			e.printStackTrace();
		}
		return letter;
	}

	@Override
	public String toString() {
		return String.format("%s %s", this.getNames(), this.address);
	}

	// Setters
	public void setAddress(String address) throws InvalidCitizenDataException {
		if (Demo.validStr(address)) {
			this.address = address;
		}
		else {
			throw new InvalidCitizenDataException("Invalid citizen address");
		}
	}
}
