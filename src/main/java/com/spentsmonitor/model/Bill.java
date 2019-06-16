package com.spentsmonitor.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import com.spentsmonitor.model.enums.BillType;

public class Bill {
	
	private String name;
	private Double value;
	private Date paymentDate;
	private BillType billType;
	
	public Bill(String name, Double value, int type, Date paymentDate) {
		this.name = name;
		this.value = value;
		this.billType = BillType.valueOf(type);
		this.paymentDate = paymentDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public BillType getBillType() {
		return billType;
	}

	public void setBillType(BillType billType) {
		this.billType = billType;
	}
	
	public Map< Integer, Object > toMap() {
		Map < Integer, Object > info = new TreeMap < Integer, Object >();
		
		info.put(1, name);
		info.put(2, value);
		info.put(3, billType.toString());
		info.put(4, paymentDate);
		
		return info;
	
	}
	
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		return "Conta: " + name + ", Valor: " + value + ", Tipo: " + billType.name() + ", Data do pagamento: " + sdf.format(paymentDate);
	}
	
}
