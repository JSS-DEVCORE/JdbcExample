package com.example.sqlite.jdbc;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import com.example.constants.AppConstants;
import com.example.dbservice.SimpleDbManager;
import com.example.image.service.ImageService;
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
	 * @param imageFile 
	 * @return
	 */
	private boolean add(Customer customer, String imageFile) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Adding Customer...");

		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " INSERT INTO customer (ctry_cd, customer_name, phone_no, phone_type, email_ad, customer_guid, "
					+ " last_mdfy_user, last_mdfy_prog, init_insert_ts, last_mdfy_ts, photo_id_bytes) "
					+ " VALUES (?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, ?) ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Set Values for INSERT
			 */
			pstmt.setString(1, customer.getCtry_cd());
			pstmt.setString(2, customer.getCustomer_name());
			pstmt.setString(3, customer.getPhone_no());
			pstmt.setString(4, customer.getPhone_type());
			pstmt.setString(5, customer.getEmail_ad());
			pstmt.setString(6, customer.getCustomer_guid());

			pstmt.setString(7, customer.getLast_mdfy_user());
			pstmt.setString(8, customer.getLast_mdfy_prog());
			
			byte[] photo_bytes = ImageService.encodeAsBytes(imageFile);
			pstmt.setBytes(9, photo_bytes);
		
			int rowsUpdated = pstmt.executeUpdate();
			success = rowsUpdated > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		CustomerAdd service = new CustomerAdd();
		Customer customer = new Customer();

		customer.setCtry_cd("US");
		customer.setCustomer_name("John Howard");
		customer.setCustomer_guid(UUID.randomUUID().toString());
		customer.setEmail_ad("john.hr@somewhere.com");
		customer.setPhone_no("2233445566");
		customer.setPhone_type("MOBILE");

		customer.setLast_mdfy_prog(CLAZZ);
		customer.setLast_mdfy_user("SYSTEM");
		
		service.add(customer, "images/thanks.png");
	}
}