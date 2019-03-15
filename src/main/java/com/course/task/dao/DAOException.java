package com.course.task.dao;

public class DAOException extends Exception {
	
	public DAOException() {
		super();
    }
	
	public DAOException(Exception e){
		super(e);
	}
	
	public DAOException(String er, Exception e){
		super(er);
	}
	
}