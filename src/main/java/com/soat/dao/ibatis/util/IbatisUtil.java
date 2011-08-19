package com.soat.dao.ibatis.util;

import java.io.Reader;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class IbatisUtil {

	private static final Logger logger = Logger.getLogger(IbatisUtil.class);
	private static final String resource = "ibatis-config.xml";
	private static SqlMapClient sqlMapClient;
	
	static{
		Reader reader = null;
		try{						
			reader = Resources.getResourceAsReader(resource);
			sqlMapClient = SqlMapClientBuilder.buildSqlMapClient(reader);
		} catch (Exception e) {
			logger.error(e);
		}finally{
			IOUtils.closeQuietly(reader);
		}
	}
	
	public static SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}
}