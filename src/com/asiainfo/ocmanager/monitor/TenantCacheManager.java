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
 * Used to sych-up tenants info between local cache and Mysql. Sych-up local cache from Mysql if {@link #pull()} was called. And push updated cache into Mysql if {@link #commit()}
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
	 * Update tenants cache in batch. Updating would only be performed to existing tenants. And
	 * tenants whose name is in accordance with CITIC would be removed from cache, to reduce
	 * IO frequency when {@link #commit()} is called.
	 * @param id
	 * @param name
	 * @param level
	 */
	public synchronized void updateCache(List<AppEntity> tenants)
	{
		for(AppEntity en : tenants)
		{
			if (inCache(en)) {
				Tenant tenant = map.get(en.getId());
				if (!tenant.getName().equals(en.getAbbreviation())) {
					tenant.setName(en.getAbbreviation());// use abbreviation as tenant name.
					LOG.debug("Updated tenant: " + tenant);
				}
				else{
					map.remove(en.getId());// remove item from cache which has same tenant name with CITIC
				}
			}
		}
	}
	
	/**
	 * Exist in cache
	 * @param en
	 * @return
	 */
	private boolean inCache(AppEntity en) {
		return map.containsKey(en.getId());
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
