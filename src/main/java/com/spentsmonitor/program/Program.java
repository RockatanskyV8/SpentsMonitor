package com.spentsmonitor.program;

import com.spentsmonitor.model.Product;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Product p1 = new Product("produto 1", 2.50, 4);
		Product p2 = new Product("produto 2", 1.00, 2);
		Product p3 = new Product("produto 3", 4.00, 5);
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
	}

}
