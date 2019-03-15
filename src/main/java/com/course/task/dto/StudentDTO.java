package com.course.task.dto;

import javax.persistence.*;

@Entity
@Table(name = "STUDENTS")
public class StudentDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;
	
	public long getId() {
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public StudentDTO(){}
	
	public StudentDTO(long id, String firstName, String lastName){
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public StudentDTO(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public StudentDTO(long id){
		this.id = id;
	}
	
}