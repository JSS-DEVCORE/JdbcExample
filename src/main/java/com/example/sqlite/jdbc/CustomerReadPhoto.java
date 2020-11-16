package com.example.sqlite.jdbc;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import com.example.constants.AppConstants;
import com.example.dbservice.SimpleDbManager;
import com.example.model.Customer;

/**
 * @project JdbcExample - Query Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerReadPhoto implements AppConstants {

	/**
	 * Query by Email Ad
	 * 
	 * @param emailAd
	 */
	private void queryOne(String emailAd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Reading Customer(s) by emailAd: " + emailAd);
		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " SELECT customer_id, ctry_cd, customer_name, photo_id_bytes "
					+ " FROM customer WHERE ctry_cd = ? AND email_ad = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "US");
			pstmt.setString(2, emailAd);
			/**
			 * Get Result Set
			 */
			rs = pstmt.executeQuery();

			Customer customer = null;
			while (rs.next()) {
				customer = new Customer();
				success = true;

				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCtry_cd(rs.getString("ctry_cd"));
				customer.setCustomer_name(rs.getString("customer_name"));
				/**
				 * Saving Image from Stored byte Array
				 */
				byte[] photo_bytes = rs.getBytes("photo_id_bytes");
				/**
				 * Assign Base64 for Client Transport
				 */
				customer.setPhoto_id_bytes(Base64.getEncoder().encodeToString(photo_bytes));
				try (OutputStream out = new BufferedOutputStream(new FileOutputStream("images/customer_photo.png"))) {
					out.write(photo_bytes);
				}
				System.out.println("Photo b64: " + Base64.getEncoder().encodeToString(photo_bytes));
				System.out.println(customer);
			}
		} catch (SQLException | IOException e) {
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
		CustomerReadPhoto service = new CustomerReadPhoto();
		service.queryOne("john.hr@somewhere.com");
		// service.queryOne("kumar.r@somewhere.com");
	}
}