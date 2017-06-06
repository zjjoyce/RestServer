package com.asiainfo.ocmanager.persistence.test;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * 
 * @author zhaoyim
 *
 */

public class DBConnectorFactory {

	public static SqlSessionFactory sessionFactory;
	
	static{

		try {
			String resource = "com/asiainfo/ocmanager/persistence/configuration.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static SqlSession getSession(){
		return sessionFactory.openSession();
	}
	

}
