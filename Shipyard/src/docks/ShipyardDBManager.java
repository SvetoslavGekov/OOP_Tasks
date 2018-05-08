package docks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ShipyardDBManager {
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "task_docks";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "123456";
	//Fields
	private static ShipyardDBManager instance;
	private static Connection con;
	
	//Constructors
	private ShipyardDBManager() {
		//1. Testing if the class (driver) is loaded in the application
				String jdbcDriver = "com.mysql.jdbc.Driver";
				
				try {
					Class.forName(jdbcDriver);
				}
				catch (ClassNotFoundException e) {
					System.out.println("Sorry, driver not loaded or does not exist. Aborting!");
					
				}
				
				System.out.println("Driver successfully loaded.");
				
				
				//2. Create connection
				try {
					ShipyardDBManager.con = DriverManager.getConnection(
							String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME),DB_USER, DB_PASS );
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
	}
	
	//Methods
	
	public synchronized static ShipyardDBManager getInstance() {
		if(instance == null) {
			instance = new ShipyardDBManager();
		}
		return instance;
	}
	
	public synchronized static Connection getCon() {
		return con;
	}
}
