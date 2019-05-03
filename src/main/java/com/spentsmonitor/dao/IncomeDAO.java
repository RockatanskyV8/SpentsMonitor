package com.spentsmonitor.dao;

import java.util.List;

import com.spentsmonitor.model.Income;

public interface IncomeDAO {
	List<Income> AllIncomes();
	void insertIncome(Income i);
	void removeIncome(int id);
	void updateFrequentIncome(int id, Income i);
	Income selectIncomeBySource(String source);
}