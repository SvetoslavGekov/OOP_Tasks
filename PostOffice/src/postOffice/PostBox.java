package postOffice;

import java.util.HashSet;
import java.util.Set;

import people.mailman.Mailman;
import postItems.PostItem;
import postItems.letters.Letter;

public class PostBox {
	//Fields
	private static int currentId = 1;
	private int id;
	private Set<PostItem> items = new HashSet<>();
	private boolean recentlyCollected = false;
	
	//Constructor
	public PostBox() {
		this.id = currentId++;
	}

	//Methods
	@Override
	public String toString() {
		return String.format("Post box %d", this.id);
	}
	
	public synchronized void receiveLetter(Letter letter) {
		//Only 1 person at a time can put letters in this post box
		this.items.add(letter);
		System.out.printf("%s dropped a %s in %s adressed to %s.%n", letter.getSender().getNames(), letter.getClass().getSimpleName(),
				this, letter.getReceiver().getNames());
	}

	public synchronized boolean getMail(Mailman mailman) {
		//If not recently collected
		if(!this.recentlyCollected && !this.items.isEmpty()) {
			this.recentlyCollected = true;
			//Postman collects post
			mailman.addItemsToCollected(items);
			System.out.printf("%s collected %d letters from %s%n%n", mailman, this.items.size(), this);
			//Clear items from postbox
			this.items.clear();
			
			
			//Start new thread for making the postbox not recently collected after 15 seconds
			Thread t = new Thread(() -> {
				try {
					Thread.sleep(15_000);
					this.recentlyCollected = false;
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
			t.start();
			return true;
		}
		return false;
	}
}
