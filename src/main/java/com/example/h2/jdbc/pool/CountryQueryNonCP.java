/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.h2.jdbc.pool;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.model.Country;

public class CountryQueryNonCP implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CountryQueryNonCP.class);
	
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

	/**
	 * Start
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		try {
			for (String ctry_cd : new String[] { "IN", "US", "SG", "JP", "X1", "FR", "GB", "AE" }) {
				long startTime = new Date().getTime();
				/**
				 * Search Country
				 */
				search(ctry_cd);
				long endTime = new Date().getTime();
				/**
				 * Compute Time Taken
				 */
				long difference = endTime - startTime;
				logger.info("---Action completed in " + difference + " ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

	/**
	 * Search Country
	 * 
	 * @param hds
	 * @param ctry_cd
	 */
	private void search(String ctry_cd) {
		logger.info("\n===Reading Country by Ctry: " + ctry_cd);
		Connection conn = null;
		try {
			/**
			 * Get Connection from DataSource
			 */
			conn = getConnection(false);
			logger.info("Connection: " + conn);
			String sql = " SELECT * FROM country WHERE ctry_cd = ? ";
			/**
			 * Handler Bean for ResultSet
			 */
			BeanListHandler<Country> beanListHandler = new BeanListHandler<>(Country.class);
			/**
			 * Fetch Data
			 */
			List<Country> countries = new QueryRunner().query(conn, sql, beanListHandler, new Object[] { ctry_cd });
			for (Country country : countries) {
				logger.info(country);
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
		logger.info("Done - Reading Country by Ctry: " + ctry_cd);
	}

	/**
	 * Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CountryQueryNonCP().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}