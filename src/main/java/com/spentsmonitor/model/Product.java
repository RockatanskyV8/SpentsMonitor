package com.spentsmonitor.model;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
	
	private String name;
	private Double price;
	private Date buyDate;
	private Integer quantity;
	
	public Product(String name, Double price, Date buyDate,Integer quantity) {
		this.name = name;
		this.price = price;
		this.buyDate = buyDate;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	
	public Date getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Date buyDate) {
		this.buyDate = buyDate;
	}

	public double total() {
		return price * quantity;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Produto: " + name + ", Preço: " + price +  ", Data: " + sdf.format(buyDate) + ", Quantidade: " + quantity + ", Total: " + total();
	}

}
