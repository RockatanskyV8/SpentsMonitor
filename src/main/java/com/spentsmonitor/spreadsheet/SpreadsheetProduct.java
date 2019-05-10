package com.spentsmonitor.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;

import com.spentsmonitor.model.*;

public class SpreadsheetProduct {
	
	private int [] order;
	private String pageName;
	
	public SpreadsheetProduct(int[] order, String pageName) { 
		this.order = order;
		this.pageName = pageName;
	}
	
	XSSFWorkbook workbook = new XSSFWorkbook(); 
    
    XSSFDataFormat df = workbook.createDataFormat();
    XSSFCellStyle cs;
    
    XSSFRow row;
    Cell cell;
	
	public void organizeIncome(List<Income> In) throws ParseException {
		int rowid = 0; int cellid = 0;
		for(Income i : In) { extractInfo(i, rowid++, cellid); }
		fileWriter("Writesheet");
	}
	
	public void organizeProducts(List<Product> Ps) throws ParseException {
		int rowid = 0; int cellid = 0;
		for(Product p : Ps) { extractInfo(p, rowid++, cellid); }
		fileWriter("Writesheet");
	}
	
	public void organizeBills(List<Bill> Bs) throws ParseException {
		int rowid = 0; int cellid = 0;
		for(Bill b : Bs) { extractInfo(b, rowid++, cellid); }
		fileWriter("Writesheet");
	}
	
	private void extractInfo(Model p, int rowid, int cellid) {
		XSSFSheet spreadsheet = workbook.createSheet(pageName);
		row = spreadsheet.createRow(rowid);
		
		for(int k : order) {
			cell = row.createCell(cellid++);
			writeInfo(p, k);
		}
	}
	
	private void writeInfo(Model m, int k) {
		
		String dataType = m.toMap().get(k).getClass().getName();
		cs = workbook.createCellStyle();
		
		if(dataType == "java.lang.String") { cell.setCellValue((String) m.toMap().get(k)); } 
		else if(dataType == "java.lang.Double") { cs.setDataFormat(df.getFormat("#.##"));
												  cell.setCellValue((Double) m.toMap().get(k)); } 
		else if (dataType == "java.lang.Integer") { cell.setCellValue((Integer) m.toMap().get(k)); } 
		else if (dataType == "java.util.Date") { cs.setDataFormat(df.getFormat("dd/MM/yyyy"));
												 cell.setCellValue((Date) m.toMap().get(k) ); }
		
		cell.setCellStyle(cs);
	}
	
	private void fileWriter(String SheetName) {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File(SheetName + ".xlsx"));
		    workbook.write(out);
		    out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Writesheet.xlsx written successfully");
	}
	
}



