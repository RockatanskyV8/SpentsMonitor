package com.spentsmonitor.database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBCreator {
	
	private static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/db/";
	
	public static void CreateNewDatabase(String fileName) {
		
		String db = url + fileName;
		
        try (Connection conn = DriverManager.getConnection(db)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }
 
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}
	
    public static void createTableBills(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
        		+ "CREATE TABLE IF NOT EXISTS bills (\n"//Bills Table
                + "	bill_id integer PRIMARY KEY,\n"
                + "	name text NOT NULL,\n"
                + "	bill_type integer NOT NULL\n"
                + ");\n";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTableProducts(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TABLE IF NOT EXISTS products (\n"//Products Table
                + "	product_id integer PRIMARY KEY,\n"
                + "	name text NOT NULL\n"
                + ");\n";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTableValues(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TABLE IF NOT EXISTS costs (\n"//Values Table
                + "	id integer PRIMARY KEY,\n"
                + " quantity integer DEFAULT 1,\n"
                + "	cost real,\n"
                + "	spent_day date NOT NULL,\n"
                + " profit real DEFAULT 0.0,\n"
                + "	product_id integer,\n"
                + "	bill_id integer,\n"
                + "	FOREIGN KEY(product_id) REFERENCES products(product_id),\n"
                + "	FOREIGN KEY(bill_id) REFERENCES products(bill_id)\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTableIncome(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TABLE IF NOT EXISTS income (\n"//Values Table
                + "	income_id integer PRIMARY KEY,\n"
                + " value real NOT NULL,\n"
                + " source text NOT NULL,\n"
                + " frequency_type integer NOT NULL,\n"
                + " frequency_num integer NOT NULL\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTableIncomes(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TABLE IF NOT EXISTS income_data (\n"//Values Table
                + "	income_data_id integer PRIMARY KEY,\n"
                + " income_day date NOT NULL,\n"
                + " income_id integer NOT NULL\n,"
                + " FOREIGN KEY(income_id) REFERENCES income(income_id)\n"
                + ");";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTriggerCostProducts(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TRIGGER IF NOT EXISTS costs_of_products AFTER INSERT ON products \n"
                + "	BEGIN\n"
                + " INSERT INTO costs "
                + "  (spent_day, product_id)"
                + " VALUES "
                + "  (date('now'),new.product_id);"
                + " END;";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void createTriggerCostBills(String fileName) {
        
    	String db = url + fileName;
        
        String sql = "" 
                + "CREATE TRIGGER IF NOT EXISTS costs_of_bills AFTER INSERT ON bills \n"
                + "	BEGIN\n"
                + " INSERT INTO costs "
                + "  (spent_day, bill_id)"
                + " VALUES "
                + "  (date('now'),new.bill_id);"
                + " END;";
        try (Connection conn = DriverManager.getConnection(db);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
}
