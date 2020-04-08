package com.people.equifax.college.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com 
 * version 1.0
 */

@Entity
@Table(name = "course")
public class Course implements Serializable {
	
	private static final long serialVersionUID = 7300857162967077521L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name")
	private String nameCourse;

	@Column(name = "code")
	private String codeCourse;
	
	@OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
	@JsonIgnore
	private Set<Student> students;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return nameCourse;
	}

	public void setName(String name) {
		this.nameCourse = name;
	}

	public String getCode() {
		return codeCourse;
	}

	public void setCode(String code) {
		this.codeCourse = code;
	}

	public Set<Student> getStudents() {
		return students;
	}
	
	public void setStudents(Set<Student> students) {
		this.students = students;
	}
}
