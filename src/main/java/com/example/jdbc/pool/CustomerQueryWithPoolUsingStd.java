/**
 * @project		JdbcExample
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.jdbc.pool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.dbservice.HikariPoolStdManager;
import com.example.model.Country;
import com.zaxxer.hikari.HikariDataSource;

public class CustomerQueryWithPoolUsingStd implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CustomerQueryWithPoolUsingStd.class);

	/**
	 * Start
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		HikariDataSource hds = HikariPoolStdManager.getDataSource();
		try {
			for (String ctryCd : new String[] { "IN", "US", "SG", "JP", "XX", "FR", "GB", "AE" }) {
				long startTime = new Date().getTime();
				/**
				 * Search Country
				 */
				search(hds, ctryCd);
				long endTime = new Date().getTime();
				/**
				 * Compute Time Taken
				 */
				long difference = endTime - startTime;
				logger.info("---Action completed in " + difference + " ms");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			/**
			 * Shutdown DataSource
			 */
			hds.close();
		}
	}

	/**
	 * Search Country
	 * 
	 * @param hds
	 * @param country_cd
	 */
	private void search(HikariDataSource hds, String country_cd) {
		logger.info("\n===Reading Country(s) by Ctry: " + country_cd);

		PreparedStatement pstmt = null;
		ResultSet rs = null;

		Country country = null;
		boolean success = false;
		Connection conn = null;
		try {
			/**
			 * Get Connection from DataSource
			 */
			conn = hds.getConnection();
			logger.info("Connection: " + conn);
			String sql = " SELECT country_id, country_cd, country_nm FROM country WHERE country_cd = ? ";
			/**
			 * Prepare Statement
			 */
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, country_cd);
			/**
			 * Get Result Set
			 */
			rs = pstmt.executeQuery();
			while (rs.next()) {
				country = new Country();
				success = true;

				country.setCountry_id(rs.getLong("country_id"));
				country.setCountry_cd(rs.getString("country_cd"));
				country.setCountry_nm(rs.getString("country_nm"));
				logger.info(country);
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
		if (!success) {
			logger.warn("Country NOT FOUND for Ctry: " + country_cd);
		} else {
			logger.info("Done - Reading Country(s) by Ctry: " + country_cd);
		}
	}

	/**
	 * Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CustomerQueryWithPoolUsingStd().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}