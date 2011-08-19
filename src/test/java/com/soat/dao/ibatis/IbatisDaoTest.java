package com.soat.dao.ibatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;
import com.soat.beans.Author;
import com.soat.beans.Book;
import com.soat.beans.Param;
import com.soat.beans.User;
import com.soat.dao.ibatis.util.IbatisUtil;

public class IbatisDaoTest extends TestCase {

	private static final Logger logger = Logger.getLogger(IbatisDaoTest.class);
	private static final String NAMESPACE = "book";
	private SqlMapClient sqlMap = IbatisUtil.getSqlMapClient();
	
	private static final Map<String, Object> params = new HashMap<String, Object>();
	
	private static final Book book = new Book();
	private static final Param param = new Param();
	
	private static String ns(String query){
		return NAMESPACE+"."+query; 
	}
	
	static{
		params.put("name", "titi");		
		param.setName("donald");
		
		final Author author = new Author();
		author.setName("Zola");
		
		book.setIsbn("784-4564");
		book.setAuthor(author);
		book.setImageName("");
		book.setTitle("germinal");
		book.setShortDescription("un ouvrage de Zola");
		book.setLongDescription("un ouvrage de Zola, vraiment très long");
		
	}
	
//	public void test_init() throws Exception{
//		sqlMap.insert(ns("insertAuthor"), book.getAuthor());
//		sqlMap.insert(ns("insertBook"), book);
//		
//	}
	
	public void test_FunctionCall() throws Exception{
		String res = (String) sqlMap.queryForObject(ns("helloFunction"), params);
		logger.info(res);
		assertNotNull(res);
	}
	
	public void test_FunctionCall_ProcStyle() throws Exception{
		sqlMap.update(ns("helloFunctionProc"), param);
		String res = param.getMessage();
		logger.info(res);
		assertNotNull(res);
	}
	
	public void test_ProcedureCall() throws Exception{
		sqlMap.update(ns("helloProcedure"), param);
		String res = param.getMessage();
		logger.info(res);
		assertNotNull(res);
	}

	public void test_Insert() throws Exception{
		sqlMap.insert(ns("insertBook"), book);
		logger.info(book.getId());
	}

	public void test_Update() throws Exception{
		final Author author = new Author();
		author.setId(2);
		author.setName("un autre auteur");
		book.setAuthor(author);
		sqlMap.update(ns("updateBook"), book);
	}

	public void test_Delete() throws Exception{
		sqlMap.delete(ns("deleteBook"), book);
	}
	
	@SuppressWarnings("unchecked")
	public void test_GreedySelect() throws Exception{
		try{
		List<Book> books = sqlMap.queryForList(ns("selectBookAndCategoryGreedy"),null);
		for (Book book : books) {
			System.out.println(book);
		}	
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void test_LazySelect() throws Exception{
		try{
			List<Book> books = sqlMap.queryForList(ns("selectLazyBook"),null);		
			/* non lazy object property is accessed, nothing sould happen */
			System.out.println(books.get(0).getTitle());
			/* watch logs: lazy property is loaded  now */
			System.out.println(books.get(0).getCategories().get(0).getName());
		
			System.out.println(books.get(0).getAuthor().getName());
			
		}catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void test_multiInsert() throws Exception{
		List<User> users = new ArrayList<User>();
		users.add(new User("u1@mail.com","Thomas", "Thierry", "1234"));
		users.add(new User("u2@mail.com","Antoine", "Raoul", "5678"));
		
		try{
		sqlMap.startTransaction();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("users", users);
		sqlMap.insert("user."+"insertUserList",params);
		
		List<User> allUsers = sqlMap.queryForList("user."+"selectAllUsers", null);
		for (User user : allUsers) {
			System.out.println(user);
		}
//		sqlMap.commitTransaction();
		/* don't save to test again! */
		}finally{
			sqlMap.endTransaction();
		}		
	}
	
	@SuppressWarnings("unchecked")
	public void test_RowBounds() throws Exception{
		final int offset = 5, limit = 10;
		List<Book> books = sqlMap.queryForList(ns("selectAllBooks"), null, offset, limit);
		assertTrue(books.size() <= limit);
	}
	
	
	public void test_RowHandler() throws Exception{
		final RowHandler rowHandler = new RowHandler() {			
			@Override
			public void handleRow(Object valueObject) {
				Book book = (Book) valueObject;
				System.out.println(book.getTitle());
			}
		};
		sqlMap.queryWithRowHandler(ns("selectAllBooks"), null, rowHandler);				
	}
	
}