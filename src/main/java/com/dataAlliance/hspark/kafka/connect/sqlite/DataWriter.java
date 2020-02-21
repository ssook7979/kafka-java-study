package com.dataAlliance.hspark.kafka.connect.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataWriter {
	private Connection connection;
	private Statement statement;
	private String dbFileName;
	private boolean isOpened = false;

	public final static String DATABASE = "test.db"; 
	static {
		try { 
			Class.forName("org.sqlite.JDBC"); 
		} catch(Exception e) { 
			e.printStackTrace(); 
		} 
	}
	
	public DataWriter(String databaseFileName) {
		this.dbFileName = databaseFileName; 
	}
	
	public boolean open() {
		try {
			/*
			SQLiteConfig config = new SQLiteConfig();
			config.enableRecursiveTriggers(true);
			connection = DriverManager.getConnection("jdbc:sqlite:/" + this.dbFileName, config.toProperties());
			*/
			
			connection = DriverManager.getConnection("jdbc:sqlite:" + this.dbFileName);
			statement = connection.createStatement();
			
			String sql = "CREATE TABLE FROM_KAFKA " +
                    "(ID INT PRIMARY KEY AUTOINCREMENT," + 
                    " RANDINT        INT      NOT NULL, " + 
                    " TOPIC          CHAR(50) NOT NULL, " + 
                    " DATETIME       CHAR(50) NOT NULL)";
			
			if (statement.executeUpdate(sql) > 0) {
				System.out.println(sql + "has been executed.");				
			} else {
				System.out.println("Table creation has been failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		isOpened = true;
		return true;
	}
	
	public boolean put(Integer randint, String topic, Long timestamp) {
		if (!isOpened) {
			open();
		}
		
		String sql = "INSERT INTO FROM_KAFKA "
				+ "(RANDINT, TOPIC, DATETIME) "
				+ "VALUES ("
				+ randint + ", "
				+ topic + ", "
				+ "datetime('now')"
				+ ");";
		
		try {
			if (statement.executeUpdate(sql) > 0) {
				System.out.println(sql + "has been executed.");				
			} else {
				System.out.println("Table creation has been failed.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}
	
	public boolean close() {
		if (this.isOpened == false) return true;
		
		try {
			this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
