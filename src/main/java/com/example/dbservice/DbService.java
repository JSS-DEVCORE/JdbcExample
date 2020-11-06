package com.example.dbservice;

import java.io.File;
import java.io.FileInputStream;
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

	private String propFile;
	private Properties props = null;

	private String jdbc_url;
	private String db_user;
	private String db_password;

	/**
	 * Default Constructor
	 * 
	 * @param propFile
	 */
	public DbService(String propFile) {
		super();
		this.propFile = propFile;
		try {
			readProps();
			this.jdbc_url = props.getProperty("jdbc.jdbcUrl");
			this.db_user = props.getProperty("jdbc.username");
			this.db_password = props.getProperty("jdbc.password");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Read Property File
	 * 
	 * @param propFile
	 * @return
	 * @throws IOException
	 */
	private void readProps() throws IOException {
		System.out.println("---Reading Property file...");

		InputStream is = new FileInputStream(new File(propFile));
		props = new Properties();
		props.load(is);
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