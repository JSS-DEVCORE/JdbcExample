package com.example.dbservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.example.constants.AppConstants;

/**
 * @project JdbcExample - Connection Service
 * @author User
 * @date Nov 2, 2020
 */
public class DbService implements AppConstants {

	private static Properties props = null;

	static {
		try {
			System.out.println("---Reading Property file: " + DB_PROP_FILE);
			InputStream is = new FileInputStream(new File(DB_PROP_FILE));
			props = new Properties();
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get Connection with Auto Commit
	 * 
	 * @param autoCommit
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnect(boolean autoCommit) throws SQLException {
		System.out.println("---Getting Connection...");
		Connection conn = DriverManager.getConnection(props.getProperty("jdbc.jdbcUrl"),
				props.getProperty("jdbc.username"), props.getProperty("jdbc.password"));
		conn.setAutoCommit(autoCommit);
		System.out.println("---Got Connection: " + conn);
		return conn;
	}
}
