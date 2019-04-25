package com.spentsmonitor.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {
	
	private static String url = "jdbc:sqlite:" + System.getProperty("user.dir") + "/db/";
	
	public static Connection connect(String fileName) {
	  Connection conn = null;
	  String db = url + fileName;
        try {
            conn = DriverManager.getConnection(db); 
        } catch (SQLException e) {
            System.out.println("connection error " + e.getMessage());
        }
        return conn;
	}
	
	public static Connection TestConnection(String fileName) {
		  Connection conn = null;
		  String db = url + fileName;
	        try {
	            conn = DriverManager.getConnection(db);
	            System.out.println("Connection to SQLite has been established.");   
	        } catch (SQLException e) {
	            System.out.println("connection error " + e.getMessage());
	        }
	        return conn;
		}
}
