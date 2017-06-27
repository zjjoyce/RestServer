package com.asiainfo.ocmanager.persistence.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.asiainfo.ocmanager.persistence.model.Dashboard;

/**
 * 
 * @author zhaoyim
 *
 */
public interface DashboardMapper {

	public List<Dashboard> selectAllLinks();
	
	public Dashboard selectLinkByName(@Param("name") String name);

	public void insertLink(Dashboard dashboard);

	public void updateLink(Dashboard dashboard);

	public void deleteLink(@Param("id") int id);
}
