package com.asiainfo.ocmanager.persistence;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.Tenant;


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
			e.printStackTrace();
		}
		
	}
	
	public static SqlSession getSession(){
		return sessionFactory.openSession();
	}
	
	
	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			Tenant tenant = mapper.selectTenantById(1);
			System.out.println(tenant);
			System.out.println(tenant.getId());
			System.out.println(tenant.getName());
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}
}
