package com.course.task.logic; 

public class MarkDTO {
	
	private long id;
	private long studentId;
	private long subjectId;
	private int mark;
	private String date;
	private String name;
	private String firstName;
	private String lastName;
	private long learningSubjectId;

	public long getId() {
		return id;
	}
	public void setId(long id){
		this.id = id;
	}
	public long getStudentId() {
		return studentId;
	}
	public void setStudentId(long studentId){
		this.studentId = studentId;
	}
	public long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(long subjectId){
		this.subjectId = subjectId;
	}
	public int getMark() {
		return mark;
	}
	public void setMark(int mark){
		this.mark = mark;
	}
	public String getDate(){
		return date;
	}
	public void setDateInYYYYMMDD(String date){
		this.date = date;
	}
	public String getName(){
		return name;
	}
	public void setName(String name){
		this.name = name;
	}
	public String getFirstName(){
		return firstName;
	}
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	public String getLastName(){
		return lastName;
	}
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	public long getLearningSubjectId(){
		return learningSubjectId;
	}
	public void setLearningSubjectId(long learningSubjectId){
		this.learningSubjectId = learningSubjectId;
	}

	public MarkDTO () throws IllegalArgumentException{
	}

	public MarkDTO (long learningSubjectId, int mark, String date) throws IllegalArgumentException{
		this.learningSubjectId = learningSubjectId;
		this.mark = mark;
		if(date.charAt(4) == '-' &&  date.charAt(7) == '-') {
			setDateInYYYYMMDD(date);
		} else {
			throw new IllegalArgumentException("Incorrect date format! Use only YYYY-MM-DD");
		}
	}
	
	public MarkDTO (long id, String name, int mark, String date) throws IllegalArgumentException{
		this.id = id;
		this.name = name;
		this.mark = mark;
		if(date.charAt(4) == '-' &&  date.charAt(7) == '-') {
			setDateInYYYYMMDD(date);
		} else {
			throw new IllegalArgumentException("Incorrect date format! Use only YYYY-MM-DD");
		}
	}

	public MarkDTO (long id, long learningSubjectId, int mark, String date) throws IllegalArgumentException{
		this.id = id;
		this.learningSubjectId = learningSubjectId;
		this.mark = mark;
		if(date.charAt(4) == '-' &&  date.charAt(7) == '-') {
			setDateInYYYYMMDD(date);
		} else {
			throw new IllegalArgumentException("Incorrect date format! Use only YYYY-MM-DD");
		}
	}
	
	public MarkDTO (long id, String firstName, String lastName, int mark, String date) throws IllegalArgumentException{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.mark = mark;
		if(date.charAt(4) == '-' &&  date.charAt(7) == '-') {
			setDateInYYYYMMDD(date);
		} else {
			throw new IllegalArgumentException("Incorrect date format! Use only YYYY-MM-DD");
		}
	}

	public MarkDTO (long id) throws IllegalArgumentException{
		this.id = id;
	}

}