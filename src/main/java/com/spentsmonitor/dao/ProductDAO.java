package com.spentsmonitor.dao;

import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Product;

public interface ProductDAO {
	List<Product> AllProducts();
	void insertProduct(Product p);
	void removeProduct(int id);
	void updateProduct(int id, Date d, Product p);
	Product selectProduct(int id);
	Product selectProductByName(String name);
	List<Product> searchProduct(String name);
	List<Product> searchProductByDate(Date init, Date end);
	double sumOfValues(Date init, Date end);
}
