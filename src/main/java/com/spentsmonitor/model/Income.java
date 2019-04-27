package com.spentsmonitor.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.spentsmonitor.model.enums.FrequencyType;

public class Income {
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
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return  "Value: " 			+ value + 
				", source: " 		+ source + 
				", incomeDay: " 	+ sdf.format(incomeDay) +
				", frequencyType: " + frequencyType.name() + 
				", frequencyNumber: " + frequencyNumber;
	}
		
}
