package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.example.constants.AppConstants;
import com.example.dbservice.DbService;
import com.example.model.Customer;

/**
 * @project		JdbcExample - Add Customer
 * @author		User
 * @date		Nov 2, 2020
 */
public class CustomerAdd implements AppConstants {

	static String CLAZZ = CustomerAdd.class.getSimpleName();

	/**
	 * Add Customer
	 * 
	 * @param customer
	 * @return
	 */
	private boolean add(Customer customer) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Adding Customer...");

		try {
			/**
			 * Get Connection
			 */
			conn = DbService.getConnect(autoCommit);
			String sql = " INSERT INTO customer (ctry_cd, phone_no, phone_type, email_ad, customer_guid, last_mdfy_user, "
					+ " last_mdfy_prog, update_token, update_token_expiry_ts, init_insert_ts, last_mdfy_ts) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP) ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Set Values for INSERT
			 */
			pstmt.setString(1, customer.getCtry_cd());
			pstmt.setString(2, customer.getPhone_no());
			pstmt.setString(3, customer.getPhone_type());
			pstmt.setString(4, customer.getEmail_ad());
			pstmt.setString(5, customer.getCustomer_guid());

			pstmt.setString(6, customer.getLast_mdfy_user());
			pstmt.setString(7, customer.getLast_mdfy_prog());

			pstmt.setString(8, customer.getUpdate_token());

			int rowsUpdated = pstmt.executeUpdate();
			success = rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
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
		System.out.println("Done - Adding Customer success: " + success);
		System.out.println("=================================");

		return success;
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerAdd service = new CustomerAdd();
		Customer customer = new Customer();

		customer.setCtry_cd("IN");
		customer.setCustomer_guid(UUID.randomUUID().toString());
		customer.setEmail_ad("john@somewhere.com");
		customer.setPhone_no("123456789");
		customer.setPhone_type("MOBILE");

		customer.setUpdate_token(UUID.randomUUID().toString());

		customer.setLast_mdfy_prog(CLAZZ);
		customer.setLast_mdfy_user("SYSTEM");

		service.add(customer);
	}
}