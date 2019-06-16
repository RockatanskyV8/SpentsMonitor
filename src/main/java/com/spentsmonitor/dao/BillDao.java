package com.spentsmonitor.dao;

import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Bill;

public interface BillDao {
	List<Bill> AllBills();
	void insertBill(Bill b);
	void removeBill(int id);
	void updateBill(int id, Date d, Bill b);
	Bill selectBill(int id);
	Bill selectBillByName(String name);
	List<Bill> searchBillByDate(Date init, Date end);
}
