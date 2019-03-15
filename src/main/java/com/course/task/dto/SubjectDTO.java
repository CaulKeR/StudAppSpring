package com.course.task.dto;

import javax.persistence.*;

@Entity
@Table(name = "SUBJECTS")
public class SubjectDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "NAME")
	private String name;
	
	public long getId() {
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name){
		this.name = name;
	}
	
	public SubjectDTO (){}
	
	public SubjectDTO (long id, String name){
		this.id = id;
		this.name = name;
	}

	public SubjectDTO (String name){
		this.name = name;
	}
	
}