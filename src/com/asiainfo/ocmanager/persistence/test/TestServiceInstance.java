package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceInstanceMapper;
import com.asiainfo.ocmanager.persistence.model.Role;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;

public class TestServiceInstance {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			ServiceInstanceMapper mapper = session.getMapper(ServiceInstanceMapper.class);
			mapper.insertServiceInstance(new ServiceInstance("9", "inst9", "2", "300", "ETCD", "hdfs tenant 005 quota"));
			mapper.deleteServiceInstance("2", "inst9");
			System.out.println("=== delete successfully ==");
			
			List<ServiceInstance> sis = mapper.selectServiceInstancesByTenant("2");

			for (ServiceInstance si : sis) {
				System.out.println(si.getId());
				System.out.println(si.getServiceTypeName());

			}
			
			
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
