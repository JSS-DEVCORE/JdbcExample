package com.example.sqlite.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.example.constants.AppConstants;
import com.example.dbservice.SimpleDbManager;
import com.example.model.Customer;

/**
 * @project JdbcExample - Query Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerQueryUsingDbUtils implements AppConstants {

	/**
	 * Query by Email Ad
	 * 
	 * @param emailAd
	 */
	private void queryOne(String emailAd) {
		Connection conn = null;
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Reading Customer(s) by emailAd: " + emailAd);
		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " SELECT customer_id, ctry_cd, customer_name, email_ad, phone_no, customer_guid "
					+ " FROM customer WHERE email_ad = ? ";
			/**
			 * Handler Bean for ResultSet
			 */
			BeanListHandler<Customer> beanListHandler = new BeanListHandler<>(Customer.class);
			/**
			 * Fetch Data
			 */
			List<Customer> customers = new QueryRunner().query(conn, sql, beanListHandler, new Object[] { emailAd });
			for (Customer customer : customers) {
				success = true;
				System.out.println(customer);
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
		if (!success) {
			System.err.println("Customer NOT FOUND for emailAd: " + emailAd);
		} else {
			System.out.println("Done - Reading Customer(s) by emailAd: " + emailAd);
		}
		System.out.println("=================================");
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerQueryUsingDbUtils service = new CustomerQueryUsingDbUtils();
		service.queryOne("john.hr@somewhere.com");
	}
}