package demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBManager {
	private static final String DB_HOST = "localhost";
	private static final String DB_PORT = "3306";
	private static final String DB_NAME = "task_eggs";
	private static final String DB_USER = "root";
	private static final String DB_PASS = "123456";
	//Fields
	private static DBManager instance;
	private static Connection con;
	
	//Constructors
	private DBManager() {
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
					DBManager.con = DriverManager.getConnection(
							String.format("jdbc:mysql://%s:%s/%s", DB_HOST, DB_PORT, DB_NAME),DB_USER, DB_PASS );
				}
				catch (SQLException e) {
					e.printStackTrace();
				}
	}
	
	//Methods
	
	public synchronized static DBManager getInstance() {
		if(instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	public synchronized static Connection getCon() {
		return con;
	}
}
