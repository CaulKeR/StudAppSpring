package com.course.task.logic; 

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;

@Component
public class MarkDAO extends ConnectionManager implements AbstractDAOMark, AutoCloseable {

	Connection connection = null;
	PreparedStatement insertPs = null;
	PreparedStatement removePs = null;
	PreparedStatement updatePs = null;
	PreparedStatement getAllPs = null;
	PreparedStatement getMarkByIdPs = null;
	PreparedStatement getAllByStudentIdPs = null;
	PreparedStatement getAllBySubjectIdPs = null;
	PreparedStatement getAllMarksByStudentIdSubjectIdAndForPeriodPs = null;
	
	public void close() throws DAOException {
		
		SQLException exp = null;
		
		try {
			if (insertPs != null) {
				insertPs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (removePs != null) {
				removePs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (updatePs != null) {
				updatePs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (getAllPs != null) {
				getAllPs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (getMarkByIdPs != null) {
				getMarkByIdPs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (getAllByStudentIdPs != null) {
				getAllByStudentIdPs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (getAllBySubjectIdPs != null) {
				getAllBySubjectIdPs.close();
			}
		} catch (SQLException e) {
			exp = e;
		}
		try {
			if (getAllMarksByStudentIdSubjectIdAndForPeriodPs != null) {
				getAllMarksByStudentIdSubjectIdAndForPeriodPs.close();
			}
		} catch (SQLException e) {
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
			throw new DAOException("Error in DAOMark close method!", exp);
		}
		
	}

	public void insert(MarkDTO mark) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (insertPs == null) {
				insertPs = connection.prepareStatement("INSERT INTO MARKS(LEARNING_SUBJECT_ID, MARK, DATE_OF_RECEIVING) VALUES (?, ?, ?)");
			}
			insertPs.setLong(1, mark.getLearningSubjectId());
			insertPs.setInt(2, mark.getMark());
			insertPs.setString(3, mark.getDate());
			insertPs.executeUpdate();
		} catch(SQLException e) {
			e.printStackTrace();
			throw new DAOException("Error in DAOMark insert method!", e);
		}
		
	}
	
	public void remove(MarkDTO mark) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM MARKS WHERE ID = ?");
			}
			removePs.setLong(1, mark.getId()); 
			removePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark remove method!", e);
		}
		
	}

	public void remove(long id) throws DAOException {

		try {
			connection = checkForNotTest();
			if (removePs == null) {
				removePs = connection.prepareStatement("DELETE FROM MARKS WHERE ID = ?");
			}
			removePs.setLong(1, id);
			removePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark remove method!", e);
		}

	}
	
	public void update(MarkDTO mark) throws DAOException {
		
		try {
			connection = checkForNotTest();
			if (updatePs == null) {
				updatePs = connection.prepareStatement("UPDATE MARKS SET LEARNING_SUBJECT_ID = ?, MARK = ?, DATE_OF_RECEIVING = ? WHERE ID = ?");
			}
			updatePs.setLong(1, mark.getLearningSubjectId());
			updatePs.setInt(2, mark.getMark());
			updatePs.setString(3, mark.getDate());
			updatePs.setLong(4, mark.getId());
			updatePs.executeUpdate();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark update method!", e);
		}
		
	}
	
	public List<MarkDTO> getAll() throws DAOException {

		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllPs == null) {
				getAllPs = connection.prepareStatement("SELECT ID, LEARNING_SUBJECT_ID, MARK, DATE_OF_RECEIVING FROM MARKS");
			}
			resultSet = getAllPs.executeQuery();
			List<MarkDTO> markList = new ArrayList<>();
			while(resultSet.next()) {
				MarkDTO mark = new MarkDTO(resultSet.getLong("ID"), resultSet.getLong("LEARNING_SUBJECT_ID"), resultSet.getInt("MARK"), resultSet.getString("DATE_OF_RECEIVING"));
				markList.add(mark);
			}
			return markList;
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark getAll method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOMark getAll method!", e);
			}
		}		
		
	}
	
	public MarkDTO getMarkById(long id) throws DAOException {

		MarkDTO mark = null;
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getMarkByIdPs == null) {
				getMarkByIdPs = connection.prepareStatement("SELECT ID, LEARNING_SUBJECT_ID, MARK, DATE_OF_RECEIVING FROM MARKS WHERE ID = ?");
			}
			getMarkByIdPs.setLong(1, id);
			resultSet = getMarkByIdPs.executeQuery();
			resultSet.next();
			mark = new MarkDTO(resultSet.getLong("ID"), resultSet.getLong("LEARNING_SUBJECT_ID"), resultSet.getInt("MARK"), resultSet.getString("DATE_OF_RECEIVING"));
			getMarkByIdPs.executeQuery();
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark getMarkById method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOMark getMarkById method!", e);
			}
		} 
		return mark;
		
	}
	
	public List<MarkDTO> getAllByStudentId(long studentId) throws DAOException {
		
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if(getAllByStudentIdPs == null) {
				getAllByStudentIdPs = connection.prepareStatement("SELECT MARKS.ID, SUBJECTS.NAME, MARKS.MARK,"+
							" MARKS.DATE_OF_RECEIVING FROM LEARNING_SUBJECTS, MARKS, SUBJECTS WHERE"+
							" LEARNING_SUBJECTS.STUDENT_ID = ? AND LEARNING_SUBJECTS.ID = MARKS.LEARNING_SUBJECT_ID"+
							" AND LEARNING_SUBJECTS.SUBJECT_ID = SUBJECTS.ID;");
			}
			getAllByStudentIdPs.setLong(1, studentId);
			resultSet = getAllByStudentIdPs.executeQuery();
			List<MarkDTO> markList = new ArrayList<>();
			while(resultSet.next()) {
				MarkDTO mark = new MarkDTO(resultSet.getLong("ID"), resultSet.getString("NAME"), resultSet.getInt("MARK"), resultSet.getString("DATE_OF_RECEIVING"));
				markList.add(mark);
			}
			return markList;			
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark getAllByStudentId method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOMark getAllByStudentId method!", e);
			}
		}
		
	}
	
	public List<MarkDTO> getAllBySubjectId(long subjectId) throws DAOException {
		
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllBySubjectIdPs == null) {
				getAllBySubjectIdPs = connection.prepareStatement("SELECT MARKS.ID, STUDENTS.FIRST_NAME, STUDENTS.LAST_NAME,"+
							" MARKS.MARK, MARKS.DATE_OF_RECEIVING FROM LEARNING_SUBJECTS, MARKS, STUDENTS WHERE"+
							" LEARNING_SUBJECTS.SUBJECT_ID = ? AND LEARNING_SUBJECTS.ID = MARKS.LEARNING_SUBJECT_ID"+
							" AND LEARNING_SUBJECTS.STUDENT_ID = STUDENTS.ID;");
			}
			getAllBySubjectIdPs.setLong(1, subjectId);
			resultSet = getAllBySubjectIdPs.executeQuery();
			List<MarkDTO> markList = new ArrayList<>();
			while(resultSet.next()) {
				MarkDTO mark = new MarkDTO(resultSet.getLong("ID"), resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"), resultSet.getInt("MARK"), resultSet.getString("DATE_OF_RECEIVING"));
				markList.add(mark);
			}
			return markList;			
		} catch(SQLException e) {
			throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
			}
		}
		
	}
	
	public List<MarkDTO> getAllMarksByStudentIdSubjectIdAndForPeriod(long studentId, long subjectId, String period) throws DAOException {
		
		String[] timeArr = period.split(" ");
		ResultSet resultSet = null;
		try {
			connection = checkForNotTest();
			if (getAllMarksByStudentIdSubjectIdAndForPeriodPs == null) {
				getAllMarksByStudentIdSubjectIdAndForPeriodPs = connection.prepareStatement("SELECT MARKS.ID, MARKS.MARK, MARKS.DATE_OF_RECEIVING" +
						" FROM MARKS, LEARNING_SUBJECTS WHERE LEARNING_SUBJECTS.STUDENT_ID = ? AND LEARNING_SUBJECTS.SUBJECT_ID = ?" +
						" AND MARKS.DATE_OF_RECEIVING >= ? AND MARKS.DATE_OF_RECEIVING <= ?;");
			}
			getAllMarksByStudentIdSubjectIdAndForPeriodPs.setLong(1, studentId);
			getAllMarksByStudentIdSubjectIdAndForPeriodPs.setLong(2, subjectId);
			getAllMarksByStudentIdSubjectIdAndForPeriodPs.setString(3, timeArr[0]);
			getAllMarksByStudentIdSubjectIdAndForPeriodPs.setString(4, timeArr[1]);
			resultSet = getAllMarksByStudentIdSubjectIdAndForPeriodPs.executeQuery();
			List<MarkDTO> markList = new ArrayList<>();
			while(resultSet.next()) {
				MarkDTO mark = new MarkDTO(resultSet.getLong("ID"), resultSet.getInt("MARK"), resultSet.getString("DATE_OF_RECEIVING"));
				markList.add(mark);
			}
			return markList;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Error in DAOMark getAllMarksByStudentIdSubjectIdAndForPeriod method!", e);
		} finally {
			try {
				if(resultSet != null) {
					resultSet.close();
				}
			} catch(SQLException e) {
				throw new DAOException("Error in DAOMark getAllMarksByStudentIdSubjectIdAndForPeriod method!", e);
			}
		}
		
	}

	private Connection checkForNotTest() throws DAOException {
		try {
			StackTraceElement[] st = Thread.currentThread().getStackTrace();
			if (st[3].getClassName().equals("com.course.task.logic.MarkDAOTest")) {
				return getConnection(true);
			} else {
				return getConnection(false);
			}
		} catch (DAOException e) {
			throw new DAOException(e);
		}
	}
	
}