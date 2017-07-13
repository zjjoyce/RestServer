package com.asiainfo.ocmanager.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.asiainfo.ocmanager.monitor.entity.AppEntity;
import com.asiainfo.ocmanager.persistence.model.Tenant;
import com.asiainfo.ocmanager.rest.resource.utils.TenantPersistenceWrapper;

/**
 * Used to sych-up tenants info between local cache and Mysql. Sych-up local cache from Mysql if {@link #pull()} was called. And push cache into Mysql if {@link #commit()}
 * was called.
 * @author EthanWang
 *
 */
public class TenantCacheManager {
	private static final Logger LOG = Logger.getLogger(TenantCacheManager.class);
	
	private static TenantCacheManager instance;
	
	private Map<String, Tenant> map = new ConcurrentHashMap<>();
	
	public static TenantCacheManager getInstance()
	{
		if (instance == null) {
			synchronized(TenantCacheManager.class)
			{
				if (instance == null) {
					instance = new TenantCacheManager();
				}
			}
		}
		return instance;
	}

	private TenantCacheManager()
	{
		resetMap();
		LOG.info("Tenant cache init succeeded: " + new ArrayList<>(map.values()));
	}
	
	private void resetMap() {
		List<Tenant> tenants = TenantPersistenceWrapper.getAllTenants();
		map.clear();
		for(Tenant t : tenants)
		{
			map.put(t.getId(), t);
		}
	}

	/**
	 * Get all cached tenants.
	 * @return
	 */
	public List<Tenant> getAllTenants()
	{
		return new ArrayList<>(map.values());
	}
	
	/**
	 * batch update tenants cache, in which only existing tenants would be updated.
	 * @param id
	 * @param name
	 * @param level
	 */
	public synchronized void updateCache(List<AppEntity> tenants)
	{
		for(AppEntity en : tenants)
		{
			if (map.containsKey(en.getId())) {
				Tenant t = map.get(en.getId());
				t.setName(en.getAbbreviation());// use abbreviation as tenant name.
				LOG.debug("Updated tenant: " + t);
			}
		}
	}
	
	/**
	 * Sych-up local cache with Mysql.
	 */
	public synchronized void pull()
	{
		resetMap();
		LOG.info("Tenant cache reset succeeded: " + new ArrayList<>(map.values()));
	}
	
	/**
	 * Commit cached tenants to Mysql.
	 */
	public synchronized void commit()
	{
		for(Tenant t : map.values())
		{
			TenantPersistenceWrapper.updateTenantName(t.getId(), t.getName());
			LOG.info("Committed tenant to Mysql: " + t);
		}
	}
	
	
	/**
	 * Get tenant info my id.
	 * @param id
	 * @return
	 */
	public Tenant getTenant(String id)
	{
		return map.get(id);
	}
	
}
