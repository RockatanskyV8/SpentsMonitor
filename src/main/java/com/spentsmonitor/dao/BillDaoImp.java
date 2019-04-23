package com.spentsmonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.spentsmonitor.database.DBConnector;
import com.spentsmonitor.model.Bill;
import com.spentsmonitor.model.enums.*;

public class BillDaoImp implements BillDao {

	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	@Override
	public List<Bill> AllBills() throws ParseException {
		// TODO Auto-generated method stub
		List<Bill> billsList = new ArrayList<Bill>();
		String sql = "SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id";
		try (Connection conn = DBConnector.connect("teste.db");
	         Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	            	billsList.add(
	            			new Bill(
	    	            			rs.getString("name"),
	    	            			rs.getDouble("cost"),
	    	            			rs.getInt("bill_type"),
	    	            			sdf.parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)"))
	            					))
	            			);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return billsList;
	}

	@Override
	public void insertBill(Bill b) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO bills (name, bill_type) VALUES(?,?)";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, b.getName());
            pstmt.setInt(2, b.getBillType().getValue());
            pstmt.executeUpdate();
            insertCost(b);
        } catch (SQLException e) {
            System.out.println("insert error: " +e.getMessage());
        }
	}
	
	private void insertCost(Bill b) {
		String sql = "UPDATE costs SET cost = ? WHERE product_id IS NULL AND spent_day = date('now')";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, b.getValue());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert error: " + e.getMessage());
        }
	}

	@Override
	public void removeBill(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM bills WHERE bill_id = ?";
		try (Connection conn = DBConnector.connect("teste.db");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	@Override
	public void updateBill(int id, Bill b) {
		// TODO Auto-generated method stub
	}

	@Override
	public Bill selectBill(int id) throws ParseException {
		String sql ="SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day)"
				   +" FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id"
				   +" WHERE bills.bill_id = ?";
		
		Bill b = null;
		
		try (Connection conn = DBConnector.connect("teste.db");
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	b = new Bill(
	            				rs.getString("name"),
	            				rs.getDouble("cost"),
	            				rs.getInt("bill_type"), 
	            				sdf.parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)")))
	            				);
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return b;
	}
	
	public List<Bill> selectBillByName(String name) throws ParseException{
		String sql ="SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day)"
				   +" FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id"
				   +" WHERE bills.name LIKE ?";
		
		List<Bill> billsList = new ArrayList<Bill>();
		
		try (Connection conn = DBConnector.connect("teste.db");
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1, '%' + name + '%');
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	billsList.add(new Bill(
	            				  rs.getString("name"),
	            				  rs.getDouble("cost"),
	            				  rs.getInt("bill_type"), 
	            				  sdf.parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)")))
	            				));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return billsList;
	}

}
