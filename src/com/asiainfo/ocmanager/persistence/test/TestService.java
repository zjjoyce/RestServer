package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.ServiceMapper;
import com.asiainfo.ocmanager.persistence.model.Service;

public class TestService {

	public static void main(String[] args) {
		SqlSession session = DBConnectorFactory.getSession();
		try {
			ServiceMapper mapper = session.getMapper(ServiceMapper.class);
			mapper.insertService(new Service("100", "hdfs1", "hdfs description"));
			session.commit();
			List<Service> services = mapper.selectAllServices();
			System.out.println("=== All services ===");
			for (Service s : services) {
				System.out.println(s.getId());
				System.out.println(s.getServicename());
				System.out.println(s.getDescription());
			}
			session.commit();
			System.out.println("=== Service by id ===");
			Service service = mapper.selectServiceById("100");
			System.out.println(service.getId());
			System.out.println(service.getServicename());
			System.out.println(service.getDescription());

			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
