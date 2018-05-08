package demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import docks.Crane;
import docks.Dock;
import docks.InvalidDockDataException;
import docks.ShipyardDBManager;
import docks.StorageRecord;
import ships.InvalidShipDataException;
import ships.Ship;
import storages.Storage;

public class Shipyard {
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

	public static final Set<StorageRecord> records = new TreeSet<StorageRecord>((sr1, sr2) -> {
		return sr1.getUnloadingTime().compareTo(sr2.getUnloadingTime());
	});
	public static final HashSet<Dock> docks = new HashSet<>();
	public static final HashSet<Ship> ships = new HashSet<>();
	public static final HashSet<Storage> storages = new HashSet<>();
	public static final HashSet<Crane> cranes = new HashSet<>();
	public static final Connection con = ShipyardDBManager.getInstance().getCon();

	public static final Storage getRandomStorage() {
		ArrayList<Storage> storages = new ArrayList<>(Shipyard.storages);
		return storages.get(RNG(storages.size()));
	}

	public static void main(String[] args) {
		// 1. Create docks

		for (int i = 0; i < 5; i++) {
			try {
				Shipyard.docks.add(new Dock(i + 1));
			}
			catch (InvalidDockDataException e) {
				e.printStackTrace();
			}
		}

		// 2. Create ships
		for (int i = 0; i < 10; i++) {
			try {
				Shipyard.ships.add(new Ship("Ship " + (i + 1)));
			}
			catch (InvalidShipDataException e) {
				e.printStackTrace();
			}
		}

		// 3. Create storages
		for (int i = 0; i < 2; i++) {
			try {
				Shipyard.cranes.add(new Crane());
				Shipyard.storages.add(new Storage());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		while(true) {
			printStatistics();
		}
	}

	public static final void printStatistics() {
		while(true) {
			try {
				Thread.sleep(10000);
				System.out.println(getAllShipments());
				System.out.println(getShipsForCurrentDay());
				System.out.println(getShipmentsByCranes());
				System.out.println(getBestShip());
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static final String getShipsForCurrentDay() {
		StringBuilder ships = new StringBuilder();
		try(PreparedStatement ps = con.prepareStatement("SELECT dock_id, COUNT(dock_id) as ships FROM port_shipments\r\n" + 
				"WHERE DATE(unloading_time) = CURDATE()\r\n" + 
				"	GROUP BY dock_id ORDER BY dock_id;");){
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ships.append(String.format("Dock:%d, Total ships: %d %n", rs.getInt("dock_id"), rs.getInt("ships")));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return ships.toString();
	}
	
	
	public static final String getBestShip() {
		StringBuilder ship = new StringBuilder();
		try(PreparedStatement ps = con.prepareStatement("SELECT boat_name, COUNT(package_id) as sum FROM port_shipments GROUP By boat_name ORDER BY sum DESC LIMIT 1;");){
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				ship.append(String.format("Ship %s, Total shipments %d%n", rs.getString("boat_name"), rs.getInt("sum")));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return ship.toString();
	}
	
	public static final String getShipmentsByCranes() {
		StringBuilder cranes = new StringBuilder();
		try(PreparedStatement ps = con.prepareStatement("SELECT crane_id, count(*) as sum FROM port_shipments GROUP BY crane_id ORDER BY crane_id;");){
			
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				cranes.append(String.format("Crane %s, Total shipments %d%n", rs.getInt("crane_id"), rs.getInt("sum")));
			}
			rs.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return cranes.toString();
	}
	
	public static final String getAllShipments() {
		StringBuilder shipments = new StringBuilder();
		for (Dock dock : docks) {
			shipments.append("Dock " + dock.getNumber() + "\n");
			try(PreparedStatement ps = con.prepareStatement("SELECT * FROM port_shipments WHERE dock_id = ? ORDER BY unloading_time;");){
				ps.setInt(1, dock.getNumber());
				
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					shipments.append(String.format("Shipment %d	Ship: %s Crane: %d	Unloading time: %s%n",
							rs.getInt("package_id"), rs.getString("boat_name"), rs.getInt("crane_id"), rs.getTimestamp("unloading_time")));
				}
				rs.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return shipments.toString();
	}

	public static final void saveRecordToDB(StorageRecord record) {
		try (PreparedStatement ps = con.prepareStatement(
				"INSERT INTO port_shipments (boat_name, dock_id, crane_id, unloading_time, package_id) VALUES("
						+ "?,?,?,?,?);");) {
			ps.setString(1, record.getShip().getName());
			ps.setInt(2, record.getDock().getNumber());
			ps.setInt(3, record.getCrane().getId());
			ps.setTimestamp(4, java.sql.Timestamp.valueOf(record.getUnloadingTime()));
			ps.setInt(5, record.getShipment().getId());
			ps.executeUpdate();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
