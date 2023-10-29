package com.jsp.et.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExpensesTableDTO 
{
	private int expenseId;
	
	private String date;
	private double amount;
	private String description;
	//To Take range of an amount select by user
	private String range;
	//Creating EXpenseCategoryDTO referance variable may create difficulties in service layer hence go with String...
	private String category;
	
}
