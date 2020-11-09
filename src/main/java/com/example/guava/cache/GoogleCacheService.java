/**
 * @project		JdbcExample - Guava Cache Service
 * @author		User
 * @date		Nov 8, 2020
 */
package com.example.guava.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;

public abstract class GoogleCacheService {

	private Cache<String, Object> cache = null;

	/**
	 * @return the cache
	 */
	public Cache<String, Object> getCache() {
		return cache;
	}

	/**
	 * Build
	 */
	public void build(int expiration, boolean recordStats) {
		if (recordStats) {
			this.cache = CacheBuilder.newBuilder().expireAfterWrite(expiration, TimeUnit.MINUTES).build();
		} else {
			this.cache = CacheBuilder.newBuilder().expireAfterWrite(expiration, TimeUnit.MINUTES).build();
		}
	}

	/**
	 * Add
	 */
	public void add(String key, Object value) {
		this.cache.put(key, value);
	}

	/**
	 * Remove
	 * 
	 * @param key
	 */
	public void remove(String key) {
		this.cache.invalidate(key);
	}

	/**
	 * Search
	 * 
	 * @param key
	 * @return
	 */
	public Object search(String key) {
		return this.cache.getIfPresent(key);
	}

	/**
	 * Get Statistics of Cache
	 * 
	 * @return
	 */
	public CacheStats stats() {
		return cache.stats();
	}
}