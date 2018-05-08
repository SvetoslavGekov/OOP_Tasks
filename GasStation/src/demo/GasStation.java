package demo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cars.Car;
import fuelingRecord.FuelingRecord;
import people.InvalidPersonDataException;
import people.Worker;

public class GasStation {
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

	public static final List<GasPump> pumps = new ArrayList<>();
	public static final List<CashDesk> cashDesks = new ArrayList<>();
	public static final List<Worker> workers = new ArrayList<>();
	private static final List<FuelingRecord> fuelRecords = new ArrayList<>();
	private static final Connection con = GasStationDBManager.getInstance().getCon();

	public static void main(String[] args) {
		// 1. Fill static collections

		for (int i = 0; i < 2; i++) {
			try {
				GasStation.cashDesks.add(new CashDesk());
				GasStation.workers.add(new Worker("Worker " + (i + 1)));
			}
			catch (InvalidPersonDataException e) {
				e.printStackTrace();
			}
		}

		for (int i = 0; i < 5; i++) {
			GasStation.pumps.add(new GasPump());
		}

		// 2. Create some cars
		for (int i = 0; i < 100; i++) {
			new Car();
		}

		Thread t = new Thread(() -> {
			GasStation.printStatistics();
		});
		t.setDaemon(true);
		t.start();
	}

	public synchronized static GasPump getRandomGasPump() {
		return GasStation.pumps.get(RNG(GasStation.pumps.size()));
	}

	public static CashDesk getRandomCashDesk() {
		return GasStation.cashDesks.get(RNG(GasStation.cashDesks.size()));
	}

	public static boolean hasWaitingCars() {
		for (GasPump gasPump : pumps) {
			if (gasPump.hasWaitingCars()) {
				return true;
			}
		}
		return false;
	}

	public static void addFuelingRecord(FuelingRecord record) {
		synchronized (fuelRecords) {
			// Add record to station
			GasStation.fuelRecords.add(record);
			// Add record to DB
			addRecordToDB(record);
		}
	}

	private static void addRecordToDB(FuelingRecord record) {
		try (PreparedStatement ps = con
				.prepareStatement("INSERT INTO station_loadings (kolonka_id, fuel_type, fuel_quantity, loading_time, price)"
						+ " VALUES(?,?,?,?,?);")) {
			ps.setInt(1, record.getPump().getId());
			ps.setString(2, record.getFuel().toString());
			ps.setInt(3, record.getLitres());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(record.getFueling_time()));
			ps.setDouble(5, record.getFuel().getCost() * (double) record.getLitres());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void printStatistics() {
		while (true) {
			try {
				Thread.sleep(30000);
				System.out.println("==================== STATISTICS TIME =================");
				System.out.println(getAllFuelings());
				System.out.println(getAllFuelingsForTheDay());
				System.out.println(getAllFuelingsByFuelType());
				System.out.printf("The gasStation made a total of %.2f money.%n", getTotalMoneyFromFuelings());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private static String getAllFuelings() {
		StringBuilder sb = new StringBuilder();
		for (GasPump pump : pumps) {
			try (PreparedStatement ps = con
					.prepareStatement("SELECT * FROM station_loadings WHERE kolonka_id = ? ORDER BY loading_time;");) {
				ps.setInt(1, pump.getId());

				ResultSet rs = ps.executeQuery();
				sb.append(pump.toString() + " \n");
				while (rs.next()) {
					sb.append(String.format("Fuel:%s	Litres: %d	Loading_time: %s%n", rs.getString("fuel_type"),
							rs.getInt("fuel_quantity"), rs.getTimestamp("loading_time")));
				}
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}

		printReportsInFiles(sb.toString(), 1);
		return sb.toString();
	}

	private static String getAllFuelingsForTheDay() {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = con.prepareStatement("SELECT kolonka_id, COUNT(fuel_type) fuelings "
				+ "FROM station_loadings WHERE DATE(loading_time) = CURDATE() GROUP BY kolonka_id;");) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(String.format("Pump: %s	Total fuelings: %d%n", rs.getString("kolonka_id"),
						rs.getInt("fuelings")));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}

		printReportsInFiles(sb.toString(), 2);
		return sb.toString();
	}

	private static String getAllFuelingsByFuelType() {
		StringBuilder sb = new StringBuilder();
		try (PreparedStatement ps = con.prepareStatement("SELECT fuel_type, SUM(fuel_quantity) litres "
				+ "FROM station_loadings GROUP BY fuel_type ORDER BY fuel_type;");) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				sb.append(
						String.format("Fuel: %s	Total litres: %d%n", rs.getString("fuel_type"), rs.getInt("litres")));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		printReportsInFiles(sb.toString(), 3);
		return sb.toString();
	}
	
	private static double getTotalMoneyFromFuelings() {
		double totalCost = 0;
		try (PreparedStatement ps = con.prepareStatement("SELECT ROUND(SUM(price),2) money FROM station_loadings;");) {

			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				totalCost = rs.getDouble("money");
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		printReportsInFiles(String.format("The gasStation made %.2f money", totalCost), 4);
		return totalCost;
	}
	
	private static void printReportsInFiles(String content, int number) {
		File f = new File("Report-" + number + "-"+LocalDate.now()+".txt");
		try(FileWriter fw = new FileWriter(f);) {
			
			fw.write(content);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}
