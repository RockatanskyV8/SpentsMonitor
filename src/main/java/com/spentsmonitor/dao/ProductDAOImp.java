package com.spentsmonitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.spentsmonitor.model.Product;
import com.spentsmonitor.database.*;

public class ProductDAOImp implements ProductDAO{

	@Override
	public List<Product> AllProducts() throws ParseException{
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<Product> productsList = new ArrayList<Product>();
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id";
		try (Connection conn = DBConnector.connect("teste.db");
	         Statement stmt  = conn.createStatement();
	         ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	                productsList.add(
	                new Product(rs.getString("name"),
	                			rs.getDouble("cost"), 
	                			sdf.parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
	                			rs.getInt("quantity")));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return productsList;
	}

	@Override
	public void insertProduct(Product p) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO products (name) VALUES(?)";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.executeUpdate();
            insertCost(p);
        } catch (SQLException e) {
            System.out.println("insert error: " +e.getMessage());
        }
	}
	
	private void insertCost(Product p) {
		// TODO Auto-generated method stub
		String sql = "UPDATE costs SET quantity = ?, cost = ? WHERE bill_id IS NULL AND spent_day = date('now')";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert error: " + e.getMessage());
        }
	}

	@Override
	public void removeProduct(int id) {
		// TODO Auto-generated method stub
		String sql = "DELETE FROM products WHERE product_id = ?";
		try (Connection conn = DBConnector.connect("teste.db");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	@Override
	public void updateProduct(int id, Product p) {
		// TODO Auto-generated method stub
		String sql = "UPDATE products SET name = ? WHERE product_id = ?";
		try (Connection conn = DBConnector.connect("teste.db");
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, p.getName());
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
	}

	@Override
	public Product selectProduct(int id) throws ParseException {
		// TODO Auto-generated method stub
		Product p = null;
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String sql = "SELECT products.product_id, name, cost, quantity, costs.bill_id, strftime('%d/%m/%Y', costs.spent_day) "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id WHERE products.product_id = ?";
		try (Connection conn = DBConnector.connect("teste.db");
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	                p = new Product(rs.getString("name"),
                				rs.getDouble("cost"), 
                				sdf.parse(rs.getString("strftime('%d/%m/%Y', costs.spent_day)")), 
                				rs.getInt("quantity"));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return p;
	}

}
