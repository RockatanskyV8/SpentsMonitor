package com.spentsmonitor.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;

import java.util.Date;

public class SpreadsheetProduct {
	
	private int [] order;
	
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet spreadsheet;
    
    XSSFDataFormat df = workbook.createDataFormat();
    XSSFCellStyle cs = workbook.createCellStyle();
    
    XSSFRow row;
    Cell cell;
    
	public SpreadsheetProduct(int[] order, String pageName) { 
		this.order = order;
		spreadsheet = workbook.createSheet(pageName);
	}
	
    public void writeTitleCell(String title, int rowFrom, int rowTo, int colFrom) {
    	row = spreadsheet.createRow(rowFrom);
    	cell = CellUtil.createCell(row, colFrom, title);
    	CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
    	spreadsheet.addMergedRegion(new CellRangeAddress(rowFrom,rowTo,colFrom, order.length - 1));
    }
    
    public void writeHeads(String [] headsName, int rowid) {
    	row = spreadsheet.createRow(rowid);
    	
    	for(int i = 0; i < headsName.length; i++) {
    		cell = CellUtil.createCell(row, i, headsName[i]);
    		CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
    	}
    }
    
	public void extractInfo(Map< Integer, Object > p, int rowid, int cellid) {
		
		row = spreadsheet.createRow(rowid);
		
		for(int k : order) {
			cell = row.createCell(cellid++);
			writeInfo(p.get(k));
		}
		fileWriter("Writesheet");
	}
	
	private void writeInfo(Object o) {
		
		String dataType = o.getClass().getName();
		XSSFCellStyle cs1 = workbook.createCellStyle();
		
		if(dataType == "java.lang.String") { cell.setCellValue((String) o); } 
		else if(dataType == "java.lang.Double") { cs1.setDataFormat(df.getFormat("#.##"));
												  cell.setCellValue((Double) o); } 
		else if (dataType == "java.lang.Integer") { cell.setCellValue((Integer) o); } 
		else if (dataType == "java.util.Date") { cs1.setDataFormat(df.getFormat("dd/MM/yyyy"));
												 cell.setCellValue((Date) o); }
		
		cell.setCellStyle(cs1);
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