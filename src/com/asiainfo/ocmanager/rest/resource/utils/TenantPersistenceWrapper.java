package com.asiainfo.ocmanager.rest.resource.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;

/**
 * 
 * @author zhaoyim
 *
 */
public class TenantPersistenceWrapper {

	/**
	 * 
	 * @param tenant
	 */
	public static void createTenant(Tenant tenant) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);

			mapper.insertTenant(tenant);

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static List<Tenant> getAllTenants() {
		SqlSession session = DBConnectorFactory.getSession();
		List<Tenant> tenants = new ArrayList<Tenant>();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);

			tenants = mapper.selectAllTenants();

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return tenants;
	}

	/**
	 * 
	 * @param tenantId
	 * @return
	 */
	public static Tenant getTenantById(String tenantId) {
		SqlSession session = DBConnectorFactory.getSession();
		Tenant tenant = null;
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			tenant = mapper.selectTenantById(tenantId);
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
		return tenant;
	}

	/**
	 * 
	 * @param parentTenantId
	 * @return
	 */
	public static List<Tenant> getChildrenTenants(String parentTenantId) {
		SqlSession session = DBConnectorFactory.getSession();
		List<Tenant> childrenTenants = new ArrayList<Tenant>();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			childrenTenants = mapper.selectChildrenTenants(parentTenantId);
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
		return childrenTenants;
	}

}
