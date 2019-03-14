package com.course.task.logic;

import com.ibatis.common.jdbc.ScriptRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.*;

public class StudentDAOTest extends ConnectionManager {

    private StudentDAO studentDao = null;
    private Connection conn = null;

    @BeforeClass
    public void setUp() {
        studentDao = new StudentDAO("connectionForTests");
    }

    @Test
    public void insert() throws Exception {
        StudentDTO expected = new StudentDTO("IntelliJ", "Idea");
        studentDao.insert(expected);
        StudentDTO actual = getLastRecord();
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
    }

    @Test
    public void remove1() throws Exception {
        StudentDTO expected = getLastRecord();
        studentDao.remove(expected);
        StudentDTO actual = getLastRecord();
        assertNotEquals(expected.getId(), actual.getId());
    }

    @Test(dependsOnMethods = {"getAll"})
    public void remove() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertStudentScript.sql"))));
        studentDao.remove(100L);
        List<StudentDTO> list = studentDao.getAll();
        for (StudentDTO student : list) {
            assertTrue(student.getId() != 100L);
        }
    }

    @Test(dependsOnMethods = {"getStudentById"})
    public void update() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertStudentScript.sql"))));
        StudentDTO expected = new StudentDTO(100L, "TEST", "test");
        assertNotEquals(expected, studentDao.getStudentById(100L));
        studentDao.update(expected);
        StudentDTO actual = studentDao.getStudentById(100L);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getFirstName(), actual.getFirstName());
        assertEquals(expected.getLastName(), actual.getLastName());
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeStudentScript.sql"))));
    }

    @Test
    public void getAll() throws Exception {
        List<StudentDTO> expected = studentDao.getAll();
        List<StudentDTO> actual = getTable("STUDENTS");
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
        }
    }

    @Test
    public void getStudentById() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertStudentScript.sql"))));
        StudentDTO student = studentDao.getStudentById(100L);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeStudentScript.sql"))));
        assertEquals(student.getId(), 100L);
        assertEquals(student.getFirstName(), "Java");
        assertEquals(student.getLastName(), "Learning");
    }

    @Test
    public void getAllStudentsWhoStudySubject() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/getAllStudentsWhoStudySubjectScript.sql"))));
        List<StudentDTO> expected = studentDao.getAllStudentsWhoStudySubject(2L);
        List<StudentDTO> actual = getTable("TEST");
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/dropTestTable.sql"))));
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
        }
    }

    @Test
    public void assignSubjectForStudent() throws DAOException {
        PreparedStatement getLearnedSubjects = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            studentDao.assignSubjectForStudent(1L, 2L);
            getLearnedSubjects = conn.prepareStatement("SELECT ID, STUDENT_ID, " +
                    "SUBJECT_ID FROM LEARNING_SUBJECTS WHERE STUDENT_ID = ? AND SUBJECT_ID = ? ORDER BY ID DESC LIMIT 1;");
            getLearnedSubjects.setLong(1, 1L);
            getLearnedSubjects.setLong(2, 2L);
            resultSet = getLearnedSubjects.executeQuery();
            resultSet.next();
            long studId = resultSet.getLong("STUDENT_ID");
            long subjId = resultSet.getLong("SUBJECT_ID");
            getLearnedSubjects.executeQuery();
            assertEquals(1L, studId);
            assertEquals(2L, subjId);
            ScriptRunner runner = new ScriptRunner(conn, false, false);
            runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                        .getResourceAsStream("/removeLearningSubject.sql"))));
        } catch (SQLException | IOException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (getLearnedSubjects != null) {
                    getLearnedSubjects.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }
    }

    private StudentDTO getLastRecord() throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM STUDENTS ORDER BY ID DESC LIMIT 1;");
            resultSet = getLast.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("ID");
            String firstName = resultSet.getString("FIRST_NAME");
            String lastName = resultSet.getString("LAST_NAME");
            StudentDTO studentDto = new StudentDTO(id, firstName, lastName);
            getLast.executeQuery();
            return studentDto;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (getLast != null) {
                    getLast.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }

    private List<StudentDTO> getTable(String tableTitle) throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM " + tableTitle + ";");
            resultSet = getLast.executeQuery();
            List<StudentDTO> studentList = new ArrayList<>();
            while(resultSet.next()) {
                StudentDTO student = new StudentDTO(resultSet.getLong("ID"),
                        resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"));
                studentList.add(student);
            }
            return studentList;
        } catch (SQLException e) {
            throw new DAOException(e);
        } finally {
            try {
                if (getLast != null) {
                    getLast.close();
                }
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
        }

    }
}