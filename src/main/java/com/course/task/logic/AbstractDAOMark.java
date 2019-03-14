package com.course.task.logic; 

import java.util.List;

public interface AbstractDAOMark {
	
	void insert(MarkDTO mark) throws DAOException;
	void remove(MarkDTO mark) throws DAOException;
	void remove(long markId) throws DAOException;
	void update(MarkDTO mark) throws DAOException;
	
	MarkDTO getMarkById(long id) throws DAOException;
	List<MarkDTO> getAll() throws DAOException;
	List<MarkDTO> getAllByStudentId(long id) throws DAOException;
	List<MarkDTO> getAllMarksByStudentIdSubjectIdAndForPeriod(long studentId, long subjectId,
															  String period) throws DAOException;
	
}