package people.mailman;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import people.InvalidPersonDataException;
import people.Person;
import postItems.PostItem;
import postOffice.PostBox;
import postOffice.PostOffice;

public class Mailman extends Person {
	// Fields
	public static PostOffice postOffice;
	private int experience;
	private boolean isTrusted;
	private int totalItemsSent = 0;
	private Set<PostItem> collectedMail = new HashSet<>();

	// Constructor
	public Mailman(String names, int experience, boolean isTrusted) throws InvalidPersonDataException {
		super(names);
		setExperience(experience);
		setIsTrusted(isTrusted);
		System.out.println(this);
	}

	// Methods
	public void sendMail(int itemsPerMailman) {
		// Get that number of items from post office and increase items processed
		Set<PostItem> items = null;
		try {
			items = postOffice.giveItemsToMailman(itemsPerMailman);
			setTotalItemsSent(totalItemsSent + items.size());
		}
		catch (InvalidMailmanDataException e) {
			e.printStackTrace();
		}

		for (PostItem postItem : collectedMail) {
			try {
				// Sleep for the time an item needs to be processed
				Thread.sleep(postItem.getMailmanProcessTime() * 100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.printf("%s finished sending %d of postitems.%n", this, items.size());
	}

	public void collectMail() {
		// Get the lit of post boxes associated with the post office
		List<PostBox> boxes = postOffice.getAllPostBoxes();

		// Go and collect letters from the boxes
		for (PostBox postBox : boxes) {
			// Only 1 postman can collect mail from this box at a time
			synchronized (postBox) {
				if (postBox.getMail(this)) {
					try {
						Thread.sleep(1000);
					}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		// Sleep for 2 hours (20 seconds) and return mail
		try {
			Thread.sleep(20_000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}

		postOffice.collectItemsFromMailman(this.collectedMail);
//		// Add items to post
//		for (PostItem item : this.collectedMail) {
//			postOffice.receieverLetterOrPackage(item);
//		}
//
//		// Clear items
//		this.collectedMail.clear();
	}

	@Override
	public String toString() {
		return String.format("%s	Experience: %d	IsTrusted:%s", this.getNames(), this.experience, this.isTrusted);
	}

	// Setters
	private void setExperience(int experience) throws InvalidMailmanDataException {
		if (experience >= 0) {
			this.experience = experience;
		}
		else {
			throw new InvalidMailmanDataException("Invalid mailman experience.");
		}
	}

	private void setIsTrusted(boolean isTrusted) {
		this.isTrusted = isTrusted;
	}

	private void setTotalItemsSent(int totalItemsSent) throws InvalidMailmanDataException {
		if (totalItemsSent > 0) {
			this.totalItemsSent = totalItemsSent;
		}
		else {
			throw new InvalidMailmanDataException("Invalid number of sent items.");
		}
	}

	public void addItemsToCollected(Collection<PostItem> items) {
		if (items != null) {
			this.collectedMail.addAll(items);
		}
	}

	// Getters
	public boolean getIsTrusted() {
		return this.isTrusted;
	}
	
	public int getTotalItemsSent() {
		return this.totalItemsSent;
	}

}
