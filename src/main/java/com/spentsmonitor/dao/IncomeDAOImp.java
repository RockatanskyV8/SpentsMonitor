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
import com.spentsmonitor.exceptions.DBException;
import com.spentsmonitor.exceptions.DateException;
import com.spentsmonitor.model.Income;

public class IncomeDAOImp implements IncomeDAO{
	
	private String bdName;
	
	private SimpleDateFormat sdf(String format) { return new SimpleDateFormat(format); }
	
	public IncomeDAOImp(String bdName) {
		this.bdName = bdName;
	}

	@Override
	public List<Income> AllIncomes() {
		
		String sql = "SELECT value, source, strftime('%d/%m/%Y', income_data.income_day), frequency_type, frequency_num FROM income"
					+" INNER JOIN income_data"
					+" ON income.income_id = income_data.income_id";
		
		List<Income> incomeList = new ArrayList<Income>();
		
		try (Connection conn = DBConnector.connect(bdName);
		         Statement stmt  = conn.createStatement();
		         ResultSet rs    = stmt.executeQuery(sql)){
		            while (rs.next()) {
		            	incomeList.add(
		                new Income(rs.getDouble("value"), 
		                		   rs.getString("source"), 
		                		   sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', income_data.income_day)")),
		                		   rs.getInt("frequency_type"), 
		                		   rs.getInt("frequency_num"))
		                );
		            }
		        } catch (SQLException e) {
		        	throw new DBException("List error: " + e.getMessage());
		        } catch (ParseException e) {
		        	throw new DateException("Date format error: " + e.getMessage());
				}
		
		return incomeList;
	}

	@Override
	public void insertIncome(Income i) {
		Income aux = selectIncomeBySource(i.getSource());
		
		if(aux == null) {
			newIncome(i);
		} else {
			insertIncomeDate(i);
		}
		
	}
	
	private void newIncome(Income i) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO income (value, source, frequency_type, frequency_num) "
					+"VALUES (?, ?, ?, ?)";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, i.getValue());
            pstmt.setString(2, i.getSource());
            pstmt.setInt(3, i.getFrequencyType().getValue());
            pstmt.setInt(4, i.getFrequencyNumber());
            pstmt.executeUpdate();
            insertIncomeDate(i);
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        } 
	}
	
	private void insertIncomeDate(Income i) {
		String sql = "INSERT INTO income_data (income_day, income_id) "
					+"VALUES (?, (SELECT income_id FROM income WHERE value = ? AND source = ?) )";
		Connection conn = DBConnector.connect(bdName);
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, sdf("yyyy-MM-dd").format(i.getIncomeDay()));
	        pstmt.setDouble(2, i.getValue());
	        pstmt.setString(3, i.getSource());
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	    	throw new DBException("Insert error: " + e.getMessage());
	    } 
	}

	@Override
	public void removeIncome(int id) {
		String sql = "DELETE FROM income WHERE income_id = ?";
		Connection conn = DBConnector.connect(bdName);
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setInt(1, id);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	    	throw new DBException("Delete error: " + e.getMessage());
	    } 
	}

	@Override
	public void updateFrequentIncome(int id, Income i) {
		String sql =  "UPDATE income SET frequency_type = 5"
					+ " WHERE income_id = ? AND frequency_type != 5";
		try (Connection conn = DBConnector.connect(bdName);
	        PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
	        pstmt.executeUpdate();
	        updatedIncome(i);
	    } catch (SQLException e) {
	    	throw new DBException("Alteration error: " + e.getMessage());
	    }
	}
	
	private void updatedIncome(Income i) {
		String sql = "INSERT INTO income (value, source, frequency_type, frequency_num) "
					+"VALUES (?, ?, ?, ?)";
		Connection conn = DBConnector.connect(bdName);
	    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setDouble(1, i.getValue());
	        pstmt.setString(2, i.getSource());
	        pstmt.setInt(3, i.getFrequencyType().getValue());
	        pstmt.setInt(4, i.getFrequencyNumber());
	        pstmt.executeUpdate();
	        insertIncomeDate(i);
	    } catch (SQLException e) {
	    	throw new DBException("Alteration error: " + e.getMessage());
	    }
	}

	@Override
	public Income selectIncomeBySource(String source){
		
		String sql = "SELECT value, source, income_data.income_day, frequency_type, frequency_num FROM income"
				+" INNER JOIN income_data"
				+" ON income.income_id = income_data.income_id WHERE source = ?";
	
		Income in = null;
		
		try (Connection conn = DBConnector.connect(bdName);
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1,source);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	in = new Income(rs.getDouble("value"), 
	                		   rs.getString("source"), 
	                		   sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', income_data.income_day)")),
	                		   rs.getInt("frequency_type"), 
	                		   rs.getInt("frequency_num"));
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return in;
	}
	
	public List<Income> searchIncomeByDate(Date init, Date end){
		String sql = "SELECT value, source, strftime('%d/%m/%Y', income_data.income_day), frequency_type, frequency_num FROM income"
				+" INNER JOIN income_data"
				+" ON income.income_id = income_data.income_id"
				+ "WHERE income_data.income_day BETWEEN ? AND ?";
	
	List<Income> incomeList = new ArrayList<Income>();
	
	try (Connection conn = DBConnector.connect(bdName);
		 PreparedStatement pstmt  = conn.prepareStatement(sql)){
			pstmt.setString(1, sdf("yyyy-MM-dd").format(init));
            pstmt.setString(2, sdf("yyyy-MM-dd").format(end));
            ResultSet rs  = pstmt.executeQuery();
            while (rs.next()) {
            	incomeList.add(
                new Income(rs.getDouble("value"), 
                		   rs.getString("source"), 
                		   sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', income_data.income_day)")),
                		   rs.getInt("frequency_type"), 
                		   rs.getInt("frequency_num"))
                );
            }
	        } catch (SQLException e) {
	        	throw new DBException("List error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
	
		return incomeList;
	}

}