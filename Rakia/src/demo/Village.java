package demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import people.Brewer;
import people.Gatherer;
import people.InvalidPersonDataException;
import products.FruitType;
import products.Rakia;

public class Village {

	public static final int RNG(int max) {
		Random r = new Random();
		return r.nextInt(max);
	}

	public static  final int RNG(int min, int max) {
		Random r = new Random();
		return r.nextInt(max - min) + min;
	}
	
	public static final boolean validStr(String str) {
		return str != null && !str.trim().isEmpty();
	}
	
	public static final ArrayList<Distiller> distillers = new ArrayList<>();
	private static final Map<FruitType, Integer> gatheredFruits = new ConcurrentHashMap<>();
	private static final Map<FruitType, Integer> brewedRakii = new ConcurrentHashMap<FruitType, Integer>();  
	private static final Connection con = VillageDBManager.getInstance().getCon();
	
	public static void main(String[] args) {
		
		//1. Create distillers
		for (int i = 0; i < 5; i++) {
			Village.distillers.add(new Distiller());
		}
		
		//2. Create gatherers and brewers
		ArrayList<Gatherer> gatherers = new ArrayList<>();
		ArrayList<Brewer> brewers = new ArrayList<>();
		
		//2.1 Create 1 gatherer and brewer for each fruit
		int counter = 1;
		for (FruitType fruit : FruitType.values()) {
			try {
				gatheredFruits.put(fruit, 0);
				brewedRakii.put(fruit, 0);
				gatherers.add(new Gatherer("Gatherer " + counter, RNG(25, 45), fruit));
				brewers.add(new Brewer("Brewer " + counter, RNG(40, 50), fruit));
				counter++;
			}
			catch (InvalidPersonDataException e) {
				e.printStackTrace();
			}			
		}
		
		for (int i = counter; i <= 7; i++) {
			FruitType fruit = FruitType.getRandomFruitType();
			try {
				gatherers.add(new Gatherer("Gatherer " + i, RNG(25, 45), fruit));
			}
			catch (InvalidPersonDataException e) {
				e.printStackTrace();
			}
		}
		
		Thread t = new Thread(() ->{
			printStatitstics();
		} )  ;
		t.setDaemon(true);
		t.start();
		
		try {
			Thread.sleep(20000);
			sendEmail();
			createJSON(gatherers, false);
			createJSON(brewers, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printStatitstics() {
		while(true) {
			try {
				Thread.sleep(10000);
				System.out.println("================================== STATISTICS TIME ==================================");
				logBestFruit();
				logBestRakia();
				logRakiaRatio();
				System.out.println(getRakiaInfoFromDB());
				System.out.println(getBestBrewerFromDB());
				System.out.println(getYoungestGatherer());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void logRakiaRatio() {
		//Print in file
		try(FileWriter fw = new FileWriter(new File("Statistics.txt"), true);){
			fw.write(String.format("Grapes %d litres / Apricots %d litres%n", 
					Village.brewedRakii.get(FruitType.GRAPES), Village.brewedRakii.get(FruitType.APRICOT)));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void logBestRakia() {
		//Get best fruit
		FruitType fruit = null;
		int litres = 0;
		for (Entry<FruitType, Integer> e: Village.brewedRakii.entrySet()) {
			if(e.getValue() > litres) {
				fruit = e.getKey();
				litres = e.getValue();
			}
		}
		
		//Print in file
		try(FileWriter fw = new FileWriter(new File("Statistics.txt"), true);){
			fw.write(String.format("The best rakia is: %s rakia with a total of %d litres brewed.%n", fruit, litres));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	private static void logBestFruit() {
		//Get best fruit
		FruitType fruit = null;
		int maxCount = 0;
		for (Entry<FruitType, Integer> e: Village.gatheredFruits.entrySet()) {
			if(e.getValue() > maxCount) {
				fruit = e.getKey();
				maxCount = e.getValue();
			}
		}
		
		//Print in file
		try(FileWriter fw = new FileWriter(new File("Statistics.txt"));){
			fw.write(String.format("The best fruit is: %s with a total of %d gathered.%n", fruit, maxCount));
		}
		catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static final void addFruit(FruitType fruit, Integer count) {
		int currentCount = Village.gatheredFruits.get(fruit);
		Village.gatheredFruits.put(fruit, count + currentCount); 
	}
	
	public static final void addRakia(FruitType fruit, Integer litres) {
		int currentCount = Village.brewedRakii.get(fruit);
		Village.brewedRakii.put(fruit, litres+ currentCount); 
	}
	
	public synchronized static boolean hasAReadyDistiller(FruitType fruitType) {
		for (Distiller distiller : distillers) {
			if(distiller.canMakeRakia(fruitType)) {
				return true;
			}
		}
		return false;
	}
	
	public static void saveGatherer(Gatherer g) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO gatherers (name, age) VALUES (?,?);")) {
			ps.setString(1, g.getName());
			ps.setInt(2, g.getAge());
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveBrewer(Brewer b) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO brewers (name, age) VALUES (?,?);")) {
			ps.setString(1, b.getName());
			ps.setInt(2, b.getAge());
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void saveRakia(Rakia r) {
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO rakii (fruit, brewer_name, litres,date) VALUES (?,?,?,?);")) {
			ps.setString(1, r.getFruit().toString());
			ps.setString(2, r.getBrewer().getName());
			ps.setInt(3, r.getLitres());
			ps.setDate(4, java.sql.Date.valueOf(r.getDate()));
			ps.executeUpdate();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getRakiaInfoFromDB() {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = con.prepareStatement("SELECT fruit, SUM(litres) litres FROM rakii GROUP BY fruit;")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				sb.append(String.format("Rakia: %s	Litres: %d%n",rs.getString("fruit"), rs.getInt("litres")));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getBestBrewerFromDB() {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = con.prepareStatement("SELECT brewer_name, SUM(litres) litres "
				+ "FROM rakii GROUP BY brewer_name ORDER BY litres DESC LIMIT 1;")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				sb.append(String.format("%nBest brewer is: %s with %d litres brewed",rs.getString("brewer_name"), rs.getInt("litres")));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getYoungestGatherer() {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = con.prepareStatement("SELECT name, MIN(AGE) age FROM gatherers;")) {
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				sb.append(String.format("%nYoungest gatherer is: %s with an age of %d%n",rs.getString("name"), rs.getInt("age")));
			}
			rs.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static void sendEmail() {
		StringBuilder info  = new StringBuilder(getRakiaInfoFromDB());
		info.append(getBestBrewerFromDB());
		info.append(getYoungestGatherer());
		MailSender.sendEmail("svetoslavgekov@gmail.com", "921022Ww;", "svetoslavgekov@gmail.com", info.toString(), new File("Statistics.txt"));
	}
	
	public static void createJSON(ArrayList collection, boolean append) throws IOException {
		GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting();
		Gson g= builder.create();
		FileWriter fw = new FileWriter(new File("JSON-A.json"), append);
		g.toJson(collection,fw);
		fw.close();

	}
	
}
