package com.course.task.logic;

import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Component
public class SubjectDAO extends ConnectionManager implements AbstractDAOSubject, AutoCloseable {
	
	Connection connection = null;
	PreparedStatement insertPs = null;
	PreparedStatement removePs = null;
	PreparedStatement updatePs = null;
	PreparedStatement getAllPs = null;
	PreparedStatement getSubjectByIdPs = null;
	PreparedStatement getAllSubjectsIdLearnedByStudentPs = null;
	
	public void close() throws DAOException {
		
		SQLException exp = null;
		
		try {
			if (insertPs != null) {
				insertPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (removePs != null) {
				removePs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (updatePs != null) {
				updatePs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (getAllPs != null) {
				getAllPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (getSubjectByIdPs != null) {
				getSubjectByIdPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (getAllSubjectsIdLearnedByStudentPs != null) {
				getAllSubjectsIdLearnedByStudentPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (connection != null) {
				connection.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		
		if(exp != null) {
			throw new DAOException("Error in DAOSubject close method!", exp);
		}
		
	}
	public void insert(SubjectDTO subject) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (insertPs == null) {
				insertPs = connection.prepareStatement("INSERT INTO SUBJECTS (NAME) VALUES (?)");
			}
			insertPs.setString(1, subject.getName());
			insertPs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject insert method!", e);
		}
		
	}
	
	public void remove(SubjectDTO subject) throws DAOException {

		try {
			connection = checkForNotTest();
			if (removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM SUBJECTS WHERE ID = ?");
			}
			removePs.setLong(1, subject.getId()); 
			removePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject remove method!", e);
		}
	
	}
	
	public void remove(long subjectId) throws DAOException {

		try {
			connection = checkForNotTest();
			if (removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM SUBJECTS WHERE ID = ?");
			}
			removePs.setLong(1, subjectId); 
			removePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject remove method!", e);
		}
	
	}
	
	public void update(SubjectDTO subject) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (updatePs == null) {
				updatePs = connection.prepareStatement("UPDATE SUBJECTS SET NAME = ? WHERE ID = ?");
			}
			updatePs.setString(1, subject.getName()); 
			updatePs.setLong(2, subject.getId()); 
			updatePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject update method!", e);
		}
		
	}
	
	public List<SubjectDTO> getAll() throws DAOException {
		
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllPs == null) {
				getAllPs = connection.prepareStatement("SELECT ID, NAME FROM SUBJECTS");
			}
			resultSet = getAllPs.executeQuery();
			List<SubjectDTO> subjectList = new ArrayList<>();
			while(resultSet.next()) { 
				SubjectDTO subject = new SubjectDTO(resultSet.getLong("ID"), resultSet.getString("NAME"));
				subjectList.add(subject); 
			}
			return subjectList;
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject getAll method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOSubject getAll method!", e);
			}
		}
		
	}
	
	public SubjectDTO getSubjectById(long id) throws DAOException {
		
		SubjectDTO subject = null;
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getSubjectByIdPs == null) {
				getSubjectByIdPs = connection.prepareStatement("SELECT ID, NAME FROM SUBJECTS WHERE ID = ?");
			}
			getSubjectByIdPs.setLong(1, id);
			resultSet = getSubjectByIdPs.executeQuery();
			resultSet.next(); 
			subject = new SubjectDTO(resultSet.getLong("ID"), resultSet.getString("NAME"));
			getSubjectByIdPs.executeQuery();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOSubject getSubjectById method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOSubject getSubjectById method!", e);
			}
		}
		return subject;
		
	}
	
	public List<SubjectDTO> getAllSubjectsIdLearnedByStudent(long studentId) throws DAOException {
		
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllSubjectsIdLearnedByStudentPs == null) {
				getAllSubjectsIdLearnedByStudentPs = connection.prepareStatement("SELECT SUBJECTS.ID, SUBJECTS.NAME"+
								" FROM LEARNING_SUBJECTS, SUBJECTS  WHERE LEARNING_SUBJECTS.STUDENT_ID = ? AND"+
								" LEARNING_SUBJECTS.SUBJECT_ID = SUBJECTS.ID");
			}
			getAllSubjectsIdLearnedByStudentPs.setLong(1, studentId);
			resultSet = getAllSubjectsIdLearnedByStudentPs.executeQuery();
			List<SubjectDTO> subjectList = new ArrayList<>();
			while(resultSet.next()) {
				SubjectDTO subject = new SubjectDTO(resultSet.getLong("ID"), resultSet.getString("NAME"));
				subjectList.add(subject);
			}
			return subjectList;
		} catch (SQLException e) {
			throw new DAOException("Error in DAOSubject getAllSubjectsIdLearnedByStudent method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOSubject getAllSubjectsIdLearnedByStudent method!", e);
			}
		}
		
	}

	private Connection checkForNotTest() throws DAOException {
		try {
			StackTraceElement[] st = Thread.currentThread().getStackTrace();
			if (st[3].getClassName().equals("com.course.task.logic.SubjectDAOTest")) {
				return getConnection(true);
			} else {
				return getConnection(false);
			}
		} catch (DAOException e) {
			throw new DAOException(e);
		}
	}
	
}