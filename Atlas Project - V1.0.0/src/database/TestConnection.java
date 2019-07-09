package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnection {
	public static void main(String[] argv) {
		System.out.println("-------- Derby JDBC Connection Testing ------------");
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		} catch (ClassNotFoundException e) {
			System.out.println("Where is your Derby JDBC Driver?");
			e.printStackTrace();
			return;
		}
		System.out.println("Derby JDBC Driver Registered!");
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:derby:C:\\Backup of student files\\Summer 2019\\Atlas project\\AtlasProject\\Atlas Project - V1.0.0\\src\\main\\resources\\db;create=true;upgrade=true;", "", "");
			if (connection != null) {
				System.out.println("You made it, take control your database now!");
			} else {
				System.out.println("Failed to make connection!");
			}
		} catch (SQLException e) {
			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
			return;
		} finally {
			if (connection != null) {
				try {
					if (!connection.isClosed()) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
