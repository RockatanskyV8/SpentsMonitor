package com.spentsmonitor.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.sql.Date;

import com.spentsmonitor.model.*;
import com.spentsmonitor.model.enums.BillType;
import com.spentsmonitor.database.*;
import com.spentsmonitor.dao.*;

public class Program {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) throws ParseException {
		//testBD();
		testProductDAO();
		testBillDAO();
	}
	
	public static void testBD() {
		
		DBCreator.CreateNewDatabase("teste.db");
		DBCreator.createTableBills("teste.db");
		DBCreator.createTableProducts("teste.db");
		DBCreator.createTableValues("teste.db");
		DBCreator.createTriggerCostProducts("teste.db");
		DBCreator.createTriggerCostBills("teste.db");
		DBConnector.connect("teste.db");
		
	}
	
	public static void testBillDAO() throws ParseException {
		BillDao dao = new BillDaoImp();
		
		for (Bill b : dao.AllBills()) {
			System.out.println(b.toString());
		}
	}
	
	public static void testProductDAO() throws ParseException  {
		ProductDAO dao = new ProductDAOImp();
		
		for(Product p : dao.AllProducts()) {
			System.out.println(p.toString());
		}
	}
	
	/*
	try {
		//testBills();
		testProduct();
	} catch (ParseException e) {
		e.printStackTrace();
	}
	*/
	
	public static void testProduct() throws ParseException  {
		Product p1 = new Product("produto 1", 2.50, sdf.parse("14/04/2017"), 4);
		Product p2 = new Product("produto 2", 1.00, sdf.parse("14/04/2017"), 2);
		Product p3 = new Product("produto 3", 4.00, sdf.parse("14/04/2017"), 5);
	}
	
	public static void testBills() throws ParseException {
		Bill b1 = new Bill("Conta 1", 25.00,1, sdf.parse("14/04/2017"));
		Bill b2 = new Bill("Conta 2", 35.00,2, sdf.parse("13/01/2017"));
		Bill b3 = new Bill("Conta 3", 35.00,3, sdf.parse("15/02/2017"));
	}

}
