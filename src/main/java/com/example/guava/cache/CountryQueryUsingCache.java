/**
 * @project		JdbcExample - Country Cache Service
 * @author		User
 * @date		Nov 4, 2020
 */
package com.example.guava.cache;

import java.sql.SQLException;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.example.constants.AppConstants;
import com.example.model.Country;

public class CountryQueryUsingCache implements AppConstants {

	private static final Logger logger = LogManager.getLogger(CountryQueryUsingCache.class);

	/**
	 * Start
	 * 
	 * @throws SQLException
	 */
	private void execute() throws SQLException {
		CountryCacheServiceImpl countryService = new CountryCacheServiceImpl();
		try {
			for (String ctry_cd : new String[] { "IN", "US", "SG", "JP", "FR", "GB", "AE" }) {
				long startTime = new Date().getTime();
				/**
				 * Search Country
				 */
				Country country = countryService.find(ctry_cd);
				logger.info(country);
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
		}
	}

	/**
	 * Main Driver
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			new CountryQueryUsingCache().execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}