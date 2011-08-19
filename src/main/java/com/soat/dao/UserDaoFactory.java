package com.soat.dao;

import com.soat.dao.ibatis.UserDaoIbatis;


public class UserDaoFactory {

	private static final UserDao dao = new UserDaoIbatis();
	
	private UserDaoFactory(){};
	
	public static UserDao getInstance(){
		return dao;
	}
	
}
