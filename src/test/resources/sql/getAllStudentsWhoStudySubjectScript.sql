use grades_list_tests;
CREATE TABLE TEST SELECT STUDENTS.ID, STUDENTS.FIRST_NAME, STUDENTS.LAST_NAME FROM LEARNING_SUBJECTS, STUDENTS WHERE LEARNING_SUBJECTS.SUBJECT_ID = 2 AND LEARNING_SUBJECTS.STUDENT_ID = STUDENTS.ID;
SELECT * FROM TEST;