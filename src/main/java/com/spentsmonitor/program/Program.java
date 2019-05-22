package com.spentsmonitor.program;

import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.spentsmonitor.model.*;
import com.spentsmonitor.database.*;
import com.spentsmonitor.dao.*;
import com.spentsmonitor.spreadsheet.SpreadsheetProduct;

public class Program {

	public static SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	public static SpreadsheetProduct sp;// = new SpreadsheetProduct(new int[]{5,1,2,3,4}, "2019");
	
	public static void main(String[] args) throws ParseException  {
		DBConnector.TestConnection("teste.db");
		
		//testBD();
		
		sp = new SpreadsheetProduct(new int[]{5,1,2,3,4}, "2019");
		int rowid = 0; int cellid = 0;
		testIncomeDAO(rowid, cellid + 7);
		System.out.println();
		testProductDAO(rowid, cellid);
		System.out.println();
		testBillDAO();
		
	}
	
	public static void testIncomeDAO(int rowid, int cellid) throws ParseException {
		IncomeDAO dao = new IncomeDAOImp("teste.db");
		List<Income> Ps = dao.searchIncomeByDate(sdf.parse("01/01/2019"), sdf.parse("31/12/2019"));
		System.out.println("Income");
		sp.writeTitleCell("Income", rowid++, cellid);
		sp.writeHeads(new String[] {"Dia do Pagamento","Fonte","Valor","Tipo de Frequência","Quão frequente"}, rowid++, cellid);
		for(Income p : Ps) {
			sp.extractInfo(p.toMap(), rowid, cellid);
		}
		
	}
	
	public static void testProductDAO(int rowid, int cellid) throws ParseException   {
		ProductDAO dao = new ProductDAOImp("teste.db");
		List<Product> Ps = dao.searchProductByDate(sdf.parse("01/01/2019"), sdf.parse("31/12/2019"));
		System.out.println("Products");
		sp.writeTitleCell("Products", rowid++, cellid);
		sp.writeHeads(new String[] {"Dia","Nome","Quantidade","Valor","Venda"}, rowid++, cellid);
		for(Product p : Ps) {
			sp.extractInfo(p.toMap(), rowid, cellid);
		}
		
	}
	
	
	public static void testBillDAO() {
		BillDao dao = new BillDaoImp("teste.db");
		System.out.println("Bill");
		for (Bill b : dao.AllBills()) {
			System.out.println(b.toString());
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