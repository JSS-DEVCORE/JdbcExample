package com.example.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.constants.AppConstants;
import com.example.dbservice.DbService;

/**
 * @project JdbcExample - Delete Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerDelete implements AppConstants {

	static String CLAZZ = CustomerDelete.class.getSimpleName();

	/**
	 * Delete by Email Address
	 * 
	 * @param emailAd
	 * @return
	 */
	private boolean delete(String emailAd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Deleting Customer by EmailAd: " + emailAd);
		try {
			/**
			 * Get Connection
			 */
			conn = DbService.getConnect(autoCommit);
			String sql = " DELETE FROM customer WHERE email_ad = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Set Value for Delete
			 */
			pstmt.setString(1, emailAd);
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
		System.out.println("Done - Deleting Customer by EmailAd: " + emailAd + " success: " + success);
		System.out.println("=================================");

		return success;
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerDelete service = new CustomerDelete();
		service.delete("john@somewhere.com");
	}
}