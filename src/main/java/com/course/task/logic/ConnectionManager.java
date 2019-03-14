package com.course.task.logic;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;
import java.util.Properties;

public class ConnectionManager{

	private SessionFactory sessionFactory = null;
	private Connection connection;

	ConnectionManager(String connection){
		buildSessionFactory(connection);
	}

	ConnectionManager(){
		buildSessionFactory("connection");
	}

	public void buildSessionFactory(String connProperties) {
		try {
			Configuration configuration = new Configuration();
			Properties properties = new Properties();
			properties.load(Objects.requireNonNull(ConnectionManager.class.getClassLoader()
								.getResourceAsStream("\\" + connProperties + ".properties")));
			configuration.setProperties(properties);
			configuration.addAnnotatedClass(StudentDTO.class);
			configuration.addAnnotatedClass(SubjectDTO.class);
			configuration.addAnnotatedClass(MarkDTO.class);
			configuration.addAnnotatedClass(LearningSubjectsDTO.class);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		} catch (Throwable ex) {
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public SessionFactory getSessionFactory() throws HibernateException {
		return sessionFactory;
	}

	public Connection getConnection() throws DAOException {

		if (connection == null) {
			try {
				Properties properties = new Properties();
				properties.load(Objects.requireNonNull(com.course.task.logic.ConnectionManager.class
						.getClassLoader().getResourceAsStream("\\connectionForTests.properties")));
				Class.forName(properties.getProperty("hibernate.connection.driver_class")).getDeclaredConstructor();
				connection = DriverManager.getConnection(properties.getProperty("hibernate.connection.url"),
						properties.getProperty("hibernate.connection.username"),
						properties.getProperty("hibernate.connection.password"));
			} catch (Exception e) {
				throw new DAOException("Error in ConnectionManager getConnection method!", e);
			}
			return connection;
		} else {
			return connection;
		}
	}
	
}