package com.course.task.web; 

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*; 
import com.course.task.logic.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Servlet extends HttpServlet {

	private @Autowired StudentDAO studentDao;
	private @Autowired SubjectDAO subjectDao;
	private @Autowired MarkDAO markDao;
	private @Autowired Utility utility;

public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		HttpSession session = req.getSession();
		processRequest(req, resp, session); 
	} catch(Exception e) {
		e.printStackTrace();
		req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students. Please try this operation again");
		req.getRequestDispatcher("/Error.jsp").forward(req, resp);
	}
} 

public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	try {
		HttpSession session = req.getSession();
		processRequest(req, resp, session); 
	} catch(Exception e) {
		req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students. Please try this operation again");
		req.getRequestDispatcher("/Error.jsp").forward(req, resp);
	}
}

public void init(ServletConfig config) throws ServletException {
	super.init(config);
	ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
	utility = context.getBean(Utility.class);
}

private void processRequest(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 
String nameCommand = req.getParameter("command");
resp.setContentType("text/html;charset=utf-8"); 

switch(nameCommand) {
case "showStudents" : showAllStudents(req, resp, session);
break; 
case "editStudent" : editStudent(req, resp, session);
break;
case "deleteStudent" : deleteStudent(req, resp, session); 
break;
case "showSubjects" : showAllSubjects(req, resp, session);
break;
case "editSubject" : editSubject(req, resp, session);
break;
case "deleteSubject" : deleteSubject(req, resp, session);
break;
case "assignSubjectForStudent" : assignSubjectForStudent(req, resp, session);
break;
case "saveChanges" : saveChangesInEdit(req, resp, session);
break;
case "fullStudentInfo" : fullStudentInfo(req, resp, session);
break;
case "fullSubjectInfo" : fullSubjectInfo(req, resp, session);
break;
case "selectSubjectToAssign" : selectSubjectToAssign(req, resp, session);
break;
} 

} 

private void showAllStudents(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException {

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
req.setAttribute("StudentsList", studentDao.getAll());
req.getRequestDispatcher("/showAllStudents.jsp").forward(req, resp);
} catch(DAOException e) { 
e.printStackTrace();
req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet showAllStudents method!", e); 
}

}

private void editStudent(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
long studentId = Long.parseLong(req.getParameter("studentId"), 10);
req.setAttribute("student", studentDao.getStudentById(studentId));
req.getRequestDispatcher("/editStudent.jsp").forward(req, resp);
} catch(DAOException e) {
req.setAttribute("ErrorMessage", "Something goes wrong when we try to edit student. Please try this operation again");
req.getRequestDispatcher("/jsp/Error.jsp").forward(req, resp); 
} catch(Exception e) { 
throw new ServletException("Error in Servlet editStudent method!", e); 
} 

} 

private void deleteStudent(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try { 
studentDao = (StudentDAO) session.getAttribute("StudDAO");
long studentId = Long.parseLong(req.getParameter("studentId"), 10);
studentDao.remove(studentId);
req.setAttribute("studentId", studentId);
req.getRequestDispatcher("/deleteStudent.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to delete student. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet deleteStudent method!", e); 
}

} 

private void showAllSubjects(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
req.setAttribute("SubjectsList", subjectDao.getAll());
req.getRequestDispatcher("/showAllSubjects.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all subjects. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet showAllSubjects method!", e); 
}

} 

private void editSubject(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
req.setAttribute("subject", subjectDao.getSubjectById(subjectId));
req.getRequestDispatcher("/editSubject.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to edit subject. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet editSubject method!", e);
}

} 

private void deleteSubject(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
subjectDao.remove(subjectId); 
req.setAttribute("subjectId", subjectId);
req.getRequestDispatcher("/deleteSubject.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to delete subject. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet deleteSubject method!", e);
}

}

private void assignSubjectForStudent(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
studentDao.assignSubjectForStudent(Long.parseLong(req.getParameter("studentId"), 10),
				Long.parseLong(req.getParameter("subjectId"), 10));
req.setAttribute("studentId", Long.parseLong(req.getParameter("studentId"), 10));
req.setAttribute("subjectId", Long.parseLong(req.getParameter("subjectId"), 10));
req.getRequestDispatcher("/AssignedSubjectForStudent.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to assign subject for student. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet assignSubjectForStudent method!", e);
}

}

