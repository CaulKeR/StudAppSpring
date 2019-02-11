package com.course.task.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import javax.servlet.http.*;

public class Listener implements HttpSessionListener {

	private @Autowired StudentDAO studentDao;
	private @Autowired SubjectDAO subjectDao;
	private @Autowired MarkDAO markDao;

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		
		try {
			ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
			HttpSession session = se.getSession();
			studentDao = context.getBean(StudentDAO.class);
			session.setAttribute("StudDAO", studentDao);
			subjectDao = context.getBean(SubjectDAO.class);
			session.setAttribute("SubjDAO", subjectDao);
			markDao = context.getBean(MarkDAO.class);
			session.setAttribute("MarkDAO", markDao);
			System.out.println("Session and DAO created");
		} catch(Exception e) {
			throw new RuntimeException("Error in Servlet sessionCreated method!", e);
		}
		
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		
		HttpSession session = se.getSession();
		studentDao = (StudentDAO) session.getAttribute("StudDAO");
		session.removeAttribute("StudDAO");
		subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
		session.removeAttribute("SubjDAO");
		markDao = (MarkDAO) session.getAttribute("MarkDAO");
		session.removeAttribute("MarkDAO");
		
		DAOException exp = null;
		
		try {
			if (studentDao != null) {
				studentDao.close();
			}
		} catch(DAOException e) {
			exp = e;
		}
		try {
			if (subjectDao != null) {
				subjectDao.close();
			}
		} catch(DAOException e) {
			exp = e;
		}
		try {
			if (markDao != null) {
				markDao.close();
			}
		} catch(DAOException e) {
			exp = e;
		}
		
		if(exp != null) {
			throw new RuntimeException("Error in Servlet sessionDestroyed method!", exp);
		}
		
	}
	
}