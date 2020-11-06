package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.example.constants.AppConstants;
import com.example.dbservice.DbService;
import com.example.model.Customer;

/**
 * @project JdbcExample - Customer Transaction
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerAddWithTxn implements AppConstants {

	static String CLAZZ = CustomerAddWithTxn.class.getSimpleName();

	/**
	 * Add Customer
	 * 
	 * @param conn
	 * @param customer
	 * @return
	 */
	private boolean add(Connection conn, Customer customer) {
		PreparedStatement pstmt = null;

		boolean success = false;
		System.out.println("Adding Customer in Txn...");

		try {
			/**
			 * Get Connection
			 */
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done - Adding Customer success: " + success);
		System.out.println("=================================");

		return success;
	}

	/**
	 * Update Phone Number
	 * 
	 * @param conn
	 * @param emailAd
	 * @param phoneNo
	 * @return
	 */
	private boolean update(Connection conn, String emailAd, String phoneNo) {
		PreparedStatement pstmt = null;
		boolean success = false;

		System.out.println("Updating Customer PhoneNo by EmailAd: " + emailAd);
		try {
			String sql = " UPDATE customer SET phone_no = ?, last_mdfy_ts = CURRENT_TIMESTAMP WHERE email_ad = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Set Value for Delete
			 */
			pstmt.setString(1, phoneNo);
			pstmt.setString(2, emailAd);

			int rowsUpdated = pstmt.executeUpdate();
			success = rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done - Updating Customer PhoneNo by EmailAd: " + emailAd + " success: " + success);
		System.out.println("=================================");

		return success;
	}

	/**
	 * Do Transaction of Add and Delete
	 */
	private void doTxn() {
		Connection conn = null;
		boolean success = false;
		boolean autoCommit = false;
		try {
			conn = DbService.getConnect(autoCommit);
			/**
			 * INSERT Customer in a Transaction
			 */
			Customer customer = new Customer();

			customer.setCtry_cd("IN");
			customer.setCustomer_guid(UUID.randomUUID().toString());
			customer.setEmail_ad("test@somewhere.com");
			customer.setPhone_no("0987654321");
			customer.setPhone_type("MOBILE");

			customer.setUpdate_token(UUID.randomUUID().toString());

			customer.setLast_mdfy_prog(CLAZZ);
			customer.setLast_mdfy_user("SYSTEM");

			success = add(conn, customer);
			/**
			 * Verify if Above Execution is Success before Proceed to DELETE
			 */
			if (success) {
				/**
				 * Update
				 */
				success = update(conn, "test@somewhere.com", "1-800-CALL-HELP");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					if (success) {
						System.out.println("Commit Transaction...");
						conn.commit();
					} else {
						System.out.println("Rollback Transaction...");
						conn.rollback();
					}
					if (conn != null) {
						conn.close();
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerAddWithTxn service = new CustomerAddWithTxn();
		service.doTxn();
	}
}