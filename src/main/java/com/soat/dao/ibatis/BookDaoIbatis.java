package com.soat.dao.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.soat.beans.Book;
import com.soat.dao.BookDao;
import com.soat.dao.ibatis.util.IbatisUtil;

public class BookDaoIbatis implements BookDao{

	private static final Logger logger = Logger.getLogger(BookDaoIbatis.class);
	
	private static final String NAMESPACE = "book";
	
	private SqlMapClient sqlMap = IbatisUtil.getSqlMapClient();
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getAllBooks() {
		List<Book> bookList = null;
		try{
			final String query = NAMESPACE+"."+"selectAllBooks"; 
			bookList = sqlMap.queryForList(query, null);			
		}catch(SQLException e){
			logger.error(e);
		}
		return bookList;
	}

	@Override
	public Book getBookById(String id) {
		Book book = null;
		try{
			final String query = NAMESPACE+"."+"selectBookById"; 
			Integer intId = Integer.parseInt(id);
			book = (Book) sqlMap.queryForObject(query, intId);			
		}catch(SQLException e){
			logger.error(e);
		}
		return book;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByCategory(String categoryId) {
		List<Book> bookList = null;
		try{
			final String query = NAMESPACE+"."+"selectBookByCategory";
			Integer intId = Integer.parseInt(categoryId);
			bookList = sqlMap.queryForList(query, intId);			
		}catch(SQLException e){
			logger.error(e);
		}
		return bookList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Book> getBooksByKeyWord(String word){
		List<Book> bookList = null;
		try{
			final String query = NAMESPACE+"."+"selectBookByKeyWord"; 
			bookList = sqlMap.queryForList(query, word);			
		}catch(SQLException e){
			logger.error(e);
		}
		return bookList;
	}

}
