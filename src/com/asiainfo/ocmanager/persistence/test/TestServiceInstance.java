package com.asiainfo.ocmanager.persistence.test;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceInstanceMapper;
import com.asiainfo.ocmanager.persistence.model.ServiceInstance;

public class TestServiceInstance {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			ServiceInstanceMapper mapper = session.getMapper(ServiceInstanceMapper.class);
			mapper.insertServiceInstance(new ServiceInstance("5", "inst5", "2", "300", "ETCD", "hdfs tenant 005 quota"));

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
