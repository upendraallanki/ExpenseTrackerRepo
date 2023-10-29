package com.jsp.et.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ManyToAny;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class ExpenseTable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int expenseid;
	private LocalDate date;
	private double amount;
	private String description;
	
	@ManyToOne //many records from expense table to one record from user table..
	@JoinColumn(name = "user_fk")
	private UserTable userTable;
	
	@ManyToOne
	@JoinColumn(name = "category_fk")
	private ExpenseCategory expenseCategory;
	
	
	
	
	
}
