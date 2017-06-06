package com.asiainfo.ocmanager.persistence.test;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.Tenant;

/**
 * 
 * @author zhaoyim
 *
 */
public class TestTenant {

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
