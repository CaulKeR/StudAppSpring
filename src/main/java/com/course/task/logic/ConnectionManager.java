package com.course.task.logic;

import java.util.Objects;
import java.util.Properties;
import java.sql.*;

public class ConnectionManager{
	
	private Connection connection = null;

	public Connection getConnection(boolean isTest) throws DAOException{
		
		if (connection == null) {
			try {
				Properties properties = new Properties();
                properties.load(Objects.requireNonNull(com.course.task.logic.ConnectionManager.class
                        .getClassLoader().getResourceAsStream("\\connection.properties")));
				Class.forName(properties.getProperty("driver")).getDeclaredConstructor();
				String url;
                if (isTest) {
                    url = properties.getProperty("urlForTests");
                } else {
                    url = properties.getProperty("url");
                }
				connection = DriverManager.getConnection(url, properties.getProperty("userName"), properties.getProperty("password"));
			} catch (Exception e) {
				throw new DAOException("Error in ConnectionManager getConnection method!", e);
			}
			return connection;
		} else {
			return connection;
		}
		
	}
	
}