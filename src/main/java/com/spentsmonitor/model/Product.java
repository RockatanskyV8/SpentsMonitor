package com.spentsmonitor.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Product{
	
	private String name;
	private Double price;
	private Date buyDate;
	private Integer quantity;
	private Double profit;
	
	public Product(String name, Double price, Date buyDate, Integer quantity, Double profit) {
		this.name = name;
		this.price = price;
		this.buyDate = buyDate;
		this.quantity = quantity;
		this.profit = profit;
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

	public Double getProfit() {
		return profit;
	}

	public void setProfit(Double profit) {
		this.profit = profit;
	}

	public double total() {
		return price * quantity;
	}
	
	public Map< Integer, Object > toMap() {
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, name);
		info.put(2, quantity);
		info.put(3, price);
		info.put(4, profit);
		info.put(5, buyDate);
		info.put(6, total());
		
		return info;
	}

	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Produto: " + name + 
			   ", Preço: " + price +
			   ", Data: " + sdf.format(buyDate) +
			   ", Quantidade: " + quantity +
			   ", Total: " + total() +
			   ", Venda: " + profit;
	}

}
