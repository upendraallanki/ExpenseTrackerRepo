package com.jsp.et.service;

import com.jsp.et.dto.UserTableDTO;

public interface UserTableService 
{
	int registration(UserTableDTO dto);
	
	//UserTableDTO login (String username, String password);
	UserTableDTO login(UserTableDTO userdto);

	UserTableDTO updateUserProfile(int userId, UserTableDTO userDTO);
	
}
