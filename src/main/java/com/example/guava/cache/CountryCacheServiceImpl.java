package com.example.guava.cache;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.model.Country;

public class CountryCacheServiceImpl extends GoogleCacheService implements EntityCacheService<Country>, AppConstants {

	private static final Logger logger = LogManager.getLogger(CountryCacheServiceImpl.class);

	private static Properties props = null;

	static {
		try {
			System.out.println("---Reading Property file: " + DB_H2_PROP_FILE);
			InputStream is = new FileInputStream(new File(DB_H2_PROP_FILE));
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Get Connection with Auto Commit
	 * 
	 * @param autoCommit
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 */
	public Connection getConnection(boolean autoCommit) throws SQLException, ClassNotFoundException {
		System.out.println("---Getting Connection...");
		Class.forName("org.h2.Driver"); 
		Connection conn = DriverManager.getConnection(props.getProperty("jdbc.url"),
				props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
		conn.setAutoCommit(autoCommit);
		System.out.println("---Got Connection: " + conn);
		return conn;
	}

	public CountryCacheServiceImpl() {
		super();
		/**
		 * Build Google Cache
		 */
		build(15, false);
		try {
			fillCache();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.guava.cache.EntityCacheService#fillCache()
	 */
	@Override
	public void fillCache() throws Exception {
		Connection conn = null;
		try {
			/**
			 * Get Connection from DataSource
			 */
			conn = getConnection(false);
			logger.info("Connection: " + conn);
			String sql = " SELECT * FROM country ";
			/**
			 * Handler Bean for ResultSet
			 */
			BeanListHandler<Country> beanListHandler = new BeanListHandler<>(Country.class);
			/**
			 * Fetch Data
			 */
			List<Country> countries = new QueryRunner().query(conn, sql, beanListHandler);
			for (Country country : countries) {
				/**
				 * Add to cache
				 */
				logger.debug("Adding into Cache: " + country);
				add(country.getCtry_cd(), country);
			}
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("Done - Read into Cache");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.guava.cache.EntityCacheService#find(java.lang.String)
	 */
	@Override
	public Country find(String input) throws Exception {
		if (getCache() == null || getCache().size() == 0) {
			fillCache();
		}
		return (Country) search(input);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.example.guava.cache.EntityCacheService#fillCacheAfterInsert(long)
	 */
	@Override
	public void fillCacheAfterInsert(long id) throws Exception {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.guava.cache.EntityCacheService#deletefromCache(long)
	 */
	@Override
	public void deletefromCache(long id) throws Exception {
		// TODO Auto-generated method stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.example.guava.cache.EntityCacheService#evict(java.lang.String)
	 */
	@Override
	public void evict(String key) throws Exception {
		// TODO Auto-generated method stub
	}
}
