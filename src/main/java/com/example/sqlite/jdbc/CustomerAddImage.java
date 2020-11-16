package com.example.sqlite.jdbc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.example.constants.AppConstants;
import com.example.dbservice.SimpleDbManager;
import com.example.image.service.ImageService;
import com.example.model.CustomerImage;

/**
 * @project JdbcExample - Add Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerAddImage implements AppConstants {

	static String CLAZZ = CustomerAddImage.class.getSimpleName();

	/**
	 * Add Customer Image
	 * 
	 * @param customerImage
	 * @param imageFile
	 * @return
	 */
	private boolean add(CustomerImage customerImage, String imageFile) {
		Connection conn = null;
		PreparedStatement pstmt = null;

		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Adding Customer Image...");

		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " INSERT INTO customer_image (customer_id, image_type, image, last_mdfy_ts, last_mdfy_user, last_mdfy_prog) "
					+ " VALUES (?, ?, ?, CURRENT_TIMESTAMP, 'SYSTEM', ?) ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Set Values for INSERT
			 */
			pstmt.setLong(1, customerImage.getCustomer_id());
			pstmt.setString(2, customerImage.getImage_type());

			byte[] imageBytes = ImageService.encodeAsBytes(imageFile);
			pstmt.setBytes(3, imageBytes);

			pstmt.setString(4, CLAZZ);
			/**
			 * Execute
			 */
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
		System.out.println("Done - Adding Customer Image success: " + success);
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
		CustomerAddImage service = new CustomerAddImage();
		CustomerImage customerImage = new CustomerImage();
		/**
		 * Customer ID Matches Customer Table
		 */
		customerImage.setCustomer_id(1L);
		customerImage.setImage_type("PAN");

		service.add(customerImage, "images/thanks.png");
	}
}