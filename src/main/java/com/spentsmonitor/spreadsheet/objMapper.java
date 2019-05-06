package com.spentsmonitor.spreadsheet;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TreeMap;

import com.spentsmonitor.model.*;

public class objMapper {
	
	private static SimpleDateFormat sdf(String format) { return new SimpleDateFormat(format); }
	
	public static Map< Integer, Object > optionMakerProduct(Product p) {
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, p.getName());
		info.put(2, p.getQuantity());
		info.put(3, p.getPrice());
		info.put(4, p.getProfit());
		info.put(5, sdf("dd/MM/yyyy").format(p.getBuyDate()));
		
		return info;
	
	}
	
	public static Map< Integer, Object > optionMakerBill(Bill b) {
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, b.getName());
		info.put(2, b.getValue());
		info.put(3, b.getBillType());
		info.put(4, sdf("dd/MM/yyyy").format(b.getPaymentDate()));
		
		return info;
	
	}
	
	public static Map< Integer, Object > optionMakerIncome(Income i){
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, i.getSource());
		info.put(2, i.getValue());
		info.put(3, i.getFrequencyType());
		info.put(4, i.getFrequencyNumber());
		info.put(5, sdf("dd/MM/yyyy").format(i.getIncomeDay()));
		
		return info;
	}
}
