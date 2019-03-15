package com.course.task.logic; 

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class MarkDAO implements AbstractDAOMark {

	private ConnectionManager cm;

	MarkDAO(String connection) {
		cm = new ConnectionManager(connection);
	}

	MarkDAO() {
		cm = new ConnectionManager();
	}

	public void insert(MarkDTO mark) throws DAOException {

		try {
			if (mark != null) {
				Session session = cm.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				session.save(mark);
				session.getTransaction().commit();
			}
		} catch(Exception e) {
			throw new DAOException("Error in DAOMark insert method!", e);
		}
		
	}
	
	public void remove(MarkDTO mark) throws DAOException {

		try {
			if (mark != null) {
				Session session = cm.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				session.delete(mark);
				session.getTransaction().commit();
			}
		} catch(Exception  e) {
			throw new DAOException("Error in DAOMark remove method!", e);
		}
		
	}

	public void remove(long markId) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			MarkDTO mark = session.get(MarkDTO.class, markId);
			if (mark != null) {
				session.delete(mark);
			}
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOMark remove method!", e);
		}

	}
	
	public void update(MarkDTO mark) throws DAOException {

		try {
			if (mark != null) {
				Session session = cm.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				session.update(mark);
				session.getTransaction().commit();
			}
		} catch(Exception e) {
			throw new DAOException("Error in DAOMark update method!", e);
		}
		
	}
	
	public List<MarkDTO> getAll() throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<MarkDTO> list = session.createQuery("FROM MarkDTO markDto", MarkDTO.class).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAll method!", e);
		}
		
	}
	
	public MarkDTO getMarkById(long id) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			MarkDTO mark = (MarkDTO) session.createQuery("FROM MarkDTO markDto WHERE markDto.id = " + id).uniqueResult();
			session.getTransaction().commit();
			return mark;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getMarkById method!", e);
		}
		
	}
	
	public List<MarkDTO> getAllByStudentId(long studentId) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<MarkDTO> list = session.createQuery("SELECT new MarkDTO(markDto.id, subject.name, markDto.mark, " +
					"markDto.date) FROM MarkDTO markDto, LearningSubjectsDTO LS, SubjectDTO subject WHERE LS.studentId" +
					" = " + studentId + "AND LS.id = markDto.learningSubjectId AND LS.subjectId = subject.id",
					MarkDTO.class).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAllByStudentId method!", e);
		}
		
	}
	
	public List<MarkDTO> getAllBySubjectId(long subjectId) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<MarkDTO> list = session.createQuery("SELECT new MarkDTO(markDto.id, student.firstName, " +
					"student.lastName, markDto.mark, markDto.date) FROM MarkDTO markDto, LearningSubjectsDTO LS, " +
					"StudentDTO student WHERE LS.subjectId = " + subjectId + " AND LS.id = markDto.learningSubjectId " +
					"AND LS.studentId = student.id", MarkDTO.class).list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
		}

	}
	
	public List<MarkDTO> getAllMarksByStudentIdSubjectIdAndForPeriod(long studentId, long subjectId, String period) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			Query query = session.createQuery("SELECT new MarkDTO(markDto.id, markDto.mark, markDto.date) FROM " +
					"MarkDTO markDto, LearningSubjectsDTO LS WHERE LS.studentId = " + studentId + " AND LS.subjectId" +
					" = " + subjectId + " AND markDto.date >= :from AND markDto.date <= :until", MarkDTO.class);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String[] timeArr = period.split(" ");
			java.sql.Date sqlStartDate = new java.sql.Date(sdf.parse(timeArr[0]).getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(sdf.parse(timeArr[1]).getTime());
			query.setParameter("from", sqlStartDate);
			query.setParameter("until", sqlEndDate);
			List<MarkDTO> list = query.list();
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
		}
		
	}
	
}