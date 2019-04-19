package com.spentsmonitor.dao;

import java.util.List;

import com.spentsmonitor.model.Product;

public interface ProductDAO {
	List<Product> AllProducts();
	void insertProduct(Product p);
	void removeProduct(int id);
	void updateProduct(int id, Product p);
	void selectProduct(int id);
}
