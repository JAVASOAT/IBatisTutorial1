package com.soat.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.soat.dao.ibatis.util.IbatisUtil;

public class ClearDatabase {

	private static final String NAMESPACE = "book";
	private static String ns(String query){
		return NAMESPACE+"."+query; 
	}
	
	public static void main(String[] args)throws Exception {
		
		SqlMapClient sqlMap = IbatisUtil.getSqlMapClient();
		sqlMap.startTransaction();
		sqlMap.delete(ns("deleteBookCategories"), null);
		sqlMap.delete(ns("deleteCategory"), null);
		sqlMap.delete(ns("deleteBook"), null);
		sqlMap.delete(ns("deleteAuthor"), null);
		sqlMap.commitTransaction();
		sqlMap.endTransaction();
		
	}
	
}
