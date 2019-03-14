package com.course.task.logic;

import com.ibatis.common.jdbc.ScriptRunner;
import org.testng.annotations.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.testng.Assert.*;

public class MarkDAOTest extends ConnectionManager {

    private MarkDAO markDao = null;
    private Connection conn = null;
    private DateFormat df = null;

    @BeforeSuite
    public void setUp() {
        markDao = new MarkDAO("connectionForTests");
        df = new SimpleDateFormat("yyyy-MM-dd");
    }

    @Test
    public void insert() throws Exception {
        Date date = df.parse("2019-01-21");
        MarkDTO expected = new MarkDTO(1L, 7, date);
        markDao.insert(expected);
        MarkDTO actual = getLastRecord();
        assertEquals(expected.getLearningSubjectId(), actual.getLearningSubjectId());
        assertEquals(expected.getMark(), actual.getMark());
        assertEquals(df.format(expected.getDate()), df.format(actual.getDate()));
    }

    @Test
    public void remove1() throws Exception {
        MarkDTO expected = getLastRecord();
        markDao.remove(expected);
        MarkDTO actual = getLastRecord();
        assertNotEquals(expected.getId(), actual.getId());
    }

    @Test(dependsOnMethods = {"getAll"})
    public void remove() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertMarkScript.sql"))));
        markDao.remove(100L);
        List<MarkDTO> list = markDao.getAll();
        for (MarkDTO mark : list) {
            assertTrue(mark.getId() != 100L);
        }
    }

    @Test(dependsOnMethods = {"getMarkById"})
    public void update() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertMarkScript.sql"))));
        Date date = df.parse("2019-01-23");
        MarkDTO expected = new MarkDTO(100L , 4L, 6, date);
        assertNotEquals(expected, markDao.getMarkById(100L));
        markDao.update(expected);
        MarkDTO actual = markDao.getMarkById(100L);
        assertEquals(expected.getMark(), actual.getMark());
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeMarkScript.sql"))));
    }

    @Test
    public void getAll() throws Exception {
        List<MarkDTO> expected = markDao.getAll();
        List<MarkDTO> actual = getTable("MARKS", "");
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getLearningSubjectId(), actual.get(i).getLearningSubjectId());
            assertEquals(expected.get(i).getMark(), actual.get(i).getMark());
            assertEquals(df.format(expected.get(i).getDate()), df.format(actual.get(i).getDate()));
        }
    }

    @Test
    public void getMarkById() throws Exception  {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/insertMarkScript.sql"))));
        MarkDTO markDto = markDao.getMarkById(100L);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/removeMarkScript.sql"))));
        assertEquals(markDto.getId(), 100L);
        assertEquals(markDto.getLearningSubjectId(), 4);
        assertEquals(markDto.getMark(), 5);
        assertEquals(df.format(markDto.getDate()), "2019-01-23");
    }

    @Test
    public void getAllByStudentId() throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/getAllByStudentId.sql"))));
        List<MarkDTO> expected = markDao.getAllByStudentId(1L);
        List<MarkDTO> actual = getTable("TEST", "student");
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/dropTestTable.sql"))));
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getName(), actual.get(i).getName());
            assertEquals(expected.get(i).getMark(), actual.get(i).getMark());
            assertEquals(expected.get(i).getDate(), actual.get(i).getDate());
        }
    }

    @Test
    public void getAllBySubjectId()  throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/getAllBySubjectId.sql"))));
        List<MarkDTO> expected = markDao.getAllBySubjectId(2L);
        List<MarkDTO> actual = getTable("TEST" , "subject");
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/dropTestTable.sql"))));
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getFirstName(), actual.get(i).getFirstName());
            assertEquals(expected.get(i).getLastName(), actual.get(i).getLastName());
            assertEquals(expected.get(i).getMark(), actual.get(i).getMark());
            assertEquals(expected.get(i).getDate(), actual.get(i).getDate());
        }
    }

    @Test
    public void getAllMarksByStudentIdSubjectIdAndForPeriod()  throws Exception {
        conn = getConnection();
        ScriptRunner runner = new ScriptRunner(conn, false, false);
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/getAllByStudentIdSubjectIdAndForPeriod.sql"))));
        List<MarkDTO> expected = markDao.getAllMarksByStudentIdSubjectIdAndForPeriod(1L, 2L,
                    "2017-09-09 2019-01-22");
        List<MarkDTO> actual = getTable("TEST" , "period");
        runner.runScript(new BufferedReader(new InputStreamReader(getClass()
                    .getResourceAsStream("/dropTestTable.sql"))));
        assertEquals(expected.size(), actual.size());
        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getId(), actual.get(i).getId());
            assertEquals(expected.get(i).getMark(), actual.get(i).getMark());
            assertEquals(expected.get(i).getDate(), actual.get(i).getDate());
        }
    }

    private MarkDTO getLastRecord() throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM MARKS ORDER BY ID DESC LIMIT 1;");
            resultSet = getLast.executeQuery();
            resultSet.next();
            long id = resultSet.getLong("ID");
            long learningSubjectId = resultSet.getLong("LEARNING_SUBJECT_ID");
            int mark = resultSet.getInt("MARK");
            Date date = resultSet.getDate("DATE_OF_RECEIVING");
            MarkDTO markDto = new MarkDTO(id, learningSubjectId, mark, date);
            getLast.executeQuery();
            return markDto;
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

    private List<MarkDTO> getTable(String tableTitle, String type) throws DAOException {

        PreparedStatement getLast = null;
        ResultSet resultSet = null;
        try {
            conn = getConnection();
            getLast = conn.prepareStatement("SELECT * FROM " + tableTitle + ";");
            resultSet = getLast.executeQuery();
            List<MarkDTO> markList = new ArrayList<>();
            if (tableTitle.equals("MARKS")) {
                while (resultSet.next()) {
                    MarkDTO mark = new MarkDTO(resultSet.getLong("ID"),
                            resultSet.getLong("LEARNING_SUBJECT_ID"), resultSet.getInt("MARK"),
                            resultSet.getDate("DATE_OF_RECEIVING"));
                    markList.add(mark);
                }
            } else {
                if (type.equals("student")) {
                    while (resultSet.next()) {
                        MarkDTO mark = new MarkDTO(resultSet.getLong("ID"),
                                resultSet.getString("NAME"), resultSet.getInt("MARK"),
                                resultSet.getDate("DATE_OF_RECEIVING"));
                        markList.add(mark);
                    }
                }
                if (type.equals("subject")) {
                    while (resultSet.next()) {
                        MarkDTO mark = new MarkDTO(resultSet.getLong("ID"),
                                resultSet.getString("FIRST_NAME"), resultSet.getString("LAST_NAME"),
                                resultSet.getInt("MARK"), resultSet.getDate("DATE_OF_RECEIVING"));
                        markList.add(mark);
                    }
                }
                if (type.equals("period")) {
                    while (resultSet.next()) {
                        MarkDTO mark = new MarkDTO(resultSet.getLong("ID"),
                                resultSet.getInt("MARK"), resultSet.getDate("DATE_OF_RECEIVING"));
                        markList.add(mark);
                    }
                }
            }
            return markList;
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