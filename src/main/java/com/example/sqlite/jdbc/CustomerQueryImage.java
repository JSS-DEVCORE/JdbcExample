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
import com.example.model.CustomerImage;

/**
 * @project JdbcExample - Query Customer Image
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerQueryImage implements AppConstants {

	/**
	 * Query Customer Image
	 * 
	 * @param customer_id
	 */
	private void queryOne(long customer_id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean success = false;
		boolean autoCommit = true;
		System.out.println("Reading Customer Image by Customer Id...");
		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " SELECT * FROM customer_image WHERE customer_id = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, customer_id);
			/**
			 * Get Result Set
			 */
			rs = pstmt.executeQuery();

			CustomerImage customerImage = null;
			while (rs.next()) {
				customerImage = new CustomerImage();
				success = true;

				customerImage.setCustomer_image_id(rs.getLong("customer_image_id"));
				customerImage.setCustomer_id(rs.getLong("customer_id"));
				customerImage.setImage_type(rs.getString("image_type"));
				/**
				 * Saving Image from Stored byte Array
				 */
				byte[] imageBytes = rs.getBytes("image");
				/**
				 * Assign Base64 for Client Transport
				 */
				customerImage.setImage(Base64.getEncoder().encodeToString(imageBytes));
				String imageOutFile = "images/customer_image_" + customerImage.getCustomer_id() + "_"
						+ customerImage.getCustomer_image_id() + ".png";
				/**
				 * Read back to ensure Image is correct
				 */
				try (OutputStream out = new BufferedOutputStream(new FileOutputStream(imageOutFile))) {
					out.write(imageBytes);
				}
				System.out.println("Check Image: " + imageOutFile);
				System.out.println(customerImage);
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
			System.err.println("Customer Image NOT FOUND for customer_id: " + customer_id);
		} else {
			System.out.println("Done - Reading Customer(s) by customer_id: " + customer_id);
		}
		System.out.println("=================================");
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerQueryImage service = new CustomerQueryImage();
		service.queryOne(1L);
	}
}