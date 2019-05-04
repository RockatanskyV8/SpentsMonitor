package com.spentsmonitor.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.spentsmonitor.model.*;
import com.spentsmonitor.database.*;
import com.spentsmonitor.dao.*;
import com.spentsmonitor.spreadsheet.SpreadsheetProduct;

public class Program {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) throws ParseException  {
		DBConnector.TestConnection("teste.db");
		
		//testBD();
		
		testIncomeDAO();
		System.out.println();
		testProductDAO();
		System.out.println();
		testBillDAO();
		
	}
	
	public static void testIncomeDAO() {
		IncomeDAO dao = new IncomeDAOImp("teste.db");
		System.out.println("Income");
		for(Income i : dao.AllIncomes()) {
			System.out.println(i.toString());
		}
	}
	
	public static void testBillDAO() {
		BillDao dao = new BillDaoImp("teste.db");
		System.out.println("Bill");
		for (Bill b : dao.AllBills()) {
			System.out.println(b.toString());
		}
	}
	
	public static void testProductDAO() throws ParseException   {
		ProductDAO dao = new ProductDAOImp("teste.db");
		System.out.println("Products");
		//(new SpreadsheetProduct(dao.searchProductByDate(sdf.parse("01/01/2019"), sdf.parse("02/05/2019")), "2019")).organizeInfo();
		(new SpreadsheetProduct(sdf.parse("01/01/2019"), sdf.parse("31/12/2019"))).organizeInfoYear();
	}
	
	public static void testBD() {
		
		DBCreator.CreateNewDatabase("teste.db");
		DBCreator.createTableBills("teste.db");
		DBCreator.createTableProducts("teste.db");
		DBCreator.createTableValues("teste.db");
		DBCreator.createTriggerCostProducts("teste.db");
		DBCreator.createTriggerCostBills("teste.db");
		DBCreator.createTableIncome("teste.db");
		DBCreator.createTableIncomes("teste.db");
		DBConnector.TestConnection("teste.db");
		
	}
}
