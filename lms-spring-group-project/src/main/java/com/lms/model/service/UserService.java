package com.lms.model.service;

import com.lms.bean.User;

public interface UserService {

	boolean checkUser(User user);
	int checkAdmin(User u);
}
