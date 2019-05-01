package com.spentsmonitor.program;

import java.text.SimpleDateFormat;

import com.spentsmonitor.model.*;
import com.spentsmonitor.database.*;
import com.spentsmonitor.dao.*;

public class Program {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public static void main(String[] args) {
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
	
	public static void testProductDAO() {
		ProductDAO dao = new ProductDAOImp("teste.db");
		System.out.println("Products");
		for(Product p : dao.AllProducts()) {
			System.out.println(p.toString());
		}
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
