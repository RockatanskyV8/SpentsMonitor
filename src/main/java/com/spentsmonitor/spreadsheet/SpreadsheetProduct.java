package com.spentsmonitor.spreadsheet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataFormat;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Date;

import com.spentsmonitor.model.Product;
import com.spentsmonitor.dao.*;

public class SpreadsheetProduct {
	
	private Date [] interval = new Date [2];
	
    XSSFWorkbook workbook = new XSSFWorkbook(); 
    XSSFSheet spreadsheet = workbook.createSheet("Info");
    
    XSSFDataFormat df = workbook.createDataFormat();
    XSSFCellStyle cs;
    
    XSSFRow row;
    Cell cell;
	
	public SpreadsheetProduct(Date begin, Date end) {
		interval[0] = begin;
		interval[1] = end;
	}
	
	public void organizeInfo(int[] order) throws ParseException {
		ProductDAO dao = new ProductDAOImp("teste.db");
		List<Product> Ps = dao.searchProductByDate(interval[0], interval[1]);
		
		int rowid = 0;
		int cellid = 0;
		
		for(Product p : Ps){
			
			row = spreadsheet.createRow(rowid++);
			
			for(int k : order) {
				cell = row.createCell(cellid++);
				String dataType = p.toMap().get(k).getClass().getName();
				cs = workbook.createCellStyle();
				
				if(dataType == "java.lang.String") { cell.setCellValue((String) p.toMap().get(k)); } 
				else if(dataType == "java.lang.Double") { cs.setDataFormat(df.getFormat("#.##"));
														  cell.setCellValue((Double) p.toMap().get(k)); } 
				else if (dataType == "java.lang.Integer") { cell.setCellValue((Integer) p.toMap().get(k)); } 
				else if (dataType == "java.util.Date") { cs.setDataFormat(df.getFormat("dd/MM/yyyy"));
														 cell.setCellValue((Date) p.toMap().get(k) ); }
				
				cell.setCellStyle(cs);
				
			}
			cellid = 0;
		}
		
		fileWriter();
		
	}
	
	private void fileWriter() {
		FileOutputStream out;
		try {
			out = new FileOutputStream(new File("Writesheet.xlsx"));
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



