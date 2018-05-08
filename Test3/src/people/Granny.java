package people;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import demo.DBManager;

public class Granny extends Person {
	private static final Connection CON = DBManager.getInstance().getCon();
	
	public Granny() {
		super("Granny");
		
		Thread t = new Thread(()->{
			while(true) {
				try {
					Thread.sleep(10000);
					printStats();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		t.setDaemon(true);
		t.start();
	}
	
	//Methods
	private void printStats() {
		System.out.println(countColoredEggs());
		System.out.println(countPartyEggs());
		System.out.println(getBestJar());
		System.out.println(countGysokGreenEggs());
		System.out.println(getWorstKid());
	}
	
	private String countColoredEggs() {
		StringBuilder content = new StringBuilder("Total number of eggs colored so far: ");
		try(PreparedStatement ps = CON.prepareStatement("SELECT COUNT(id) as total_eggs FROM eggs;")){
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					content.append(rs.getInt("total_eggs"));
				}
			}
			//Print report in file
			printReportInFile("TotalEggsReport-" + LocalDate.now()+"txt", content.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	private String countPartyEggs() {
		StringBuilder content = new StringBuilder("Total number of eggs party eggs so far: ");
		try(PreparedStatement ps = CON.prepareStatement("SELECT COUNT(id) as party_eggs FROM eggs WHERE is_partycolored = TRUE;")){
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					content.append(rs.getInt("party_eggs"));
				}
			}
			
			//Print report in file
			printReportInFile("PartyEggsReport-" + LocalDate.now()+"txt", content.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	private String countGysokGreenEggs() {
		StringBuilder content = new StringBuilder("Total number of green gysok eggs so far: ");
		try(PreparedStatement ps = CON.prepareStatement("SELECT count(id) AS eggs, j.id FROM eggs AS e"
				+ "JOIN egg_types AS et ON(e.type_id = et.id)"
				+ "JOIN jars AS j ON(e.jar_id = j.id)"
				+ "WHERE j.id = 1 AND et.id = 3")){ //J.id  = 1 --> Green eggs //et.id = 3 -->gysok eggs
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					content.append(rs.getInt("eggs"));
				}
			}
			//Print report in file
			printReportInFile("GysokGreenEggsReport-" + LocalDate.now()+"txt", content.toString());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	private String getBestJar() {
		StringBuilder content = new StringBuilder("Best jar is: ");
		try(PreparedStatement ps = CON.prepareStatement("SELECT e.jar_id, e.SUM(id) as total_eggs, j.color  FROM eggs as e"
				+ " JOIN jars as j ON (e.jar_id = j.id) GROUP BY jar_id ORDER BY total_eggs DESC LIMIT 1");){
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					content.append(String.format("%d with a total of %d eggs and %s color", 
							rs.getInt("jar_id"), rs.getInt("total_eggs"), rs.getString("color")));
				}
			}
			
			//Print report in file
			printReportInFile("BestJarReport-" + LocalDate.now()+"txt", content.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	private String getWorstKid() {
		StringBuilder content = new StringBuilder("Worst kid is: ");
		try(PreparedStatement ps = CON.prepareStatement("SELECT e.kid_id, e.SUM(id) as total_eggs, k.name FROM eggs as e"
				+ " JOIN kids as k ON (e.kid_id = k.id) GROUP BY kid_id ORDER BY total_eggs ASC LIMIT 1");){
			try(ResultSet rs = ps.executeQuery()){
				if(rs.next()) {
					content.append(String.format("%d with a total of %d", 
							rs.getString("name"), rs.getInt("total_eggs")));
				}
			}
			//Print report in file
			printReportInFile("WorstKidReport-" + LocalDate.now()+"txt", content.toString());
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return content.toString();
	}
	
	private void printReportInFile(String fileName, String content) {
		File f = new File(fileName);
		try(FileWriter fw = new FileWriter(f)){
			fw.write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
