package com.student.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.student.entities.Address;
import com.student.entities.Course;
import com.student.entities.Guide;
import com.student.entities.Student;
import com.student.service.CourseService;
import com.student.service.FileUploadService;
import com.student.service.GuideService;
import com.student.service.StudentService;

/*It includes the @Controller and @ResponseBody annotations 
 * used to simplifies the controller implementation
 */
@RestController
@RequestMapping("/students")
public class StudentController {

	/*
	 * @Autowired Marks a constructor or field or setter method as to be autowired
	 * by Spring's dependency injection facilities.
	 */
	@Autowired
	StudentService studentService;

	@Autowired
	GuideService guideService;

	@Autowired
	CourseService courseService;
	@Autowired
	FileUploadService fileUploadService;

	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("file") MultipartFile file) {

		return fileUploadService.uploadFile(file);
	}

	@PostMapping("/course")
	public Course addCourse(@Valid @RequestBody Course course) {
		return courseService.saveCourse(course);
	}

	@PostMapping("/guide")
	public Guide addGuide(@Valid @RequestBody Guide guide) {
		return guideService.saveGuideData(guide);
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	// @POSTMapping to insert and save data into database
	// @RequestBody Annotation indicating a method parameter should be bound to the
	// body of the web request

	@PostMapping("/address")
	public Address addAddress(@Valid @RequestBody Address address) {
		return studentService.addAddress(address);
	}

	@PostMapping("/studentsdata")
	public Student createStudent(@Valid @RequestBody Student student) {
		return studentService.addStudent(student);
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
		return studentService.getAllStudentData();
	}

	// get student by id
	@GetMapping("/student/{id}")
	public ResponseEntity<List<Student>> getStudentById(@PathVariable(value = "id") Long stId) {
		List<Student> student = studentService.findStudentByid(stId);
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
		Student updatedStudent = studentService.updateStudentById(stId, stDetails);
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
		studentService.deleteStudentDataById(stId);
		return ResponseEntity.ok().build();
	}

}
