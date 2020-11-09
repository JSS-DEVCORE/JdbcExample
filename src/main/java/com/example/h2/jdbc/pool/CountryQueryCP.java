/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.h2.jdbc.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.dbservice.HikariConnPoolManager;
import com.example.model.Country;
import com.zaxxer.hikari.HikariDataSource;

public class CountryQueryCP implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CountryQueryCP.class);

	/**
	 * Start
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		HikariDataSource hds = HikariConnPoolManager.getDataSource();
		try {
			for (String ctry_cd : new String[] { "IN", "US", "SG", "JP", "X1", "FR", "GB", "AE" }) {
				long startTime = new Date().getTime();
				/**
				 * Search Country
				 */
				search(hds, ctry_cd);
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
			/**
			 * Shutdown DataSource
			 */
			hds.close();
		}
	}

	/**
	 * Search Country
	 * 
	 * @param hds
	 * @param ctry_cd
	 */
	private void search(HikariDataSource hds, String ctry_cd) {
		logger.info("\n===Reading Country by Ctry: " + ctry_cd);
		Connection conn = null;
		try {
			/**
			 * Get Connection from DataSource
			 */
			conn = hds.getConnection();
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
		} catch (SQLException e) {
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
			new CountryQueryCP().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}