private void saveChangesInEdit(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
StudentDTO studentDto = new StudentDTO();
SubjectDTO subjectDto = new SubjectDTO();
if(req.getParameter("studentId") != null){
long studentId = Long.parseLong(req.getParameter("studentId"), 10);
studentDto.setId(studentId);
String firstName = req.getParameter("firstName");
String lastName = req.getParameter("lastName");
studentDto = studentDao.getStudentById(studentId);
req.setAttribute("incorrectEnter", "");
if(utility.checkName(firstName)) {
studentDto.setFirstName(firstName); 
} else {
req.setAttribute("incorrectEnter", "Student first name and last name must consist at least one letter!");
req.setAttribute("student", studentDto);
req.getRequestDispatcher("/editStudent.jsp").forward(req, resp); 
}
if(utility.checkName(lastName)) {
studentDto.setLastName(lastName);
} else {
req.setAttribute("incorrectEnter", "Student first name and last name must consist at least one letter!");
req.setAttribute("student", studentDto);
req.getRequestDispatcher("/editStudent.jsp").forward(req, resp); 
}
studentDao.update(studentDto); 
} 
if(req.getParameter("subjectId") != null){
long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
String subjectName = req.getParameter("subjectName");
subjectDto = subjectDao.getSubjectById(subjectId);
req.setAttribute("incorrectEnter", "");
if(utility.checkName(subjectName)) {
subjectDto.setName(subjectName); 
} else {
req.setAttribute("incorrectEnter", "Subject name must consist at least one letter!");
req.setAttribute("subject", subjectDto);
req.getRequestDispatcher("/editSubject.jsp").forward(req, resp); 
}
subjectDao.update(subjectDto);
} 
req.getRequestDispatcher("/saveChangesInEdit.jsp").forward(req, resp); 
} catch(Exception e) { 
throw new ServletException(e); 
}

}

private void fullStudentInfo(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
markDao = (MarkDAO) session.getAttribute("MarkDAO");
long studentId = Long.parseLong(req.getParameter("studentId"), 10);
req.setAttribute("student", studentDao.getStudentById(studentId));
req.setAttribute("fullStudentInfoList", subjectDao.getAllSubjectsIdLearnedByStudent(studentId));
req.setAttribute("MarkList", markDao.getAllByStudentId(studentId));
req.getRequestDispatcher("/FullStudentInfo.jsp").forward(req, resp); 
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to show full information about student. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet fullStudentInfo method!", e);
}

}

private void fullSubjectInfo(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
studentDao = (StudentDAO) session.getAttribute("StudDAO");
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
markDao = (MarkDAO) session.getAttribute("MarkDAO");
long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
req.setAttribute("subject", subjectDao.getSubjectById(subjectId));
req.setAttribute("fullSubjectInfoList", studentDao.getAllStudentsWhoStudySubject(subjectId));
req.setAttribute("MarkList", markDao.getAllBySubjectId(subjectId));
req.getRequestDispatcher("/FullSubjectInfo.jsp").forward(req, resp); 
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all all information about subject. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet fullSubjectInfo method!", e);
}

}

private void selectSubjectToAssign(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws ServletException, IOException { 

try {
subjectDao = (SubjectDAO) session.getAttribute("SubjDAO");
long studentId = Long.parseLong(req.getParameter("studentId"), 10);
req.setAttribute("AssignSubjectForStudentList", subjectDao.getAll());
req.setAttribute("studentId", studentId);
req.getRequestDispatcher("/SelectSubjectToAssign.jsp").forward(req, resp);
} catch(DAOException e) { 
req.setAttribute("ErrorMessage", "Something goes wrong when we try to select subject to assign. Please try this operation again");
req.getRequestDispatcher("/Error.jsp").forward(req, resp);
} catch(Exception e) { 
throw new ServletException("Error in Servlet selectSubjectToAssign method!", e);
}

}

}