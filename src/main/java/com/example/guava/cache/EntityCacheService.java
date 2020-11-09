/**
 * @project		JdbcExample - Entity Cache Service
 * @author		User
 * @date		Nov 8, 2020
 */
package com.example.guava.cache;

public interface EntityCacheService<T> {

	/**
	 * Fill Cache
	 * 
	 * @throws Exception
	 */
	void fillCache() throws Exception;

	/**
	 * Find Single Entity
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	T find(String input) throws Exception;

	/**
	 * Insert into ENTITY should add to cache
	 * 
	 * @param id
	 * @throws Exception
	 */
	void fillCacheAfterInsert(long id) throws Exception;

	/**
	 * Delete from ENTITY should remove from Cache
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deletefromCache(long id) throws Exception;

	/**
	 * Evict from Cache
	 * 
	 * @param key
	 * @throws Exception
	 */
	void evict(String key) throws Exception;
}
