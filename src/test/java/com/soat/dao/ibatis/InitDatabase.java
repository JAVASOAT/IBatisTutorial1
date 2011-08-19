package com.soat.dao.ibatis;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.soat.beans.Author;
import com.soat.beans.Book;
import com.soat.beans.Category;
import com.soat.dao.ibatis.util.IbatisUtil;

public class InitDatabase {

	private static final String NAMESPACE = "book";
	private static String ns(String query){
		return NAMESPACE+"."+query; 
	}
	
	public static void main(String[] args)throws Exception {
		
		SqlMapClient sqlMap = IbatisUtil.getSqlMapClient();
		sqlMap.startTransaction();
				
		Author zola = new Author();
		zola.setName("Emile Zola");
		sqlMap.insert(ns("insertAuthor"), zola);
		
		Author jkrowling = new Author();
		jkrowling.setName("J K Rowling");		
		sqlMap.insert(ns("insertAuthor"), jkrowling);
		
		Category roman = new Category();
		roman.setName("Roman");
		sqlMap.insert(ns("insertCategory"), roman);
		
		Category nouvelle = new Category();
		nouvelle.setName("Nouvelle");
		sqlMap.insert(ns("insertCategory"), nouvelle);
		
		Category aventure = new Category();
		aventure.setName("Nouvelle");
		sqlMap.insert(ns("insertCategory"), aventure);
		
		Book b;
		
		b=new Book();
		b.setTitle("Nana");
		b.setIsbn("547E45");
		b.setShortDescription("Un ouvrage de Zola");
		b.setLongDescription("Un très long ouvrage écrit par Zola");
		b.setAuthor(zola);
		b.addCategory(roman);
		sqlMap.insert(ns("insertBook"), b);
		sqlMap.insert(ns("insertBookCategories"), b);
		
		b=new Book();
		b.setTitle("Germinal");
		b.setIsbn("874F98");
		b.setShortDescription("Un autre ouvrage de Zola");
		b.setLongDescription("Une histoire toujorus aussi longue écrite par Zola");
		b.setAuthor(zola);
		b.addCategory(nouvelle);
		sqlMap.insert(ns("insertBook"), b);
		sqlMap.insert(ns("insertBookCategories"), b);
		
		b=new Book();
		b.setTitle("Harry Potter");
		b.setIsbn("8647SW54");
		b.setShortDescription("On ne présente plus Harry");
		b.setLongDescription("Les aventures de Harry le magicien, en 7 ou 8 tomes");
		b.setAuthor(jkrowling);
		b.addCategory(roman);
		b.addCategory(aventure);
		sqlMap.insert(ns("insertBook"), b);
		sqlMap.insert(ns("insertBookCategories"), b);
		
		sqlMap.commitTransaction();
		sqlMap.endTransaction();
	}
	
}
