package com.course.task.dao;

import com.course.task.dto.StudentDTO;
import java.util.List;

public interface AbstractDAOStudent {
	
	void insert(StudentDTO student) throws DAOException;
	void remove(StudentDTO student) throws DAOException;
	void remove(long studentId) throws DAOException;
	void update(StudentDTO student) throws DAOException;
	
	StudentDTO getStudentById(long id) throws DAOException;
	List<StudentDTO> getAll() throws DAOException;
	List<StudentDTO> getAllStudentsWhoStudySubject(long subjectId) throws DAOException;
	
}