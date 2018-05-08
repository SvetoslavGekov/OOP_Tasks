package postOffice;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;

import demo.Demo;
import people.mailman.Mailman;
import postItems.PostItem;
import postItems.letters.Letter;
import postItems.packages.PostPackage;

public class PostOffice {
	// Fields
	private static final int INITIAL_BOXES = 25;
	private static final int POST_ITEMS_LIMIT = 50;
	private String name;
	private Set<Mailman> mailmen = new HashSet<>();
	private Set<PostItem> storage = new HashSet<>();
	private Map<LocalDate, Map<LocalTime, PostItem>> archive = new TreeMap<>();
	private Set<PostBox> postBoxes = new HashSet<>();
	private double money = 10_000d;

	// Constructor
	public PostOffice(String name) throws InvalidPostOfficeDataException {
		setName(name);

		// Create the initial boxes
		for (int i = 0; i < INITIAL_BOXES; i++) {
			this.postBoxes.add(new PostBox());
		}
		System.out.println(this);
		this.startPostOfficeWork();
		this.printStatistics();
	}

	// Methods

	private void printStatistics() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(40_000);
					System.out.println(
							"================================================================ STATISTICS TIME ============================");
					// Statistics 1
					printItemsByDate(LocalDate.now());

					// Statistics 2
					printProcentLettersForDay(LocalDate.now());

					// Statistics 3
					printPercentFragilePostPackages();
					
