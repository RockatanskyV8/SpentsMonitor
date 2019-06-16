package com.spentsmonitor.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.spentsmonitor.database.DBConnector;
import com.spentsmonitor.model.Bill;
import com.spentsmonitor.exceptions.*;;

public class BillDaoImp implements BillDao {
	
	private String bdName;

	private SimpleDateFormat sdf(String format) { return new SimpleDateFormat(format); }
	
	public BillDaoImp(String bdName) {
		this.bdName = bdName;
	}
	
	@Override
	public List<Bill> AllBills(){
		// TODO Auto-generated method stub
		List<Bill> billsList = new ArrayList<Bill>();
		String sql = "SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id";
		try (Connection conn = DBConnector.connect(bdName);
	         Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	            	billsList.add(
	            			new Bill(
	    	            			rs.getString("name"),
	    	            			rs.getDouble("cost"),
	    	            			rs.getInt("bill_type"),
	    	            			sdf("dd/MM/yyyy").parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)"))
	            					))
	            			);
	            }
	        } catch (SQLException e) {
	            throw new DBException("List error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return billsList;
	}

	@Override
	public void insertBill(Bill b){
		Bill aux = selectBillByName(b.getName());
		
		if(aux == null) {
			newBill(b);
		} else {
			newCost(b);
		}
	}
	
	private void newBill(Bill b) {
		String sql = "INSERT INTO bills (name, bill_type) VALUES(?,?)";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, b.getName());
            pstmt.setInt(2, b.getBillType().getValue());
            pstmt.executeUpdate();
            insertCost(b);
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }
	}
	
	private void insertCost(Bill b) {
		String sql = "UPDATE costs SET cost = ?, spent_day = ?"
				   + " WHERE product_id IS NULL AND spent_day = date('now') AND bill_id = (SELECT MAX(bill_id) FROM bills)";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, b.getValue());
            pstmt.setString(2, sdf("yyyy-MM-dd").format(b.getPaymentDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }
	}
	
	private void newCost(Bill b) {
		String sql = "INSERT INTO costs (cost, spent_day, bill_id)"
				   + " VALUES (?,?,(SELECT bill_id FROM bills WHERE name = ?))";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, b.getValue());
            pstmt.setString(2, sdf("yyyy-MM-dd").format(b.getPaymentDate()));
            pstmt.setString(3, b.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }
	}

	@Override
	public void removeBill(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM bills WHERE bill_id = ?";
		try (Connection conn = DBConnector.connect(bdName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Delete error: " + e.getMessage());
        }
	}

	@Override
	public void updateBill(int id, Date d, Bill b) {
		String sql = "UPDATE bills SET name = ?, bill_type = ? WHERE bill_id = ?";
		try (Connection conn = DBConnector.connect(bdName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, b.getName());
			pstmt.setInt(2, b.getBillType().getValue());
            pstmt.setInt(3, id);
            pstmt.executeUpdate();
            updateCosts(id, d, b);
        } catch (SQLException e) {
        	throw new DBException("Alteration error: " + e.getMessage());
        }
	}

	private void updateCosts(int id, Date d, Bill b) {
		String sql =  "UPDATE costs SET cost = ?, spent_day = ?"
				    + " WHERE bill_id = ? AND spent_day = ?";
		try (Connection conn = DBConnector.connect(bdName);
	        PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setDouble(1, b.getValue());
	        pstmt.setString(2, sdf("yyyy-MM-dd").format(b.getPaymentDate()));
	        pstmt.setInt(3, id);
	        pstmt.setString(4, sdf("yyyy-MM-dd").format(d));
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	    	throw new DBException("Alteration error: " + e.getMessage());
	    }
		
	}
	

	@Override
	public Bill selectBill(int id) {
		String sql ="SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day)"
				   +" FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id"
				   +" WHERE bills.bill_id = ?";
		
		Bill b = null;
		
		try (Connection conn = DBConnector.connect(bdName);
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	b = new Bill(
	            				rs.getString("name"),
	            				rs.getDouble("cost"),
	            				rs.getInt("bill_type"), 
	            				sdf("yyyy-MM-dd").parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)")))
	            				);
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return b;
	}
	
	public Bill selectBillByName(String name){
		String sql ="SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day)"
				   +" FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id"
				   +" WHERE bills.name = ?";
		
		Bill bill = null;
		
		try (Connection conn = DBConnector.connect(bdName);
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1, '%' + name + '%');
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	bill = new Bill(
	            				  rs.getString("name"),
	            				  rs.getDouble("cost"),
	            				  rs.getInt("bill_type"), 
	            				  sdf("yyyy-MM-dd").parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)")))
	            				);
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return bill;
	}
	
	public List<Bill> searchBillByName(String name){
		String sql ="SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day)"
				   +" FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id"
				   +" WHERE bills.name LIKE ?";
		
		List<Bill> billsList = new ArrayList<Bill>();
		
		try (Connection conn = DBConnector.connect(bdName);
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1, '%' + name + '%');
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	billsList.add(new Bill(
	            				  rs.getString("name"),
	            				  rs.getDouble("cost"),
	            				  rs.getInt("bill_type"), 
	            				  sdf("yyyy-MM-dd").parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)")))
	            				));
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Search error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return billsList;
	}
	
	public List<Bill> searchBillByDate(Date init, Date end){
		List<Bill> billsList = new ArrayList<Bill>();
		String sql = "SELECT bills.bill_id, name, bill_type, cost, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM bills INNER JOIN costs ON bills.bill_id = costs.bill_id "
				   + "WHERE costs.spent_day BETWEEN ? AND ?";
		try (Connection conn = DBConnector.connect(bdName);
			 PreparedStatement pstmt  = conn.prepareStatement(sql)){
				pstmt.setString(1, sdf("yyyy-MM-dd").format(init));
	            pstmt.setString(2, sdf("yyyy-MM-dd").format(end));
	            ResultSet rs  = pstmt.executeQuery();
				while (rs.next()) {
		            	billsList.add(
		            			new Bill(
		    	            			rs.getString("name"),
		    	            			rs.getDouble("cost"),
		    	            			rs.getInt("bill_type"),
		    	            			sdf("dd/MM/yyyy").parse((rs.getString("strftime('%d/%m/%Y', costs.spent_day)"))
		            					))
		            			);
		            }
	        } catch (SQLException e) {
	            throw new DBException("List error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return billsList;
	}

}