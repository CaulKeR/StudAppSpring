package com.course.task.logic;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "MARKS")
public class MarkDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "LEARNING_SUBJECT_ID")
	private long learningSubjectId;

	@Column(name = "MARK")
	private int mark;

	@Column(name = "DATE_OF_RECEIVING")
	private Date date;

	@Transient
	private String name;

	@Transient
    private String firstName;

	@Transient
	private String lastName;

	public long getId() {
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getLearningSubjectId(){
		return learningSubjectId;
	}

	public void setLearningSubjectId(long learningSubjectId){
		this.learningSubjectId = learningSubjectId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark){
		this.mark = mark;
	}

	public Date getDate(){
		return date;
	}

	public void setDateInYYYYMMDD(Date date){
		this.date = date;
	}

	public MarkDTO () throws IllegalArgumentException{
	}

	public MarkDTO (long learningSubjectId, int mark, Date date) throws IllegalArgumentException{
		this.learningSubjectId = learningSubjectId;
		this.mark = mark;
		setDateInYYYYMMDD(date);
	}
	
	public MarkDTO (long id, String name, int mark, Date date) throws IllegalArgumentException{
		this.id = id;
		this.name = name;
		this.mark = mark;
		setDateInYYYYMMDD(date);
	}

	public MarkDTO (long id, long learningSubjectId, int mark, Date date) throws IllegalArgumentException{
		this.id = id;
		this.learningSubjectId = learningSubjectId;
		this.mark = mark;
		setDateInYYYYMMDD(date);
	}
	
	public MarkDTO (long id, String firstName, String lastName, int mark, Date date) throws IllegalArgumentException{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mark = mark;
		setDateInYYYYMMDD(date);
	}

	public MarkDTO (long id) throws IllegalArgumentException{
		this.id = id;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}