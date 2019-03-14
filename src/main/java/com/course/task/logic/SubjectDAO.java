package com.course.task.logic;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class SubjectDAO implements AbstractDAOSubject {

	private ConnectionManager cm;

	SubjectDAO(String connection) {
		cm = new ConnectionManager(connection);
	}

	SubjectDAO() {
		cm = new ConnectionManager();
	}

	public void insert(SubjectDTO subject) throws DAOException {
		
		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(subject);
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOSubject insert method!", e);
		}
		
	}

	public void remove(SubjectDTO subject) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (subject != null) {
				session.delete(subject);
			}
			session.getTransaction().commit();
		} catch(Exception  e) {
			throw new DAOException("Error in DAOSubject remove method!", e);
		}

	}
	
	public void remove(long subjectId) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			SubjectDTO subject = session.get(SubjectDTO.class, subjectId);
			if (subject != null) {
				session.delete(subject);
			}
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOSubject remove method!", e);
		}
	
	}
	
	public void update(SubjectDTO subject) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (subject != null) {
				session.update(subject);
			}
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOSubject update method!", e);
		}
		
	}
	
	public List<SubjectDTO> getAll() throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<SubjectDTO> list = (List<SubjectDTO>) session.createQuery("FROM SubjectDTO").list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOSubject getAll method!", e);
		}
		
	}
	
	public SubjectDTO getSubjectById(long id) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			SubjectDTO subject = (SubjectDTO) session.createQuery("FROM SubjectDTO subject WHERE subject.id = " + id)
					.uniqueResult();
			session.getTransaction().commit();
			return subject;
		} catch (Exception e) {
			throw new DAOException("Error in DAOSubject getSubjectById method!", e);
		}
		
	}
	
	public List<SubjectDTO> getAllSubjectsIdLearnedByStudent(long studentId) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<SubjectDTO> list = new ArrayList<SubjectDTO>();
			List<Object[]> objects = (List<Object[]>) session.createQuery("SELECT subject.id, subject.name " +
					"FROM SubjectDTO subject, LearningSubjectsDTO LS WHERE LS.studentId " +
					"= " + studentId +" AND LS.subjectId = subject.id").list();
			for (Object[] object : objects) {
				long id = (long) object[0];
				String name = (String) object[1];
				SubjectDTO subject = new SubjectDTO(id, name);
				list.add(subject);
			}
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOSubject getAllSubjectsIdLearnedByStudent method!", e);
		}
		
	}
	
}