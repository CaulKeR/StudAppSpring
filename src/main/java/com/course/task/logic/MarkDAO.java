package com.course.task.logic; 

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;

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
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			session.save(mark);
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOMark insert method!", e);
		}
		
	}
	
	public void remove(MarkDTO mark) throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (mark != null) {
				session.delete(mark);
			}
			session.getTransaction().commit();
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
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			if (mark != null) {
				session.update(mark);
			}
			session.getTransaction().commit();
		} catch(Exception e) {
			throw new DAOException("Error in DAOMark update method!", e);
		}
		
	}
	
	public List<MarkDTO> getAll() throws DAOException {

		try {
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<MarkDTO> list = (List<MarkDTO>) session.createQuery("FROM MarkDTO markDto").list();
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
			List<MarkDTO> list = new ArrayList<>();
			List<Object[]> objects = (List<Object[]>) session.createQuery("SELECT markDto.id, subject.name, " +
                    "markDto.mark, markDto.date FROM MarkDTO markDto, LearningSubjectsDTO LS, SubjectDTO subject " +
                    "WHERE LS.studentId = " + studentId + " AND LS.id = markDto.learningSubjectId AND LS.subjectId" +
                    " = subject.id").list();
			for (Object[] object : objects) {
				long id = (long) object[0];
				String name = (String) object[1];
				int mark = (int) object[2];
				Date date = Date.valueOf(((Timestamp) object[3]).toLocalDateTime().toLocalDate());
				MarkDTO markDto = new MarkDTO(id, name, mark, date);
				list.add(markDto);
			}
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
			List<MarkDTO> list = new ArrayList<>();
			List<Object[]> objects = (List<Object[]>) session.createQuery("SELECT markDto.id, student.firstName, " +
					"student.lastName, markDto.mark, markDto.date FROM MarkDTO markDto, LearningSubjectsDTO LS, " +
					"StudentDTO student WHERE LS.subjectId = " + subjectId + " AND LS.id = markDto.learningSubjectId" +
					" AND LS.studentId = student.id").list();
			for (Object[] object : objects) {
				long id = (long) object[0];
				String firstName = (String) object[1];
				String lastName = (String) object[2];
				int mark = (int) object[3];
				Date date = Date.valueOf(((Timestamp) object[4]).toLocalDateTime().toLocalDate());
				MarkDTO markDto = new MarkDTO(id, firstName, lastName, mark, date);
				list.add(markDto);
			}
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
		}

	}
	
	public List<MarkDTO> getAllMarksByStudentIdSubjectIdAndForPeriod(long studentId, long subjectId, String period) throws DAOException {

		try {
			String[] timeArr = period.split(" ");
			Session session = cm.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			List<MarkDTO> list = new ArrayList<>();
			Query query = session.createQuery("SELECT markDto.id, markDto.mark, markDto.date FROM MarkDTO markDto," +
					" LearningSubjectsDTO LS WHERE LS.studentId = " + studentId + " AND LS.subjectId = " + subjectId +
					" AND markDto.date >= :from AND markDto.date <= :until");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date date1 = sdf.parse(timeArr[0]);
			java.util.Date date2 = sdf.parse(timeArr[1]);
			java.sql.Date sqlStartDate = new java.sql.Date(date1.getTime());
			java.sql.Date sqlEndDate = new java.sql.Date(date2.getTime());
			query.setParameter("from", sqlStartDate);
			query.setParameter("until", sqlEndDate);
			List<Object[]> objects = (List<Object[]>) query.list();
			for (Object[] object : objects) {
				long id = (long) object[0];
				int mark = (int) object[1];
				Timestamp date = (Timestamp) object[2];
				MarkDTO markDto = new MarkDTO(id, mark, date);
				list.add(markDto);
			}
			session.getTransaction().commit();
			return list;
		} catch (Exception e) {
			throw new DAOException("Error in DAOMark getAllBySubjectId method!", e);
		}
		
	}
	
}