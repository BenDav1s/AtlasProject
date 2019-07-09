package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.derby.iapi.error.StandardException;

public class TestDatabase {
	/*
	private static final String DB_DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DB_CONNECTION = "jdbc:derby:C:\\Backup of student files\\Summer 2019\\Atlas project\\AtlasProject\\Atlas Project - V1.0.0\\src\\main\\resources\\db;create=true;upgrade=true;";
	private static final String DB_USER = "";
	private static final String DB_PASSWORD = "";
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static void main(String[] argv) {
		try {
			//createDbUserTable();
			
			Employee temp = new Employee();
			temp.name = "fat";
			temp.age = 12;
			temp.email = "bob@gmail.com";
			temp.gender = "male";
			temp.salary = (long) 1222;
			save(temp);
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	private static void createDbUserTable() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String createTableSQL = "CREATE TABLE Employee(" + "ID BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY "
				+ "(START WITH 1, INCREMENT BY 1), "
				+ "NAME VARCHAR(255) NOT NULL, " + "EMAIL VARCHAR(255) NOT NULL, "
				+ "AGE INTEGER NOT NULL, " + "GENDER VARCHAR(255) NOT NULL, " 
				+ "SALARY BIGINT NOT NULL" + ")";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			statement.executeUpdate("CALL SYSCS_UTIL.SYSCS_SET_DATABASE_PROPERTY(" +
				    "'derby.language.sequence.preallocator', '1')");
			
			System.out.println(createTableSQL);
			// execute the SQL stetement
			statement.execute(createTableSQL);
			System.out.println("Table \"Employee\" is created!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
	/**
	 * Insert record into table
	 * @throws SQLException
	 */
	/*
	public static void save(Employee record) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		boolean update = false;
		if(record.id != null) {
			update = true;
		}
		String updateTableSQL = "UPDATE EMPLOYEE" 
		+" SET NAME = '" + record.name 
		+"', EMAIL = '" + record.email 
		+"', AGE = " + record.age 
		+ ", SALARY = " + record.salary 
		+", GENDER = '" + record.gender + "' WHERE "
				+ "ID = " + record.id;
		
		String insertTableSQL = "INSERT INTO EMPLOYEE" + "(NAME, EMAIL, AGE, GENDER, SALARY) " + "VALUES"
				+ "('" + record.name + "', '" 
				+ record.email + "', " + record.age + ", '" + record.gender + "', "
				+ record.salary + ")";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			if(update == false) {
				System.out.println(insertTableSQL);	
				statement.executeUpdate(insertTableSQL);
				System.out.println("Record is inserted into Employee table!");
			}
			else {
				System.out.println(updateTableSQL);
				statement.executeUpdate(updateTableSQL);
				System.out.println("Record is updated in Employee table!");
			}
			
			// execute insert SQL stetement
			
		} catch (SQLException e) {
			throw e;
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	public static Employee findById(int id) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * from EMPLOYEE WHERE ID = " + id;
		Employee temp = new Employee();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					Long userid = rs.getLong("ID");
					String name = rs.getString("NAME");
					String email = rs.getString("EMAIL");
					Integer age = rs.getInt("AGE");
					String gender = rs.getString("GENDER");
					Long salary = rs.getLong("SALARY");
					
					temp.age = age;
					temp.id = userid;
					temp.gender = gender;
					temp.name = name;
					temp.email = email;
					temp.salary = salary;
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return temp;
	}
	
	public static void delete(int id) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String deleteTableSQL = "DELETE FROM EMPLOYEE WHERE ID = " + id;
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(deleteTableSQL);
			// execute delete SQL stetement
			statement.execute(deleteTableSQL);
			System.out.println("Record is deleted from DBUSER table!");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
	}
	
	public static Set<Employee> findAll() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		Set<Employee> allEmps = new HashSet<>();
		String selectTableSQL = "SELECT * from EMPLOYEE";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					Employee temp = new Employee();
					Long userid = rs.getLong("ID");
					String name = rs.getString("NAME");
					String email = rs.getString("EMAIL");
					Integer age = rs.getInt("AGE");
					String gender = rs.getString("GENDER");
					Long salary = rs.getLong("SALARY");
					
					temp.age = age;
					temp.id = userid;
					temp.gender = gender;
					temp.name = name;
					temp.email = email;
					temp.salary = salary;
					allEmps.add(temp);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return allEmps;
	}
	public static int count() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		int count = 0;
		String selectTableSQL = "SELECT COUNT(*) AS total FROM EMPLOYEE";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			rs.next();
			count = rs.getInt("total");
			
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return count;
	}
	public static Set<Employee> find(String filter) throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String selectTableSQL = "SELECT * FROM EMPLOYEE WHERE " + filter;
		Set<Employee> allEmps = new HashSet<>();
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();
			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			if (rs.next() == false) {
				System.out.println("ResultSet in empty in Java");
			} else {
				do {
					Employee temp = new Employee();
					Long userid = rs.getLong("ID");
					String name = rs.getString("NAME");
					String email = rs.getString("EMAIL");
					Integer age = rs.getInt("AGE");
					String gender = rs.getString("GENDER");
					Long salary = rs.getLong("SALARY");
					
					System.out.println("id : " + userid);
					System.out.println("name : " + name);
					System.out.println("email : " + email);
					System.out.println("age : " + age);
					System.out.println("gender : " + gender);
					System.out.println("salary : " + salary);
					
					temp.age = age;
					temp.id = userid;
					temp.gender = gender;
					temp.name = name;
					temp.email = email;
					temp.salary = salary;
					allEmps.add(temp);
				} while (rs.next());
			}
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		} finally {
			if (statement != null) {
				statement.close();
			}
			if (dbConnection != null) {
				dbConnection.close();
			}
		}
		return allEmps;
	}

	/**
	 * Get current time stamp 
	 * @return
	 */
	/*
	private static String getCurrentTimeStamp() {
		java.util.Date today = new java.util.Date();
		return dateFormat.format(today.getTime());
	}*/
}
