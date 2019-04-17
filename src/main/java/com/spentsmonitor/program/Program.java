package com.spentsmonitor.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.spentsmonitor.model.*;
import com.spentsmonitor.model.enums.BillType;

public class Program {

	public static void main(String[] args) {
		
		testProduct();
		try {
			testBills();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void testProduct() {
		
		Product p1 = new Product("produto 1", 2.50, 4);
		Product p2 = new Product("produto 2", 1.00, 2);
		Product p3 = new Product("produto 3", 4.00, 5);
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		
	}
	
	public static void testBills() throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Bill b1 = new Bill("Conta 1", 25.00,BillType.valueOf("CONTA"), sdf.parse("14/04/2017"));
		Bill b2 = new Bill("Conta 2", 35.00,BillType.BOLETO, sdf.parse("13/01/2017"));
		Bill b3 = new Bill("Conta 3", 35.00,BillType.valueOf(3), sdf.parse("15/02/2017"));
		
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		
	}

}
