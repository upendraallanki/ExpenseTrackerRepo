package com.jsp.et.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user_table")
public class UserTable 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userid;
	private String fullName;
	private String userName;
	
	@Column(unique = true)
	private String mobile;
	private String password;
	
	@Column(unique = true)
	private String email;
	
	//Bidirectional traversing
	@OneToMany(mappedBy = "userTable")
	private List<ExpenseTable> expensetable;
	
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id")
	private Image image;


	

	


	
	
}
