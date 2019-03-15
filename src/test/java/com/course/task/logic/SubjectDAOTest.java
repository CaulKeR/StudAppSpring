package com.course.task.logic;

import com.course.task.dao.DAOException;
import com.course.task.dao.impl.SubjectDAO;
import com.course.task.dto.SubjectDTO;
import com.ibatis.common.jdbc.ScriptRunner;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.testng.Assert.*;

public class SubjectDAOTest extends ConnectionManager {

    private SubjectDAO subjectDao = null;
    private Connection conn = null;

    @BeforeClass
    public void setUp() {
        subjectDao = new SubjectDAO("connectionForTests");
    }

    @Test
    public void insert() throws Exception {
        SubjectDTO expected = new SubjectDTO("Test");
        subjectDao.insert(expected);
        SubjectDTO actual = getLastRecord();
        assertEquals(expected.getName(), actual.getName());
    }

    @Test
    public void remove1() throws Exception {
        SubjectDTO expected = getLastRecord();
        subjectDao.remove(expected);
        SubjectDTO actual = getLastRecord();
        assertNotEquals(expected.getId(), actual.getId());
    }

    @Test(dependsOnMethods = {"getAll"})
    public void remove() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertSubjectScript.sql"))));
        subjectDao.remove(100L);
        List<SubjectDTO> list = subjectDao.getAll();
        for (SubjectDTO subject : list) {
            assertTrue(subject.getId() != 100L);
        }
    }

    @Test(dependsOnMethods = {"getSubjectById"})
    public void update() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertSubjectScript.sql"))));
        SubjectDTO expected = new SubjectDTO(100L, "TEST");
        assertNotEquals(expected, subjectDao.getSubjectById(100L));
        subjectDao.update(expected);
        SubjectDTO actual = subjectDao.getSubjectById(100L);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeSubjectScript.sql"))));
    }

    @Test
    public void getAll() throws Exception {
        List<SubjectDTO> expected = subjectDao.getAll();
        List<SubjectDTO> actual = getTable("SUBJECTS");
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }

    @Test
    public void getSubjectById() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertSubjectScript.sql"))));
        SubjectDTO subject = subjectDao.getSubjectById(100L);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeSubjectScript.sql"))));
        assertEquals(subject.getId(), 100L);
        assertEquals(subject.getName(), "Java");
    }

    @Test
    public void getAllSubjectsIdLearnedByStudent() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/getAllSubjectsIdLearnedByStudent.sql"))));
        List<SubjectDTO> expected = subjectDao.getAllSubjectsIdLearnedByStudent(1L);
        List<SubjectDTO> actual = getTable("TEST");
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/dropTestTable.sql"))));
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
        }
    }

    private SubjectDTO getLastRecord() throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM SUBJECTS ORDER BY ID DESC LIMIT 1;");
            resultSet = getLast.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("ID");
            String name = resultSet.getString("NAME");
            SubjectDTO subject = new SubjectDTO(id, name);
            getLast.executeQuery();
            return subject;
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

    private List<SubjectDTO> getTable(String tableTitle) throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM " + tableTitle + ";");
            resultSet = getLast.executeQuery();
            List<SubjectDTO> subjectList = new ArrayList<>();
            while(resultSet.next()) {
                SubjectDTO subject = new SubjectDTO(resultSet.getLong("ID"),
                            resultSet.getString("NAME"));
                subjectList.add(subject);
            }
            return subjectList;
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