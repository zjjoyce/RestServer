package com.asiainfo.ocmanager.persistence.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.DashboardMapper;
import com.asiainfo.ocmanager.persistence.model.Dashboard;

public class TestDashboard {

	public static void main(String[] args) {
		SqlSession session = TestDBConnectorFactory.getSession();
		try {

			DashboardMapper mapper = session.getMapper(DashboardMapper.class);

			System.out.println("==== insert");
			mapper.insertLink(new Dashboard("name4", "description", "imageUrll", "href", true));
			session.commit();
			
			System.out.println("==== select by name");
			Dashboard dashboard = mapper.selectLinkByName("name4");
			System.out.println(dashboard.getId());
			System.out.println(dashboard.getName());
			System.out.println(dashboard.getDescription());
			System.out.println(dashboard.getHref());
			System.out.println(dashboard.getImageUrl());
			System.out.println(dashboard.isBlank());
			
			System.out.println("==== update");
			mapper.updateLink(new Dashboard(12, "name1", "description1", "imageUrll1", "href1", false));
			session.commit();
			
			System.out.println("==== select all");
			List<Dashboard> list = mapper.selectAllLinks();
			for(Dashboard d : list){
				System.out.println(d.getName());
				System.out.println(d.getDescription());
				System.out.println(d.getHref());
				System.out.println(d.getImageUrl());
				System.out.println(d.isBlank());
			}
			session.commit();

			System.out.println("==== delete");
			mapper.deleteLink(12);
			session.commit();

		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

	}

}
