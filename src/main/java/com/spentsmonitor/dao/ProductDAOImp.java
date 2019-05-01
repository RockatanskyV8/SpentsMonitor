package com.spentsmonitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Product;
import com.spentsmonitor.database.*;
import com.spentsmonitor.exceptions.DBException;
import com.spentsmonitor.exceptions.DateException;

public class ProductDAOImp implements ProductDAO{

	private String bdName;
	
	private SimpleDateFormat sdf(String format) { return new SimpleDateFormat(format); }
	
	public ProductDAOImp(String bdName) {
		this.bdName = bdName;
	}

	@Override
	public List<Product> AllProducts(){
		List<Product> productsList = new ArrayList<Product>();
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id";
		try (Connection conn = DBConnector.connect(bdName);
	         Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	                productsList.add(
	                new Product(rs.getString("name"),
	                			rs.getDouble("cost"), 
	                			sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
	                			rs.getInt("quantity")));
	            }
	        } catch (SQLException e) {
	        	throw new DBException("List error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return productsList;
	}

	@Override
	public void insertProduct(Product p) {
		Product aux = selectProductByName(p.getName());
		
		if(aux == null) {
			newProduct(p);
		} else {
			newCost(p);
		}
	}
	
	private void newProduct(Product p) {
		String sql = "INSERT INTO products (name) VALUES(?)";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.executeUpdate();
            insertCost(p);
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }        
	}
	
	private void insertCost(Product p) {
		String sql = "UPDATE costs SET quantity = ?, cost = ?, spent_day = ?"
				   + " WHERE bill_id IS NULL AND spent_day = date('now') AND product_id = (SELECT MAX(product_id) FROM products)";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setString(3, sdf("yyyy-MM-dd").format(p.getBuyDate()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }
	}
	
	private void newCost(Product p) {
		String sql = "INSERT INTO costs (quantity, cost, spent_day, product_id)"
				   + " VALUES (?,?,?,(SELECT product_id FROM products WHERE name = ?))";
		Connection conn = DBConnector.connect(bdName);
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setString(3, sdf("yyyy-MM-dd").format(p.getBuyDate()));
            pstmt.setString(4, p.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Insert error: " + e.getMessage());
        }
	}

	@Override
	public void removeProduct(int id) {
		String sql = "DELETE FROM products WHERE product_id = ?";
		try (Connection conn = DBConnector.connect(bdName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Delete error: " + e.getMessage());
        }
	}

	@Override
	public void updateProduct(int id, Date d, Product p) {
		String sql = "UPDATE products SET name = ? WHERE product_id = ?";
		try (Connection conn = DBConnector.connect(bdName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, p.getName());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            updateCosts(id, d, p);
        } catch (SQLException e) {
        	throw new DBException("Alteration error: " + e.getMessage());
        }
	}
	
	private void updateCosts(int id, Date d, Product p) {
		String sql =  "UPDATE costs SET quantity = ?, cost = ?, spent_day = ?"
					+ " WHERE product_id = ? AND spent_day = ?";
		try (Connection conn = DBConnector.connect(bdName);
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setString(3, sdf("yyyy-MM-dd").format(p.getBuyDate()));
            pstmt.setInt(4, id);
            pstmt.setString(5, sdf("yyyy-MM-dd").format(d));
            pstmt.executeUpdate();
        } catch (SQLException e) {
        	throw new DBException("Alteration error: " + e.getMessage());
        }
	}

	@Override
	public Product selectProduct(int id) {
		Product p = null;
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id WHERE products.product_id = ?";
		try (Connection conn = DBConnector.connect(bdName);
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	                p = new Product(rs.getString("name"),
                				rs.getDouble("cost"), 
                				sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
                				rs.getInt("quantity"));
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return p;
	}
	
	@Override
	public Product selectProductByName(String name) {
		Product p = null;
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id WHERE products.name = ?";
		try (Connection conn = DBConnector.connect(bdName);
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1,name);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	                p = new Product(rs.getString("name"),
                				rs.getDouble("cost"), 
                				sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
                				rs.getInt("quantity"));
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return p;
	}
	
	@Override
	public List<Product> searchProduct(String name) {
		List<Product> productsList = new ArrayList<Product>();
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id WHERE products.name LIKE ?";
		try (Connection conn = DBConnector.connect(bdName);
	         PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setString(1, '%' + name + '%');
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	            	productsList.add(
	                new Product(rs.getString("name"),
                				rs.getDouble("cost"), 
                				sdf("dd/MM/yyyy").parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
                				rs.getInt("quantity"))
	                );
	            }
	        } catch (SQLException e) {
	        	throw new DBException("Return error: " + e.getMessage());
	        } catch (ParseException e) {
	        	throw new DateException("Date format error: " + e.getMessage());
			}
		return productsList;
	}
}