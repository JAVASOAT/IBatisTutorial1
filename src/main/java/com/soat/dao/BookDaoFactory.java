package com.soat.dao;

import com.soat.dao.ibatis.BookDaoIbatis;


public class BookDaoFactory {

	private static final BookDao dao = new BookDaoIbatis();
	
	private BookDaoFactory(){};
	
	public static BookDao getInstance(){
		return dao;
	}
	
}
