package com.spentsmonitor.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import com.spentsmonitor.model.*;
import com.spentsmonitor.model.enums.BillType;
import com.spentsmonitor.database.*;
import com.spentsmonitor.dao.*;

public class Program {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) throws ParseException {
		testDAO();
		
		/*
		try {
			testBills();
			testProduct();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		*/
	}
	
	public static void testDAO() throws ParseException  {
		ProductDAO dao = new ProductDAOImp();
		//dao.AllProducts();
		//dao.removeProduct(1);
		//dao.insertProduct(new Product("produto 1", 2.50, sdf.parse("14/04/2017"), 4));
		//dao.AllProducts();
		dao.selectProduct(1);
		dao.updateProduct(1, new Product("produto 1.5", 2.50, sdf.parse("14/04/2017"), 4));
		dao.selectProduct(1);
	}
	
	public static void testBD() {
		
		DBCreator.CreateNewDatabase("teste.db");
		DBCreator.createTableBills("teste.db");
		DBCreator.createTableProducts("teste.db");
		DBCreator.createTableValues("teste.db");
		//DBConnector.connect("teste.db");
		
	}
	
	public static void testProduct() throws ParseException  {
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Product p1 = new Product("produto 1", 2.50, sdf.parse("14/04/2017"), 4);
		Product p2 = new Product("produto 2", 1.00, sdf.parse("14/04/2017"), 2);
		Product p3 = new Product("produto 3", 4.00, sdf.parse("14/04/2017"), 5);
		
		System.out.println(p1);
		System.out.println(p2);
		System.out.println(p3);
		
	}
	
	public static void testBills() throws ParseException {
		
		//SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		Bill b1 = new Bill("Conta 1", 25.00,BillType.valueOf("CONTA"), sdf.parse("14/04/2017"));
		Bill b2 = new Bill("Conta 2", 35.00,BillType.BOLETO, sdf.parse("13/01/2017"));
		Bill b3 = new Bill("Conta 3", 35.00,BillType.valueOf(3), sdf.parse("15/02/2017"));
		
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);
		
	}

}
