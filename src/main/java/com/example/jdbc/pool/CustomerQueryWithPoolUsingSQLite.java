/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.jdbc.pool;

import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.dbservice.HikariPoolSQLiteManager;
import com.zaxxer.hikari.HikariDataSource;

public class CustomerQueryWithPoolUsingSQLite implements AppConstants {

	static final Logger logger = LogManager.getLogger(CustomerQueryWithPoolUsingSQLite.class);

	/**
	 * Start
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		HikariDataSource hds = HikariPoolSQLiteManager.getDataSource();
		try {
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
	 * Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CustomerQueryWithPoolUsingSQLite().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}