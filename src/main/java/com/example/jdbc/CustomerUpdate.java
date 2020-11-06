package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.constants.AppConstants;
import com.example.dbservice.DbService;

/**
 * @project JdbcExample - Update Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerUpdate implements AppConstants {

	static String CLAZZ = CustomerUpdate.class.getSimpleName();

	/**
	 * Update Phone Number
	 * 
	 * @param emailAd
	 * @param phoneNo
	 * @return
	 */
	private boolean update(String emailAd, String phoneNo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		boolean autoCommit = true;
		
		System.out.println("Updating Customer PhoneNo by EmailAd: " + emailAd);
		try {
			/**
			 * Get Connection
			 */
			conn = DbService.getConnect(autoCommit);
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
				if (conn != null) {
					conn.close();
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
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerUpdate service = new CustomerUpdate();
		service.update("john@somewhere.com", "2233445566");
	}
}