package com.lms.model.persistence;

import com.lms.bean.User;

public interface UserDao {

	public User getUser(String username,String password);
	
	public int checkAdminCtrl(String username,String password);
	
	
}
