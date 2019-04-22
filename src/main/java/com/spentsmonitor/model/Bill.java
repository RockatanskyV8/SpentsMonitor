package com.spentsmonitor.model;

import java.util.Date;

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
	
	@Override
	public String toString() {
		return "Conta: " + name + ", Valor: " + value + ", Tipo: " + billType.name() + ", Data do pagamento: " + paymentDate;
	}
	
}
