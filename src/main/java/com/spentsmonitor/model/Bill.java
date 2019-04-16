package com.spentsmonitor.model;

import java.util.Date;

public class Bill {
	private String name;
	private Double value;
	private Date paymentDate;
	
	public Bill(String name, Double value, Date paymentDate) {
		this.name = name;
		this.value = value;
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

	@Override
	public String toString() {
		return "Conta: " + name + ", Valor: " + value + ", Data do pagamento: " + paymentDate;
	}
	
}
