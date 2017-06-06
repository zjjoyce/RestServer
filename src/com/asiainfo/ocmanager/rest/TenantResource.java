package com.asiainfo.ocmanager.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.TenantMapper;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;


/**
 * 
 * @author zhaoyim
 *
 */


@Path("/tenant")
public class TenantResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String getTenants() {
		return "Hello World!";
	}
	
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getTenantById(@PathParam("id") int tenantId) {
		String res = "";
		SqlSession session = DBConnectorFactory.getSession();
		try {
			TenantMapper mapper = session.getMapper(TenantMapper.class);
			Tenant tenant = mapper.selectTenantById(tenantId);
//			System.out.println(tenant);
//			System.out.println(tenant.getId());
//			System.out.println(tenant.getName());
			res = tenant.getName();
			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}
		
		return res;
	}
	
}





