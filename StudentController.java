package com.springbootrest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springbootrest.entities.Student;
import com.springbootrest.service.StudentService;

/*It includes the @Controller and @ResponseBody annotations 
 * used to simplifies the controller implementation
 */
@RestController
@RequestMapping("/students")
// @ComponentScan("com.springbootrest.service.StudentService")
public class StudentController {

	/*
	 * @Autowired Marks a constructor or field or setter method as to be autowired
	 * by Spring's dependency injection facilities.
	 */
	@Autowired
	StudentService studentService;
	
	public void setStudentService(StudentService studentService) {
		this.studentService=studentService;
	}

	// @POSTMapping to insert and save data into database
	// @RequestBody Annotation indicating a method parameter should be bound to the
	// body of the web request
	@PostMapping("/studentsdata")
	public Student createStudent(@Valid @RequestBody Student student) {
		return studentService.insertStudent(student);
	}

	/*
	 * get student data
	 * 
	 * @GetMapping Annotation for mapping HTTP GET requests onto specific handler
	 * methods. Specifically, @GetMapping is a composed annotation that acts as a
	 * shortcut for @RequestMapping(method = RequestMethod.GET).
	 */
	@GetMapping("/student")
	public List<Student> getStudents() {
		return studentService.getStudentData();
	}

	// get student by id
	@GetMapping("/student/{id}")
	public ResponseEntity<Optional<Student>> getStudentById(@PathVariable(value = "id") Long stId) {
		Optional<Student> student = studentService.findStudentByid(stId);
		if (student == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(student);
	}

	/*
	 * update student by id using @PutMapping Annotation for mapping HTTP PUT
	 * requests onto specific handler methods. Specifically, it is a composed
	 * annotation that acts as a shortcut for @RequestMapping(method =
	 * RequestMethod.PUT).
	 */

	@PutMapping("/student/{id}")
	public ResponseEntity<Student> updateStudent(@PathVariable(value = "id") Long stId,
			@Valid @RequestBody Student stDetails) {
		Student updatedStudent = studentService.updateStudent(stId, stDetails);
		return ResponseEntity.ok().body(updatedStudent);
	}

	/*
	 * delete student data by id using
	 * 
	 * @DeleteMapping Annotation for mapping HTTP DELETE requests onto specific
	 * handler methods.Specifically, it is a composed annotation that acts as a
	 * shortcut for
	 * 
	 * @RequestMapping(method = RequestMethod.DELETE).
	 * 
	 * 
	 */
	@DeleteMapping("/student/{id}")
	public ResponseEntity<Student> deleteStudent(@PathVariable(value = "id") Long stId,
			@Valid @RequestBody Student stDetails) {
		studentService.deleteStudent(stId);
		return ResponseEntity.ok().build();
	}

}
