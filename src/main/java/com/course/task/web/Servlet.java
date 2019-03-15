package com.course.task.web;

import java.io.*;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import com.course.task.dao.impl.MarkDAO;
import com.course.task.dao.impl.StudentDAO;
import com.course.task.dao.impl.SubjectDAO;
import com.course.task.dto.StudentDTO;
import com.course.task.dto.SubjectDTO;
import com.course.task.dao.DAOException;
import com.course.task.logic.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

@Controller
@RequestMapping(value = "/servlet")
public class Servlet extends HttpServlet {

    @Autowired
    private Utility utility;
    @Autowired
    private StudentDAO studentDao;
    @Autowired
    private SubjectDAO subjectDao;
    @Autowired
    private MarkDAO markDao;

    @Override
    @GetMapping
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students. " +
                    "Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        }
    }

    @Override
    @PostMapping
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            processRequest(req, resp);
        } catch (Exception e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        }
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
        super.init(config);
    }

    private void processRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nameCommand = req.getParameter("command");
        resp.setContentType("text/html;charset=utf-8");

        switch (nameCommand) {
            case "showStudents":
                showAllStudents(req, resp);
                break;
            case "editStudent":
                editStudent(req, resp);
                break;
            case "deleteStudent":
                deleteStudent(req, resp);
                break;
            case "showSubjects":
                showAllSubjects(req, resp);
                break;
            case "editSubject":
                editSubject(req, resp);
                break;
            case "deleteSubject":
                deleteSubject(req, resp);
                break;
            case "assignSubjectForStudent":
                assignSubjectForStudent(req, resp);
                break;
            case "saveChanges":
                saveChangesInEdit(req, resp);
                break;
            case "fullStudentInfo":
                fullStudentInfo(req, resp);
                break;
            case "fullSubjectInfo":
                fullSubjectInfo(req, resp);
                break;
            case "selectSubjectToAssign":
                selectSubjectToAssign(req, resp);
                break;
        }

    }

    private void showAllStudents(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            req.setAttribute("StudentsList", studentDao.getAll());
            req.getRequestDispatcher("/showAllStudents.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all students." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet showAllStudents method!", e);
        }

    }

    private void editStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long studentId = Long.parseLong(req.getParameter("studentId"), 10);
            req.setAttribute("student", studentDao.getStudentById(studentId));
            req.getRequestDispatcher("/editStudent.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to edit student." +
                    " Please try this operation again");
            req.getRequestDispatcher("/jsp/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet editStudent method!", e);
        }

    }

    private void deleteStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long studentId = Long.parseLong(req.getParameter("studentId"), 10);
            studentDao.remove(studentId);
            req.setAttribute("studentId", studentId);
            req.getRequestDispatcher("/deleteStudent.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to delete student." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet deleteStudent method!", e);
        }

    }

    private void showAllSubjects(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            req.setAttribute("SubjectsList", subjectDao.getAll());
            req.getRequestDispatcher("/showAllSubjects.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all subjects." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet showAllSubjects method!", e);
        }

    }

    private void editSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
            req.setAttribute("subject", subjectDao.getSubjectById(subjectId));
            req.getRequestDispatcher("/editSubject.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to edit subject." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet editSubject method!", e);
        }

    }

    private void deleteSubject(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
            subjectDao.remove(subjectId);
            req.setAttribute("subjectId", subjectId);
            req.getRequestDispatcher("/deleteSubject.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to delete subject." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet deleteSubject method!", e);
        }

    }

    private void assignSubjectForStudent(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            studentDao.assignSubjectForStudent(Long.parseLong(req.getParameter("studentId"), 10),
                    Long.parseLong(req.getParameter("subjectId"), 10));
            req.setAttribute("studentId", Long.parseLong(req.getParameter("studentId"), 10));
            req.setAttribute("subjectId", Long.parseLong(req.getParameter("subjectId"), 10));
            req.getRequestDispatcher("/AssignedSubjectForStudent.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to assign subject for student. " +
                    "Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet assignSubjectForStudent method!", e);
        }

    }

    private void saveChangesInEdit(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

        try {
            StudentDTO studentDto = new StudentDTO();
            SubjectDTO subjectDto = new SubjectDTO();
            if (req.getParameter("studentId") != null) {
                long studentId = Long.parseLong(req.getParameter("studentId"), 10);
                studentDto.setId(studentId);
                String firstName = req.getParameter("firstName");
                String lastName = req.getParameter("lastName");
                studentDto = studentDao.getStudentById(studentId);
                req.setAttribute("incorrectEnter", "");
                if (utility.checkName(firstName)) {
                    studentDto.setFirstName(firstName);
                } else {
                    req.setAttribute("incorrectEnter", "Student first name and last name must consist at least" +
                            " one letter!");
                    req.setAttribute("student", studentDto);
                    req.getRequestDispatcher("/editStudent.jsp").forward(req, resp);
                }
                if (utility.checkName(lastName)) {
                    studentDto.setLastName(lastName);
                } else {
                    req.setAttribute("incorrectEnter", "Student first name and last name must consist at least" +
                            " one letter!");
                    req.setAttribute("student", studentDto);
                    req.getRequestDispatcher("/editStudent.jsp").forward(req, resp);
                }
                studentDao.update(studentDto);
            }
            if (req.getParameter("subjectId") != null) {
                long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
                String subjectName = req.getParameter("subjectName");
                subjectDto = subjectDao.getSubjectById(subjectId);
                req.setAttribute("incorrectEnter", "");
                if (utility.checkName(subjectName)) {
                    subjectDto.setName(subjectName);
                } else {
                    req.setAttribute("incorrectEnter", "Subject name must consist at least one letter!");
                    req.setAttribute("subject", subjectDto);
                    req.getRequestDispatcher("/editSubject.jsp").forward(req, resp);
                }
                subjectDao.update(subjectDto);
            }
            req.getRequestDispatcher("/saveChangesInEdit.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }

    }

    private void fullStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long studentId = Long.parseLong(req.getParameter("studentId"), 10);
            req.setAttribute("student", studentDao.getStudentById(studentId));
            req.setAttribute("fullStudentInfoList", subjectDao.getAllSubjectsIdLearnedByStudent(studentId));
            req.setAttribute("MarkList", markDao.getAllByStudentId(studentId));
            req.getRequestDispatcher("/FullStudentInfo.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show full information about" +
                    " student. Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet fullStudentInfo method!", e);
        }

    }

    private void fullSubjectInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long subjectId = Long.parseLong(req.getParameter("subjectId"), 10);
            req.setAttribute("subject", subjectDao.getSubjectById(subjectId));
            req.setAttribute("fullSubjectInfoList", studentDao.getAllStudentsWhoStudySubject(subjectId));
            req.setAttribute("MarkList", markDao.getAllBySubjectId(subjectId));
            req.getRequestDispatcher("/FullSubjectInfo.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to show all all information" +
                    " about subject. Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet fullSubjectInfo method!", e);
        }

    }

    private void selectSubjectToAssign(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            long studentId = Long.parseLong(req.getParameter("studentId"), 10);
            req.setAttribute("AssignSubjectForStudentList", subjectDao.getAll());
            req.setAttribute("studentId", studentId);
            req.getRequestDispatcher("/SelectSubjectToAssign.jsp").forward(req, resp);
        } catch (DAOException e) {
            req.setAttribute("ErrorMessage", "Something goes wrong when we try to select subject to assign." +
                    " Please try this operation again");
            req.getRequestDispatcher("/Error.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Error in Servlet selectSubjectToAssign method!", e);
        }

    }

}