package com.spentsmonitor.dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.spentsmonitor.model.Income;

public interface IncomeDAO {
	List<Income> AllIncomes() throws ParseException;
	void insertIncome(Income i) throws ParseException;
	void removeIncome(int id);
	void updateFrequentIncome(int id, Income i);
	Income selectIncomeBySource(String source) throws ParseException;
}
