package com.asiainfo.ocmanager.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.ibatis.session.SqlSession;

import com.asiainfo.ocmanager.persistence.mapper.RoleMapper;
import com.asiainfo.ocmanager.persistence.model.Role;
import com.asiainfo.ocmanager.persistence.test.DBConnectorFactory;

/**
 * 
 * @author zhaoyim
 *
 */

@Path("/role")
public class RoleResource {

	/**
	 * Get All OCManager roles
	 * 
	 * @return role list
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Role> getRoles() {
		SqlSession session = DBConnectorFactory.getSession();
		List<Role> roles = new ArrayList<Role>();
		try {
			RoleMapper mapper = session.getMapper(RoleMapper.class);
			roles = mapper.selectAllRoles();

			session.commit();
		} catch (Exception e) {
			session.rollback();
		} finally {
			session.close();
		}

		return roles;
	}

}
