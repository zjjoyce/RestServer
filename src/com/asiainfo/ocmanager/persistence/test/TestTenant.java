package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

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
		SqlSession session = TestDBConnectorFactory.getSession();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			
			System.out.println("=== Insert Tenant ===");
			mapper.insertTenant(new Tenant("id7", "name", "description", null));
			
			List<Tenant> tenants = mapper.selectAllTenants();

			System.out.println("=== All tenants ===");
			for (Tenant t : tenants) {
				System.out.println(t.getId());
				System.out.println(t.getName());
				System.out.println(t.getDescription());
				System.out.println(t.getParentId());
			}
			
			List<Tenant> children = mapper.selectChildrenTenants("2");
			
			System.out.println("=== Children tenants ===");
			for (Tenant c : children) {
				System.out.println(c.getId());
				System.out.println(c.getName());
				System.out.println(c.getDescription());
				System.out.println(c.getParentId());
			}
			
			Tenant tenant = mapper.selectTenantById("1");
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
