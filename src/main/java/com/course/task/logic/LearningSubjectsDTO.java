package com.course.task.logic;

import javax.persistence.*;

@Entity
@Table(name = "LEARNING_SUBJECTS")
public class LearningSubjectsDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "STUDENT_ID")
    private long studentId;

    @Column(name = "SUBJECT_ID")
    private long subjectId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }

    public long getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(long subjectId) {
        this.subjectId = subjectId;
    }

    public LearningSubjectsDTO(long studentId, long subjectId) {
        this.studentId = studentId;
        this.subjectId = subjectId;
    }

}
