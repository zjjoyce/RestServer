package com.asiainfo.ocmanager.rest.resource.utils;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceInstanceMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;

/**
 * 
 * @author zhaoyim
 *
 */
public class ServiceInstancePersistenceWrapper {
	/**
	 * 
	 * @param tenant
	 */
	public static void createServiceInstance(ServiceInstance serviceInstance) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			ServiceInstanceMapper mapper = session.getMapper(ServiceInstanceMapper.class);

			mapper.insertServiceInstance(serviceInstance);

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
	}
}
