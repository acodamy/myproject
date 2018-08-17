package com.springbootrest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.springbootrest.entities.Student;
import com.springbootrest.repositories.StudentRepository;

//create student service to insert,delete,update,retrieve data into student table
@Service
@Component
public class StudentService {
	/*
	 * @Autowired Marks a constructor or field or setter method as to be autowired
	 * by Spring's dependency injection facilities.
	 */
	// @Qualifier("studentRepository")
	@Autowired
	private StudentRepository studentRepository;
	
	
	public void setStudentRepository(StudentRepository studentRepository) {
		this.studentRepository=studentRepository;
	}

	// to insert and save student data into student table
	public Student insertStudent(Student student) {
		return studentRepository.save(student);
	}

	// to get student data from student table
	public List<Student> getStudentData() {
		return (List<Student>) studentRepository.findAll();
	}
	// get student data by using studentId

	public Optional<Student> findStudentByid(Long id) {
		return studentRepository.findById(id);
	}

	// to delete student data from student table
	public void deleteStudent(Long id) {
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			Student student1 = student.get();
			studentRepository.delete(student1);
		}
	}

	public Student updateStudent(Long id,  Student stDetails) {
		// TODO Auto-generated method stub
		Optional<Student> student = studentRepository.findById(id);
		if (student.isPresent()) {
			Student student1 = student.get();
			student1.setStudentName(stDetails.getStudentName());
			student1.setAddress(stDetails.getAddress());
			studentRepository.save(student1);
		}
		return stDetails;

	}
}
