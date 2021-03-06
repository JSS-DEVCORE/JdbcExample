package com.example.sqlite.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.constants.AppConstants;
import com.example.dbservice.SimpleDbManager;
import com.example.model.Customer;

/**
 * @project JdbcExample - Query All Customer
 * @author User
 * @date Nov 2, 2020
 */
public class CustomerQueryAll implements AppConstants {

	/**
	 * Query All Customers
	 */
	private void queryAll() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		System.out.println("Reading all Customer(s)...");
		boolean autoCommit = true;
		try {
			/**
			 * Get Connection
			 */
			conn = SimpleDbManager.getConnection(autoCommit);
			String sql = " SELECT customer_id, ctry_cd, customer_name, email_ad, phone_no, customer_guid FROM customer ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			/**
			 * Get Result Set
			 */
			rs = pstmt.executeQuery();
			Customer customer = null;
			while (rs.next()) {
				customer = new Customer();

				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCtry_cd(rs.getString("ctry_cd"));
				customer.setCustomer_name(rs.getString("customer_name"));
				customer.setEmail_ad(rs.getString("email_ad"));
				/**
				 * You can also use Query Column Number - Not Recommended though
				 */
				customer.setCustomer_guid(rs.getString(5));

				System.out.println(customer);
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
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Done - Reading all Customer(s)...");
		System.out.println("=================================");
	}

	/**
	 * Main
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		CustomerQueryAll service = new CustomerQueryAll();
		service.queryAll();
	}
}