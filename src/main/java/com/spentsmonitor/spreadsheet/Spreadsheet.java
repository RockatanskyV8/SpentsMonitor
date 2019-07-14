package com.spentsmonitor.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;

import java.util.Date;

public class Spreadsheet {
	
	private int [] order;
	
	XSSFWorkbook workbook = new XSSFWorkbook();
	XSSFSheet spreadsheet;
    
    XSSFDataFormat df = workbook.createDataFormat();
    XSSFCellStyle cs = workbook.createCellStyle();
    
    Cell cell;
    
	public Spreadsheet(String pageName) { 
		//this.order = order;
		spreadsheet = workbook.createSheet(pageName);
	}
	
	public void defineOrder(int[] order) {
		this.order = order;
	}
	
	public int[] getOrder() {
		return order;
	}
	
	public void writeTitleCell(String title, int rowid, int cellid) {
		Row row = setRow(rowid);
    	cell = CellUtil.createCell(row, cellid, title);
    	spreadsheet.addMergedRegion(new CellRangeAddress(rowid, rowid, cellid, cellid + order.length -1));
    	CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
	}
	
   public void writeHeads(String [] headsName, int rowid, int cellid) {
	   Row row = setRow(rowid);
	   
	   for(String head : headsName) {
		   cell = CellUtil.createCell(row, cellid++, head);
		   CellUtil.setAlignment(cell, HorizontalAlignment.CENTER);
	   }
   }
	
   public void extractInfo(Map< Integer, Object > p, int rowid, int cellid) {
		Row row = setRow(rowid);
		
		for(int o : order) {
			cell = row.createCell(cellid);
			writeInfo(p.get(o));
			spreadsheet.autoSizeColumn(cellid++);
		}
		fileWriter("Writesheet");
	}
	
	public void extractInfo(Object o, int rowid, int cellid) {
		Row row = setRow(rowid);
		cell = row.createCell(cellid);
		writeInfo(o);
		fileWriter("Writesheet");
	}
	
	private Row setRow(int rowid) {
		Row row = spreadsheet.getRow(rowid);
		if(row == null) { row = spreadsheet.createRow(rowid);}
		return row;
	}
	
	public void writeInfo(Object o) {
		
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
	
	private void recizeAll(int cellid) {
	    //for (int i = cellid; i < spreadsheet.getRow(0).getPhysicalNumberOfCells(); i++) {
	    for (int i = cellid; i < order.length; i++) {
	    	//spreadsheet.autoSizeColumn(i);
	    	System.out.println(i);
	    }
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

		System.out.println(SheetName + ".xlsx written successfully");
	}
}