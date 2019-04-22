package com.spentsmonitor.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Product;

public interface ProductDAO {
	List<Product> AllProducts() throws ParseException;
	void insertProduct(Product p);
	void removeProduct(int id);
	void updateProduct(int id, Product p);
	Product selectProduct(int id) throws ParseException;
}
