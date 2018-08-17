package com.springbootrest.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/*@Entity annotation to make class as Entity class
@Table(name="student") to create a table with specified name(student)*/
@Entity
@Table(name = "student")
@EntityListeners(AuditingEntityListener.class)
public class Student {
	/*
	 * @Id annotation to make id column as primarykey and
	 * @GeneratedValue(strategy = GenerationType.AUTO) to auto increament id value
	 * when ever new record inserted,@column to make properties as column in DB
	 * and @NotBlank is make column as not tobe blank
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long studentId;

	@Column
	@NotBlank
	private String studentName;
	@Column
	@NotBlank
	private String address;

	public long getStudentId() {
		return studentId;
	}

	public void setStudentId(long studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
