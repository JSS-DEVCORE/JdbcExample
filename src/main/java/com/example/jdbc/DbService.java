package com.example.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @project JdbcExample - Connection Service
 * @author User
 * @date Nov 2, 2020
 */
public class DbService {

	String propFile;

	/**
	 * Default Constructor
	 * 
	 * @param propFile
	 */
	public DbService(String propFile) {
		super();
		this.propFile = propFile;
	}

	/**
	 * Read Property File
	 * 
	 * @param propFile
	 * @return
	 * @throws IOException
	 */
	private Properties readProps() throws IOException {
		System.out.println("---Reading Property file...");

		InputStream is = getClass().getClassLoader().getResourceAsStream(propFile);
		Properties props = new Properties();
		props.load(is);

		return props;
	}

	/**
	 * Get Connection with Auto Commit
	 * 
	 * @param autoCommit
	 * @return
	 * @throws SQLException
	 */
	public Connection getConnect(boolean autoCommit) throws SQLException {
		System.out.println("---Connecting to Db through Properties: " + propFile);
		Properties props = null;
		try {
			props = readProps();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String jdbc_url = props.getProperty("jdbc.url");
		String db_user = props.getProperty("jdbc.username");
		String db_password = props.getProperty("jdbc.password");

		Connection conn = DriverManager.getConnection(jdbc_url, db_user, db_password);
		conn.setAutoCommit(autoCommit);
		return conn;
	}

	/**
	 * Close Connection
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void close(Connection conn) throws SQLException {
		System.out.println("---Closing Db Connection...");
		conn.close();
	}
}