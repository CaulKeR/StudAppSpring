package com.course.task.logic;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Component
public class StudentDAO extends ConnectionManager implements AbstractDAOStudent, AutoCloseable {
	
	Connection connection = null;
	PreparedStatement insertPs = null;
	PreparedStatement removePs = null;
	PreparedStatement updatePs = null;
	PreparedStatement getAllPs = null;
	PreparedStatement getStudentByIdPs = null;
	PreparedStatement getAllStudentsWhoStudySubjectPs = null;
	PreparedStatement assignSubjectForStudentPs = null;
	
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
			if (getStudentByIdPs != null) {
				getStudentByIdPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (getAllStudentsWhoStudySubjectPs != null) {
				getAllStudentsWhoStudySubjectPs.close();
			}
		} catch(SQLException e) {
			exp = e;
		}
		try {
			if (assignSubjectForStudentPs != null) {
				assignSubjectForStudentPs.close();
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
			throw new DAOException("Error in DAOStudent close method!", exp);
		}

	}
	
	public void insert(StudentDTO student) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if(insertPs == null) {
				insertPs = connection.prepareStatement("INSERT INTO STUDENTS (FIRST_NAME, LAST_NAME) VALUES (?, ?)");
			}
			insertPs.setString(1, student.getFirstName());
			insertPs.setString(2, student.getLastName());
			insertPs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOStudent insert method!", e);
		}
		
	}
	
	public void remove(StudentDTO student) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if(removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM STUDENTS WHERE ID = ?");
			}
			removePs.setLong(1, student.getId()); 
			removePs.executeUpdate();
		} catch(SQLException  e) {
			throw new DAOException("Error in DAOStudent remove method!", e);
		}

	}
	
	public void remove(long studentId) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if(removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM STUDENTS WHERE ID = ?");
			}
			removePs.setLong(1, studentId); 
			removePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOStudent remove method!", e);
		}

	}
	
	public void update(StudentDTO student) throws DAOException {

		try {
			connection = checkForNotTest();
			if(updatePs == null) {
				updatePs = connection.prepareStatement("UPDATE STUDENTS SET FIRST_NAME = ?, LAST_NAME = ? WHERE ID = ?");
			}
			updatePs.setString(1, student.getFirstName()); 
			updatePs.setString(2, student.getLastName()); 
			updatePs.setLong(3, student.getId()); 
			updatePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOStudent update method!", e);
		}

	}
	
	public List<StudentDTO> getAll() throws DAOException {

		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllPs == null) {
				getAllPs = connection.prepareStatement("SELECT ID, FIRST_NAME, LAST_NAME FROM STUDENTS");
			}
			resultSet = getAllPs.executeQuery();
			List<StudentDTO> studentList = new ArrayList<>();
			while(resultSet.next()) {
				StudentDTO student = new StudentDTO(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME")); 
				studentList.add(student);
			}
			return studentList;
		} catch(SQLException e) {
			throw new DAOException("Error in DAOStudent getAll method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOStudent getAll method!", e);
			}
		}
		
	}
	
	public StudentDTO getStudentById(long id) throws DAOException {
		
		StudentDTO student = null;
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if(getStudentByIdPs == null) {
				getStudentByIdPs = connection.prepareStatement("SELECT ID, FIRST_NAME, LAST_NAME FROM STUDENTS WHERE ID = ?");
			}
			getStudentByIdPs.setLong(1, id);
			resultSet = getStudentByIdPs.executeQuery();
			resultSet.next(); 
			student = new StudentDTO(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"));
			getStudentByIdPs.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Error in DAOStudent getStudentById method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOStudent getStudentById method!", e);
			}
		}
		return student;

	}
	
	public List<StudentDTO> getAllStudentsWhoStudySubject(long subjectId) throws DAOException {
		
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if(getAllStudentsWhoStudySubjectPs == null) {
			getAllStudentsWhoStudySubjectPs = connection.prepareStatement("SELECT STUDENTS.ID, STUDENTS.FIRST_NAME,"+
									" STUDENTS.LAST_NAME FROM LEARNING_SUBJECTS, STUDENTS WHERE"+
									" LEARNING_SUBJECTS.SUBJECT_ID = ? AND LEARNING_SUBJECTS.STUDENT_ID = STUDENTS.ID;");
			}
			getAllStudentsWhoStudySubjectPs.setLong(1, subjectId);
			resultSet = getAllStudentsWhoStudySubjectPs.executeQuery();
			List<StudentDTO> studentList = new ArrayList<>();
			while(resultSet.next()) {
				StudentDTO student = new StudentDTO(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"));
				studentList.add(student);
			}
			return studentList;
		} catch (SQLException e) {
			throw new DAOException("Error in DAOStudent getAllStudentsWhoStudySubject method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOStudent getAllStudentsWhoStudySubject method!", e);
			}
		}
		
	}
	
	public void assignSubjectForStudent(long studentId, long subjectId) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (assignSubjectForStudentPs == null) {
				assignSubjectForStudentPs = connection.prepareStatement("INSERT INTO LEARNING_SUBJECTS (STUDENT_ID, SUBJECT_ID) VALUES (?, ?)");
			}
			assignSubjectForStudentPs.setLong(1, studentId); 
			assignSubjectForStudentPs.setLong(2, subjectId); 
			assignSubjectForStudentPs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOStudent setSubjectForStudent method!", e);
		}
		
	}

	private Connection checkForNotTest() throws DAOException {
		try {
			StackTraceElement[] st = Thread.currentThread().getStackTrace();
			if (st[3].getClassName().equals("com.course.task.logic.StudentDAOTest")) {
				return getConnection(true);
			} else {
				return getConnection(false);
			}
	    } catch (DAOException e) {
			throw new DAOException(e);
		}
	}
	
}