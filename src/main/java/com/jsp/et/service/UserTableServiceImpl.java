package com.jsp.et.service;



import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jsp.et.dto.ImageDTO;
import com.jsp.et.dto.UserTableDTO;
import com.jsp.et.entity.Image;
import com.jsp.et.entity.UserTable;
import com.jsp.et.repository.UserTableRepository;


@Service
public class UserTableServiceImpl implements UserTableService 
{
	
	
	@Autowired
	private UserTableRepository userTableRepository;
	
	
	@Override
	public int registration(UserTableDTO dto) 
	{
		//Create Object of an entity class...
	    UserTable userdb = new UserTable();
	    
	    //transfer data from dto to entity object...
	    /*
	     * Basic Way 
	     * userdb.setUserName(dto.getUserName);
	     * userdb.setFullName(dto.getFullName);
	     */
	    if(dto.getPassword().equals(dto.getRepassword()))
	    {
			BeanUtils.copyProperties(dto, userdb);
			//UserTableRepository.save(userdb);
			return userTableRepository.save(userdb).getUserid();
	    }
		return 0;
		
	}
	
	
	@Override
	public UserTableDTO login(UserTableDTO userdto) {
		Optional<UserTable> findByUserNameAndPassword = userTableRepository.findByUserNameAndPassword(userdto.getUserName(),userdto.getPassword());
		UserTableDTO verifiedUser = new UserTableDTO();
		if(findByUserNameAndPassword.isPresent())
		{
			UserTable userdb = findByUserNameAndPassword.get();
			BeanUtils.copyProperties(userdb, verifiedUser);
			
			//to Store imagedto object into userdto
			if(userdb.getImage() != null)
			{
				ImageDTO imageDTO = new ImageDTO();
				BeanUtils.copyProperties(userdb.getImage(), imageDTO);
				verifiedUser.setImage(imageDTO);
			}
			return verifiedUser;
		}
		return null;
	}
	
	
	@Override
	public UserTableDTO updateUserProfile(int userId, UserTableDTO userDTO)
	{
		UserTable user = userTableRepository.findById(userId).get();
		user.setEmail(userDTO.getEmail());
		user.setFullName(userDTO.getFullName());
		user.setMobile(userDTO.getMobile());
		user.setPassword(userDTO.getPassword());
		user.setUserName(userDTO.getUserName());
		
		//to add image object into user object
		if(userDTO.getImage().getData().length != 0)
		{
			//System.out.println(userDTO.getImage().getData().length);
			Image image = new Image();
			BeanUtils.copyProperties(userDTO.getImage(), image);
			user.setImage(image);
			
		}
		UserTableDTO updated = new UserTableDTO();
		BeanUtils.copyProperties(userTableRepository.save(user), updated);
		return updated;
	}

	
	
	
	
}
