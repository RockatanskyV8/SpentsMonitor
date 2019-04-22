package com.spentsmonitor.dao;

import java.text.ParseException;
import java.util.List;

import com.spentsmonitor.model.Bill;

public interface BillDao {
	List<Bill> AllBills() throws ParseException;
	void insertBill(Bill b);
	void removeBill(int id);
	void updateBill(int id, Bill b);
	void selectBill(int id);
}
