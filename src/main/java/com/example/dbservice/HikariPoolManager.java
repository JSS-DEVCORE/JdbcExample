/**
 * @project		JdbcExample - Hikari Connection Pool Manager
 * @author		User
 * @date		Nov 5, 2020
 */
package com.example.dbservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class HikariPoolManager {

	private static final Logger logger = LogManager.getLogger(HikariPoolManager.class);

	private static HikariConfig hcfg = new HikariConfig();
	private static HikariDataSource hds = null;
	
	static {
		logger.info("BEGIN - Creating Connection Pool...");
		try {
			InputStream is = new FileInputStream(new File("config/jdbc-sqlserver.properties"));
			Properties props = new Properties();
			props.load(is);

			hcfg.setPoolName(props.getProperty("jdbc.poolName"));

			hcfg.setJdbcUrl(props.getProperty("jdbc.jdbcUrl"));
			hcfg.setUsername(props.getProperty("jdbc.username"));
			hcfg.setPassword(props.getProperty("jdbc.password"));

			hcfg.setMaximumPoolSize(Integer.parseInt(props.getProperty("jdbc.maxPoolSize")));
			hcfg.setMinimumIdle(Integer.parseInt(props.getProperty("jdbc.initialSize")));
			hcfg.setAutoCommit(false);
			hcfg.setDriverClassName(props.getProperty("jdbc.driverClassName"));

			hcfg.addDataSourceProperty("cachePrepStmts", "true");
			hcfg.addDataSourceProperty("prepStmtCacheSize", "10");
			hcfg.addDataSourceProperty("prepStmtCacheSqlLimit", "128");

			hds = new HikariDataSource(hcfg);
			logger.info("Created DataSource: " + hds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			logger.info("END - Creating Connection Pool DataSource: " + hds);
		}
	}

	/**
	 * Get Data Source
	 * 
	 * @return
	 */
	public static HikariDataSource getDataSource() {
		return hds;
	}
}