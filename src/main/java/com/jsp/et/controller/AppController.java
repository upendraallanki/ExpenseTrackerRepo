package com.jsp.et.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jsp.et.dto.ExpensesTableDTO;
import com.jsp.et.service.ExpenseTableService;
@Controller
@RequestMapping("/expense")
public class AppController {

	@RequestMapping("/index")
	public String index()
	{
		return "Index";
	}
	
	@RequestMapping("/home")
	public String home()
	{
		return "Home";
	}
	
	
	@RequestMapping("/register")
	public String register(@ModelAttribute("msg") String message)
	{
		return "Register";
	}
	
	
	@RequestMapping("/login")
	public String login(@ModelAttribute("msg") String message)
	{
		return "Login";
	}
	
	
	@RequestMapping("/viewExpense")
	public String viewExpense(@ModelAttribute("listOfExpense") List<ExpensesTableDTO> expenses)
	{
		return "ViewExpense";
	}
	
	@Autowired
	private ExpenseTableService service;
	@RequestMapping("/updateExpense/{id}")
	public String updateExpense(@PathVariable("id") int id, Model m)
	{
		ExpensesTableDTO dto = service.findByExpenseId(id);
		m.addAttribute("expense",dto);
		return "UpdateExpenses";
	}
	
	
	
	@RequestMapping("/filterExpense")
	public String filterExpense()
	{
		return "FilterExpenses";
	}
	
	@RequestMapping("/addExpense")
	public String addExpense(@ModelAttribute("error") String message)
	{
		return "AddExpenses";
	}
	
	@RequestMapping("/totalExpense")
	public String totalExpense()
	{
		return "TotalExpense";
	}
	@RequestMapping("/updateProfile")
	public String updateProfile()
	{
		return "UpdateProfile";
	}
	
}
