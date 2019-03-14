package com.course.task.logic;

import java.util.List;

public interface AbstractDAOSubject {
	
	void insert(SubjectDTO subject) throws DAOException;
	void remove(SubjectDTO subject) throws DAOException;
	void remove(long subjectId) throws DAOException;
	void update(SubjectDTO subject) throws DAOException;
	
	SubjectDTO getSubjectById(long id) throws DAOException;
	List<SubjectDTO> getAll() throws DAOException;
	List<SubjectDTO> getAllSubjectsIdLearnedByStudent(long studentId) throws DAOException;
	
}