					//Statistics 4
					printWorkersInfo();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	private void printWorkersInfo() {
		File f = new File("Workers.txt");
		TreeSet<Mailman> mailmen = new TreeSet<>(new Comparator<Mailman>() {
			@Override
			public int compare(Mailman o1, Mailman o2) {
				return (o1.getTotalItemsSent() >= o2.getTotalItemsSent()) ? 1 : -1;
			}
		});
		
		mailmen.addAll(this.mailmen);
		try(FileWriter fw = new FileWriter(f)){
			for (Mailman mailman : mailmen) {
				fw.write(mailman + " Total items: " + mailman.getTotalItemsSent() +"\n");
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printPercentFragilePostPackages() {
		File f = new File("PercentFragilePackages.txt");
		int totalPackages = 0;
		int fragilePackages = 0;
		synchronized (this.archive) {
			for (Entry<LocalDate, Map<LocalTime, PostItem>> entr : this.archive.entrySet()) {
				for (Entry<LocalTime, PostItem> itemEntry : entr.getValue().entrySet()) {
					if (itemEntry.getValue() instanceof PostPackage) {
						PostPackage p = (PostPackage) itemEntry.getValue();
						totalPackages++;
						if (p.getIsFragile()) {
							fragilePackages++;
						}
					}
				}
			}
		}
		
		double percentFragile = (double) ((double)fragilePackages/(double)totalPackages) * 100d;
		
		try (FileWriter fw = new FileWriter(f)) {
			fw.write("Total number of packages: " + totalPackages + "\n");
			fw.write("Total number of fragile packages: " + fragilePackages + "\n");
			fw.write("Percent fragile packages: " + percentFragile);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void printProcentLettersForDay(LocalDate date) {
		File f = new File("Letters_" + date.toString() + ".txt");
		int totalLetters = 0;
		int totalItems = 0;
		synchronized (archive) {
			for (Entry<LocalDate, Map<LocalTime, PostItem>> entry : this.archive.entrySet()) {
				if (entry.getKey().isEqual(date)) {
					totalItems = entry.getValue().size();
					for (PostItem item : entry.getValue().values()) {
						if (item instanceof Letter) {
							totalLetters++;
						}
					}
				}
			}
		}
		try (FileWriter fw = new FileWriter(f)) {
			fw.write("Total number of items: " + totalItems);
			fw.write("Letters: " + totalLetters + "\n");
			fw.write("Percent letters: " + (double) ((double)(totalLetters / (double)totalItems) * 100d));
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void printItemsByDate(LocalDate date) {
		File f = new File("PostItems_" + date.toString() + ".txt");
		synchronized (this.archive) {
			for (Entry<LocalDate, Map<LocalTime, PostItem>> entry : this.archive.entrySet()) {
				if (entry.getKey().isEqual(date)) {
					try (FileWriter fw = new FileWriter(f)) {
						for (PostItem item : entry.getValue().values()) {
							fw.write(item.toString()+"\n");
						}
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					
					
					Gson gsonObj = new Gson();
					
					for (PostItem item : entry.getValue().values()) {
						try (FileWriter fw = new FileWriter("JSON_TEST.json",true)) {
							gsonObj.toJson(item,fw);
							fw.write("\n");
						}
						catch (JsonIOException e) {
							e.printStackTrace();
						}
						catch (IOException e) {
							e.printStackTrace();
						}
					}
					
//					try (FileWriter fw = new FileWriter("JSON_TEST.json", true)) {
//						
//					}
//					catch (IOException e) {
//						e.printStackTrace();
//					}
				}
			}
		}
	}

	private void startPostOfficeWork() {
		Thread t = new Thread(() -> {
			while (true) {
				try {
					// Check current storage size every 5 seconds
					Thread.sleep(5000);
					System.out.println("======== ITEMS IN POST STORAGE ----->   " + this.storage.size());
					if (this.isStorageAboveLimit()) {
						// If size is above limit --> send mailman to process items
						this.sendMailmanToWork();
					}
					else {
						// If size less than limit --> send postman to collect letters from postboxes
						this.sendPostmenToWork();
					}
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}

	private void sendMailmanToWork() {
		// Split work between mailmen
		int mailmen = this.countMailmen();
		int itemsPerMailman = this.storage.size() / mailmen;

		for (Mailman mailman : this.mailmen) {
			if (mailman.getIsTrusted()) {
				// Each mailman starts sending packages
				Thread t = new Thread(() -> {
					mailman.sendMail(itemsPerMailman);
				});
				t.start();
			}
		}
	}

	private void sendPostmenToWork() {
		for (Mailman mailman : mailmen) {
			if (!mailman.getIsTrusted()) {
				// Each postman (not trusted mailmen) starts collecting mail from postboxes
				Thread t = new Thread(() -> {
					mailman.collectMail();
				});
				t.start();
			}
		}
	}

	private int countMailmen() {
		int counter = 0;
		for (Mailman mailman : mailmen) {
			if (mailman.getIsTrusted()) {
				counter++;
			}
		}
		return counter;
	}

	private boolean isStorageAboveLimit() {
		return this.storage.size() > POST_ITEMS_LIMIT;
	}

	public synchronized void receieverLetterOrPackage(PostItem item) {
		// Only 1 item can be added to the storage and archive at a time
		// Add item to storages and archive
		this.addItemToStorage(item);
		this.addItemToArchive(item);

		System.out.printf("%s accepted %s from %s addressed to %s%n", this.name, item, item.getSender(),
				item.getReceiver());

		// Get money from citizen --> we assume that people always have money for the
		// sake of simplicity
		getItemTaxMoney(item);
	}

	private void getItemTaxMoney(PostItem item) {
		double tax = item.calculateTax();
		setMoney(tax);
		// System.out.printf("%s payed %.2f to send a %s to %s.%n",
		// item.getSender().getNames(), tax, item
		// ,item.getReceiver().getNames());
	}

	private void addItemToStorage(PostItem item) {
		synchronized (this.storage) {
			if (item != null) {
				this.storage.add(item);
			}
		}
	}

	private void addItemToArchive(PostItem item) {
		synchronized (this.archive) {
			if (item != null) {
				// If no items for the current date
				LocalDate date = LocalDate.now();
				if (!this.archive.containsKey(date)) {
					// Add day collection
					this.archive.put(date, new TreeMap<LocalTime, PostItem>());
				}
				// Add item to archive
				this.archive.get(date).put(LocalTime.now(), item);
			}
		}
	}

	@Override
	public String toString() {
		return String.format("PostOffice: %s	Number of post boxes: %d", this.name, this.postBoxes.size());
	}

	// Setters
	public void hireMailman(Mailman mailman) {
		if (mailman != null) {
			this.mailmen.add(mailman);
		}
	}

	private void setName(String name) throws InvalidPostOfficeDataException {
		if (Demo.validStr(name)) {
			this.name = name;
		}
		else {
			throw new InvalidPostOfficeDataException("Invalid post office name.");
		}
	}

	public void setMoney(double money) {
		if (money >= 0) {
			this.money = money;
		}
	}

	public PostBox getRandomPostBox() {
		ArrayList<PostBox> pBoxes = new ArrayList<>(this.postBoxes);
		return pBoxes.get(Demo.RNG(pBoxes.size()));
	}

	public List<PostBox> getAllPostBoxes() {
		return Collections.unmodifiableList(new ArrayList<PostBox>(this.postBoxes));
	}

	public Set<PostItem> giveItemsToMailman(int itemsPerMailman) {
		Set<PostItem> items = new HashSet<>();

		synchronized (this.storage) {
			Iterator<PostItem> it = this.storage.iterator();

			while (itemsPerMailman > 0 && it.hasNext()) {
				PostItem item = it.next();
				items.add(item);
				it.remove();
				itemsPerMailman--;
			}
		}

		return items;
	}

	public synchronized void collectItemsFromMailman(Set<PostItem> collectedItems) {
		for (PostItem postItem : collectedItems) {
			this.receieverLetterOrPackage(postItem);
		}
		collectedItems.clear();
	}
}
