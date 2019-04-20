package com.spentsmonitor.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import com.spentsmonitor.model.Product;
import com.spentsmonitor.database.*;

public class ProductDAOImp implements ProductDAO{

	@Override
	public List<Product> AllProducts() {
		// TODO Auto-generated method stub
		//List<Product> productsList = new ArrayList();
		String sql = "SELECT products.product_id, name, cost, quantity "
				   + "FROM products INNER JOIN costs ON products.product_id = costs.product_id";
		try (Connection conn = DBConnector.connect("teste.db");
	             Statement stmt  = conn.createStatement();
	             ResultSet rs    = stmt.executeQuery(sql)){
	            while (rs.next()) {
	                System.out.println(
	                				   rs.getInt("product_id")    +  "\t" +
	                				   rs.getString("name")    +  "\t" +
	                				   rs.getDouble("cost")    +  "\t" +
	                				   rs.getDouble("quantity")+  "\t");
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
		return null;
	}

	@Override
	public void insertProduct(Product p) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO products (name) VALUES(?)";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, p.getName());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert error: " +e.getMessage());
        }
	}
	
	@Override
	public void insertCost(Product p) {
		// TODO Auto-generated method stub
		String sql = "INSERT INTO costs (quantity, cost, spentDay, product_id) VALUES (?,?,?,?)";
		Connection conn = DBConnector.connect("teste.db");
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, p.getQuantity());
            pstmt.setDouble(2, p.getPrice());
            pstmt.setDate(3, (java.sql.Date) p.getBuyDate());
            pstmt.setInt(4, getProductID(p.getName()));
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("insert error: " + e.getMessage());
        }
	}
	
	@Override
	public int getProductID(String name) {
		return 0;
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
	public void selectProduct(int id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM products WHERE product_id = ?";
		try (Connection conn = DBConnector.connect("teste.db");
	             PreparedStatement pstmt  = conn.prepareStatement(sql)){
	            pstmt.setInt(1,id);
	            ResultSet rs  = pstmt.executeQuery();
	            while (rs.next()) {
	                System.out.println(rs.getInt("product_id") +  "\t" + rs.getString("name"));
	            }
	        } catch (SQLException e) {
	            System.out.println(e.getMessage());
	        }
	}

}
