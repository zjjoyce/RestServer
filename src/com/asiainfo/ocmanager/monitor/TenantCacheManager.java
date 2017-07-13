package com.asiainfo.ocmanager.monitor;

import java.util.ArrayList;
import java.util.HashMap;
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
		// mapping id to abbreviation
		Map<String, String> citic = transform(tenants);
		List<String> toDelete = new ArrayList<>();
		for(String key : map.keySet())
		{
			String citicName = citic.get(key);
			Tenant tenant = map.get(key);
			if (citicName != null && diffName(tenant, citicName)) {
				tenant.setName(citicName);// use abbreviation as tenant name.
				LOG.debug("Updated tenant in cache: " + map.get(key));
			}
			else{
				// remove from cache when:
				//1. tenant name is same as CITIC tenant name.
				//2. tenant not in CITIC tenants list.
				toDelete.add(key);
			}
		}
		deleteAction(toDelete);
	}
	
	private void deleteAction(List<String> toDelete) {
		for (String key : toDelete) {
			LOG.debug("Tenant no need be updated. Removing from cache: " + map.get(key));
			map.remove(key);
		}
	}

	private boolean diffName(Tenant tenant, String citicName) {
		return !tenant.getName().equals(citicName);
	}

	private Map<String, String> transform(List<AppEntity> tenants) {
		// transform to tenantID, tenantName mapping
		Map<String, String> map = new HashMap<>();
		for (AppEntity t : tenants) {
			map.put(t.getId(), t.getAbbreviation());// use abbreviation as tenant name.
		}
		return map;
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
		LOG.info("Tenants going to be committed into Mysql: " + map.values());
		for(Tenant t : map.values())
		{
			TenantPersistenceWrapper.updateTenantName(t.getId(), t.getName());
			LOG.info("Tenant committed to Mysql: " + t);
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
