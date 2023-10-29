package com.jsp.et.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.entity.ExpenseTable;
import com.jsp.et.entity.UserTable;



public interface ExpenseRepository extends JpaRepository<ExpenseTable, Integer>{

	//ExpenseTable save(ExpenseTable expenses);
	
	//to find list of expenses based on user details..
	List<ExpenseTable> findByUserTable(UserTable user);

	List<ExpenseCategory> findByAmount(double amount);
	
	
	

}
