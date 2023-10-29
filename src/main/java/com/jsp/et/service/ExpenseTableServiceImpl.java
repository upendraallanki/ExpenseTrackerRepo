package com.jsp.et.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.et.dto.ExpensesTableDTO;
import com.jsp.et.entity.ExpenseCategory;
import com.jsp.et.entity.ExpenseTable;
import com.jsp.et.entity.UserTable;
import com.jsp.et.repository.ExpenseCategoryRepository;
import com.jsp.et.repository.ExpenseRepository;
import com.jsp.et.repository.UserTableRepository;
@Service
public class ExpenseTableServiceImpl implements ExpenseTableService {
	@Autowired
	private ExpenseRepository expenseRepository;
	@Autowired
	private ExpenseCategoryRepository categoryRepository;
	@Autowired
	private UserTableRepository userTableRepository;

	/*
	 * Expense Table contains two foreign keys - categoryId & userId To insert value
	 * in foreign keys columns ,firstly programmer have to verify user & category
	 * then need to retrieve it by using name and id After that insert into Expense
	 * table
	 */
	@Override
	public int addExpense(ExpensesTableDTO dto, int userId) {
		// Verification and retrieval of category by using its name...
		Optional<ExpenseCategory> category = categoryRepository.findByCategory(dto.getCategory());

		// Verification and retrieval of user by using its id...
		Optional<UserTable> user = userTableRepository.findById(userId);

		// if both are valid then insert record in table...
		if (category.isPresent() && user.isPresent()) {
			ExpenseTable expenses = new ExpenseTable();
			//convert String of Dto to Localdate Of Entity
			expenses.setDate(LocalDate.parse(dto.getDate()));
			BeanUtils.copyProperties(dto, expenses);
			expenses.setExpenseCategory(category.get());
			expenses.setUserTable(user.get());

			return expenseRepository.save(expenses).getExpenseid();
		}
		return 0;
	}

	/*
	 * To get all the expenses based on userId because every user have different
	 * expenses, Retrieve Expenses by using userId.
	 */
	@Override
	public List<ExpensesTableDTO> viewExpense(int userId) {
		// 1.Find user details in User table based on id then retrieve object..
		UserTable user = userTableRepository.findById(userId).get();

		// created created user-defined method in expenserepository to access expenses
		// by using..
		// user-details...

		/*
		 * findByUser returns list of expenses entity object,so to copy data from
		 * expenses entity list to expenseDTO list make use of stream api or foreach
		 * loop
		 */

		return expenseRepository.findByUserTable(user).stream().map(t -> {
			ExpensesTableDTO dto = new ExpensesTableDTO();
			BeanUtils.copyProperties(t, dto);

			// to store categoory from ExpensesCategory object to ExpenseDto
			/*
			 * t.getExpenseCategory() gives Object of ExpenseCategory entity class
			 */
			dto.setCategory(t.getExpenseCategory().getCategory());
			
			//convert localdate of entity class into String DTO
			dto.setDate(t.getDate().toString());
			return dto;
		}).collect(Collectors.toList());

	}

	

	/*
	 * To get expenses based on its id
	 */
	@Override
	public ExpensesTableDTO findByExpenseId(int id) 
	{

		Optional<ExpenseTable> expensedb = expenseRepository.findById(id);
		if (expensedb.isPresent()) 
		{
			ExpensesTableDTO dto = new ExpensesTableDTO();
			BeanUtils.copyProperties(expensedb.get(), dto);
			dto.setCategory(expensedb.get().getExpenseCategory().getCategory());
			//convert LocalDate to String
			dto.setDate(expensedb.get().getDate().toString());
			return dto;
		}
		return null;
	}
	
	

	/*
	 * To update expenses details
	 */
	@Override
	public ExpensesTableDTO updateExpense(ExpensesTableDTO dto, int expenseId) {
		//1.find expenses by using its id
		ExpenseTable expense = expenseRepository.findById(expenseId).get();
		//2.Verification
		if (expense != null) 
		{
			//3.updated data is retieved object of Expense
			expense.setAmount(dto.getAmount());
			expense.setDate(LocalDate.parse(dto.getDate()));
			expense.setDescription(dto.getDescription());

			//4.find category from  category table based on its name then update in expense
			ExpenseCategory category = categoryRepository.findByCategory(dto.getCategory()).get();
			expense.setExpenseCategory(category);

            //5.update  expenses by using save method
			ExpensesTableDTO updated = new ExpensesTableDTO();
			BeanUtils.copyProperties(expenseRepository.save(expense), updated);
			return updated;
		}
		return null;
	}

	/*
	 * To delete expense based on id
	 */
	@Override
	public int deleteExpense(int expenseId) {
        //1.find expenses based on id
		Optional<ExpenseTable> expenseDB = expenseRepository.findById(expenseId);
        //2.Verification
		if (expenseDB.isPresent()) {
        //3.deletion
			expenseRepository.delete(expenseDB.get());
			return 1;
		}
		return 0;
	}
	
	/*
	  * It will retrieve data from db based on matching category amount and date make
	  * use of filter method from stream api
	  */
	 @Override
	 public List<ExpensesTableDTO> filterExpenseBasedOnDateCategoryAmount(ExpensesTableDTO dto, int userId) {
	  /*
	   * call viewExpense method because its contains the logic ,to get all expenses
	   * of retrieve user,so programmer have to just filter expenses of user as per
	   * requirement
	   */

	  return viewExpense(userId).stream().filter(t -> t.getDate().equals(dto.getDate())
	    && t.getAmount() == dto.getAmount() && t.getCategory().equals(dto.getCategory())).toList();
	 }
	 
	 

	 /*
	  * It will retrieve data from db based on date make use of filter method from
	  * stream api
	  */
	 @Override
	 public List<ExpensesTableDTO> filterExpenseBasedOnDate(ExpensesTableDTO dto, int userId) {

	  return viewExpense(userId).stream().filter(t -> t.getDate().equals(dto.getDate())).toList();
	 }
	 
	 
	 
	 /*
	  * It will retrive data from db based on matching category make use of filter
	  * method from stream api
	  */
	 @Override
	 public List<ExpensesTableDTO> filterExpenseBasedOnCategory(ExpensesTableDTO dto,int userId)
	 {
		 return viewExpense(userId).stream().
				 filter(t -> t.getCategory().equals(dto.getCategory())).toList();
	 }
	 
	 
	 
	 /*
	  * It will retrieve data from db based on matching range of amount use of filter 
	  * method from stream api
	  * Take range in formate of string "100-200"
	  */
	 @Override
	 public List<ExpensesTableDTO> filterExpenseBasedOnAmount(String range,int userId)
	 {
		 String[] arr = range.split("-");
		 
		 return viewExpense(userId).stream().filter(t -> {
			int start = Integer.parseInt(arr[0]);
			int end = Integer.parseInt(arr[1]);
			return start <= t.getAmount()&& end >= t.getAmount();
		 }).collect(Collectors.toList());
		 
	 }

	
	/*@Override
	public double getTotalExpenseBasedOnTheDate(LocalDate start, LocalDate end, int userId) {
		
		return viewExpense(userId)
				.stream()
				.filter(t -> !t.getDate().isBefore(start) && !t.getDate().isAfter(end))
				.mapToDouble(t -> t.getAmount())
				.sum();
	}*/
	 
	
	 
	 
	 


}
