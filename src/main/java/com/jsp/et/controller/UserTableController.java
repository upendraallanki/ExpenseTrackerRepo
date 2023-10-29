package com.jsp.et.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.et.dto.ExpensesTableDTO;
import com.jsp.et.dto.ImageDTO;
import com.jsp.et.dto.UserTableDTO;
import com.jsp.et.service.ExpenseTableService;
import com.jsp.et.service.UserTableService;

@Controller
public class UserTableController 
{
	@Autowired
	private UserTableService userTableService;
	
	@Autowired
	private ExpenseTableService expenseTableService;
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute UserTableDTO userTableDTO,RedirectAttributes attribute)
	{
		//return userService.registration(userTableDTO)
		int id = userTableService.registration(userTableDTO);
		if(id != 0)
		{
			//m.addAttribute("msg","Registration Successfull...!!");
			attribute.addFlashAttribute("msg" ,"Registration Successfull...!!");
			return "redirect:/expense/login";
		}
		//m.addAttribute("msg","Please Enter Valid Informatioon");
		attribute.addFlashAttribute("msg" ,"Please Enter Valid Informatioon");
		return "redirect:/expense/register";
	}
	
	
	
	@PostMapping("/loginOperation")
	public String login(@ModelAttribute UserTableDTO userTableDTO,RedirectAttributes attribute, HttpServletRequest request)
	{
		 UserTableDTO dto = userTableService.login(userTableDTO);
		 if(dto !=null)
		 {
			 request.getSession().setAttribute("user", dto);
			 if(dto.getImage() != null)
			 {
				 /*
				  * store image in session object but in the form of String
				  * By using Base64 class present in java.util package - programer can encode
				  * byte data to string
				  */
				 request.getSession().setAttribute("image",
						 Base64.getMimeEncoder().encodeToString(dto.getImage().getData()));
			 }
			 return "redirect:/expense/home";
		 }
		 attribute.addFlashAttribute("msg", "ENTER VALID DETAILS");
		 return "redirect:/expense/login";
		 
	}
	
	
	@PostMapping("/addexpense/{id}")
	public String addExpense(@ModelAttribute ExpensesTableDTO dto , @PathVariable("id") int userId, RedirectAttributes attributes)
	{
		int id = expenseTableService.addExpense(dto,userId);
		System.out.println(userId);
		if(id > 0)
		{
			//return "redirect:/viewexpense/"+userId;
			return "redirect:/viewexpense"+userId;
		}
		attributes.addFlashAttribute("error","PLEASE ENTER VALID DETAILS..");
		return "redirect:/expense/addExpense";
	}
	
	@GetMapping("/viewExpense/{id}")
	public String viewExpense(@PathVariable("id") int userId,RedirectAttributes attributes)
	{
		System.out.println(userId);
	  List<ExpensesTableDTO> expenses = expenseTableService.viewExpense(userId);
	  if(!expenses.isEmpty())
	  {
		attributes.addFlashAttribute("listOfExpense",expenses);
		return "redirect:/expense/viewExpense";
	  }
	  return "redirect:/expense/home";
	}
	 
	
	@GetMapping("/findId/{id}")
	public ResponseEntity<ExpensesTableDTO> findByExpenseId(@PathVariable("id") int id)
	{
	  ExpensesTableDTO dto = expenseTableService.findByExpenseId(id);
	  if(dto != null)
	  {
		  return ResponseEntity.status(HttpStatus.OK).body(dto);
		  
	  }
	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}
	 
	@PostMapping("/updateexpense/{eid}")
	public String updateExpense(@ModelAttribute ExpensesTableDTO dto, @PathVariable("eid") int expenseId, HttpServletRequest request)
	{
	  ExpensesTableDTO expense = expenseTableService.updateExpense(dto, expenseId);
	  if(expense != null)
	  {
		  UserTableDTO userTableDTO = (UserTableDTO)request.getSession().getAttribute("usee");
		  return "redirect:/viewexpense/"+userTableDTO.getUserId();
	  }
	  return "redirect:/expense/home";
	  
	}
	 
	@GetMapping("/deleteexpense/{eid}")
	public String deleteExpense(@PathVariable("eid") int expenseId,HttpServletRequest request)
	{
	  System.out.println(expenseId);
	  int id = expenseTableService.deleteExpense(expenseId);
	  if(id != 0)
	  {
		  UserTableDTO dto = (UserTableDTO)request.getSession().getAttribute("user");
		  return "redirect:/viewExpense/"+dto.getUserId();
		  
	  }
	  return "redirect:/expense/home";
	  
	}
	
	
	public String filterExpenseByCategoryDateAmount(@ModelAttribute ExpensesTableDTO dto,
			@PathVariable("userid") int userid, RedirectAttributes attributes)
	{
		if(!dto.getRange().equalsIgnoreCase("0"))
		{
			List<ExpensesTableDTO> filterByAmount = expenseTableService.filterExpenseBasedOnAmount(dto.getRange(),userid);
			attributes.addFlashAttribute("listOfExpense", filterByAmount);	
			return "redirect:/expense/viewExpense";
		}
		else if(dto.getCategory() != "")
		{
			List<ExpensesTableDTO> filterByCategory = expenseTableService.filterExpenseBasedOnCategory(dto,userid);
			attributes.addFlashAttribute("listOfExpense", filterByCategory);	
			return "redirect:/expense/viewExpense";
		}
		else if(dto.getDate() != "")
		{
			List<ExpensesTableDTO> filterByDate = expenseTableService.filterExpenseBasedOnDate(dto,userid);
			attributes.addFlashAttribute("listOfExpense", filterByDate);	
			return "redirect:/expense/viewExpense";
		}
		return "redirect:/expense/home";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*@GetMapping("/filter/expense1/{userid}")
	public ResponseEntity<List<ExpensesTableDTO>> filterExpenseBasedOnDateCategoryAmount(@RequestBody ExpensesTableDTO dto,@PathVariable("userid") int userId)
	{
	  List<ExpensesTableDTO> expenses =	expenseTableService.filterExpenseBasedOnDateCategoryAmount(dto, userId);
	  if(expenses != null)
	  {
		  return ResponseEntity.status(HttpStatus.OK).body(expenses);
		  
	  }
	  return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	
	}
	
	@GetMapping("/filter/expense4/{userid}")
	public ResponseEntity<List<ExpensesTableDTO>> filterExpenseBasedOnCategory(ExpensesTableDTO dto,int userId)
	{
		List<ExpensesTableDTO> expenses = expenseTableService.filterExpenseBasedOnCategory(dto, userId);
		if(expenses != null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(expenses);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		
	}
	
	/*@GetMapping("/getuser/{id}")
	public ResponseEntity<UserTableDTO> getUserById(@PathVariable("id") int userId)
	{
		UserTableDTO user = userTableService.findByUserId(userId);
		if(user != null)
		{
			return ResponseEntity.status(HttpStatus.OK).body(user);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
	}*/
	
	
	/*@DeleteMapping("/deleteuser/{id}")
	public ResponseEntity<Integer> deleteUser(@PathVariable("id") int id)
	{
		int status = userTableService.deleteUserProfile(id);
		if(status != 0)
		{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).body(status);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(0);
	}*/
	
	@PostMapping("/updateuser/{id}")
	public String updateUser(@PathVariable("id") int id, @ModelAttribute UserTableDTO dto, @RequestParam("imageFile") MultipartFile file,HttpServletRequest request)
	{
		try {
			/*
			 * retrive userdto object from session object,store at the time of Login
			 */
			UserTableDTO fromSession = (UserTableDTO)request.getSession().getAttribute("user");
			//if user already have upload profile photo then update the photo
			if(fromSession.getImage() != null)
			{
				//updation logic
				fromSession.getImage().setData(file.getBytes());
				dto.setImage(fromSession.getImage());
				
				//store same image in session object
				request.getSession().setAttribute("image",
						Base64.getMimeEncoder().encodeToString(dto.getImage().getData()));
			}
			else
			{
				//if user uploading profile photo first time
				ImageDTO imageDto = new ImageDTO();
				imageDto.setData(file.getBytes());
				dto.setImage(imageDto);
			}
			userTableService.updateUserProfile(id, dto);
			return "redirect:/expense/login";
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return "redirect:/expense/home";
	}
	
	
	
	
	
	
	
	
	
	
}
