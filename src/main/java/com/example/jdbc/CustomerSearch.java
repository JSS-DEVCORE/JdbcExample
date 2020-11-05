/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;

public class CustomerSearch implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CustomerSearch.class);

	private DbService dbService = null;
	private Connection conn = null;

	/**
	 * Search Customer
	 * 
	 * @throws SQLException
	 */
	public void action() {
		try {
			/**
			 * Get DbService Instance
			 */
			dbService = new DbService(DB_PROP_FILE);
			/**
			 * Open Connection
			 */
			logger.info("Opening Connection to Database...");
			connect();
			/**
			 * Search Customer by EmailAd
			 */
			search("john@somewhere.com");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			/**
			 * Disconnect
			 */
			logger.info("Closing Connection from Database...");
			try {
				if (conn != null) {
					disconect();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Connect to Database
	 * 
	 * @throws SQLException
	 */
	private void connect() throws SQLException {
		conn = dbService.getConnect(true);
	}

	/**
	 * Close Connection
	 * 
	 * @throws SQLException
	 */
	private void disconect() throws SQLException {
		dbService.close(conn);
	}

	/**
	 * Search Customer
	 * 
	 * @param emailAd
	 */
	private void search(String emailAd) {
		logger.info("Reading Customer(s) by emailAd: " + emailAd);
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		Customer customer = null;
		boolean success = false;
		try {
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
		new CustomerSearch().action();
	}
}