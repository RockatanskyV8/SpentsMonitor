package com.spentsmonitor.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.spentsmonitor.model.enums.FrequencyType;

public class Income implements Model{
	private Double value;
	private String source;
	private Date incomeDay;
	private FrequencyType frequencyType;
	private Integer frequencyNumber;
	
	public Income(Double value, String source, Date incomeDay, Integer frequencyType, Integer frequencyNumber) {
		this.value = value;
		this.source = source;
		this.incomeDay = incomeDay;
		this.frequencyType = FrequencyType.valueOf(frequencyType);
		this.frequencyNumber = frequencyNumber;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Date getIncomeDay() {
		return incomeDay;
	}

	public void setIncomeDay(Date incomeDay) {
		this.incomeDay = incomeDay;
	}
	
	public FrequencyType getFrequencyType() {
		return frequencyType;
	}

	public void setFrequencyType(FrequencyType frequencyType) {
		this.frequencyType = frequencyType;
	}

	public Integer getFrequencyNumber() {
		return frequencyNumber;
	}

	public void setFrequencyNumber(Integer frequencyNumber) {
		this.frequencyNumber = frequencyNumber;
	}
	
	@Override
	public Map< Integer, Object > toMap(){
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, source);
		info.put(2, value);
		info.put(3, frequencyType);
		info.put(4, frequencyNumber);
		info.put(5, incomeDay);
		
		return info;
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return  "Value: " 			+ value + 
				", source: " 		+ source + 
				", incomeDay: " 	+ sdf.format(incomeDay) +
				", frequencyType: " + frequencyType.name() + 
				", frequencyNumber: " + frequencyNumber;
	}
		
}
