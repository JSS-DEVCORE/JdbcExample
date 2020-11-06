/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.jdbc.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.dbservice.HikariPoolManager;
import com.example.model.Customer;
import com.zaxxer.hikari.HikariDataSource;

public class CustomerSearchWithPool implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CustomerSearchWithPool.class);

	/**
	 * Multiple Search
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		HikariDataSource hds = HikariPoolManager.getDataSource();
		try {
			for (String emailAd : new String[] { "john@somewhere.com", "test@gmail.com", "dev@newfound-systems.com" }) {
				long startTime = new Date().getTime();
				/**
				 * Search Customer
				 */
				search(hds, emailAd);
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
	 * Search Customer
	 * 
	 * @param ds
	 * 
	 * @param emailAd
	 */
	private void search(HikariDataSource hds, String emailAd) {
		logger.info("\n===Reading Customer(s) by emailAd: " + emailAd);

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Customer customer = null;
		boolean success = false;
		Connection conn = null;
		try {
			/**
			 * Get Connection from DataSource
			 */
			conn = hds.getConnection();
			logger.info("Connection: " + conn);
			String sql = " SELECT customer_id, ctry_cd, email_ad, phone_no, customer_guid FROM customer WHERE email_ad = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, emailAd);
			/**
			 * Get Result Set
			 */
			rs = pstmt.executeQuery();
			while (rs.next()) {
				customer = new Customer();
				success = true;

				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCtry_cd(rs.getString("ctry_cd"));
				customer.setEmail_ad(rs.getString("email_ad"));
				/**
				 * You can also use Query Column Number - Not Recommended though
				 */
				customer.setCustomer_guid(rs.getString(5));
				logger.info(customer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (!success) {
			logger.warn("Customer NOT FOUND for emailAd: " + emailAd);
		} else {
			logger.info("Done - Reading Customer(s) by emailAd: " + emailAd);
		}
	}

	/**
	 * Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CustomerSearchWithPool().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}