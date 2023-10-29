package com.jsp.et.service;

import java.time.LocalDate;
import java.util.List;

import com.jsp.et.dto.ExpensesTableDTO;

public interface ExpenseTableService 
{
	int addExpense(ExpensesTableDTO dto,int userId);
	
	List<ExpensesTableDTO> viewExpense(int userId);
	
	ExpensesTableDTO findByExpenseId(int id);
	
	ExpensesTableDTO updateExpense(ExpensesTableDTO dto,int expenseId);
	
	int deleteExpense(int expenseId);
	
	List<ExpensesTableDTO> filterExpenseBasedOnDateCategoryAmount(ExpensesTableDTO dto, int userId);
	
	List<ExpensesTableDTO> filterExpenseBasedOnDate(ExpensesTableDTO dto, int userId);
	
	List<ExpensesTableDTO> filterExpenseBasedOnCategory(ExpensesTableDTO dto,int userId);
	
	List<ExpensesTableDTO> filterExpenseBasedOnAmount(String range,int userId);
	
	//double getTotalExpenseBasedOnTheDate(LocalDate start,LocalDate end,int userId);
	
	
}
