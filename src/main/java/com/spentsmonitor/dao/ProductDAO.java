package com.spentsmonitor.dao;

import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Product;

public interface ProductDAO {
	List<Product> AllProducts();
	void insertProduct(Product p);
	void insertCost(Product p);
	int getProductID(String name);
	void removeProduct(int id);
	void updateProduct(int id, Product p);
	void selectProduct(int id);
}
