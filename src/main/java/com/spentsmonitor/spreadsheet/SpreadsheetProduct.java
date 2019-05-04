package com.spentsmonitor.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;

import com.spentsmonitor.model.Product;
import com.spentsmonitor.dao.*;



public class SpreadsheetProduct {
	
	private Date [] interval = new Date [2];
	
	private SimpleDateFormat sdf(String format) { return new SimpleDateFormat(format); }
	
    XSSFWorkbook workbook = new XSSFWorkbook(); 
    XSSFSheet spreadsheet = workbook.createSheet("Info");
    XSSFRow row;
    Cell cell;
	
	public SpreadsheetProduct() {}
	
	public SpreadsheetProduct(Date begin, Date end) {
		interval[0] = begin;
		interval[1] = end;
	}
	
	public void organizeInfoYear() throws ParseException {
		ProductDAO dao = new ProductDAOImp("teste.db");
		List<Product> Ps = dao.searchProductByDate(interval[0], interval[1]);
		
		int rowid = 0;
		int cellid = 0;
		
		for(Product p : Ps){
			row = spreadsheet.createRow(rowid++);
			cell = row.createCell(cellid++);
			cell.setCellValue(p.getName());
			cell = row.createCell(cellid++);
			cell.setCellValue(sdf("dd/MM/yyyy").format(p.getBuyDate()));
			cell = row.createCell(cellid++);
			cell.setCellValue(p.getPrice());
			cell = row.createCell(cellid++);
			cell.setCellValue(p.getProfit());
			cell = row.createCell(cellid++);
			cell.setCellValue(p.getQuantity());
			cell = row.createCell(cellid++);
			cellid = 0;
		}
		
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("Writesheet.xlsx"));
		    workbook.write(out);
		    out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	    System.out.println("Writesheet.xlsx written successfully");
	}
}
