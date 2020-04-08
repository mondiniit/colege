/**
 * 
 */
package com.people.equifax.college.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.people.equifax.college.dto.StudentDTO;
import com.people.equifax.college.exception.GenericException;
import com.people.equifax.college.model.Student;
import com.people.equifax.college.service.StudentService;

/**
 * @author Rodolfo.Quiroz 
 * rquiroz1988@gmail.com
 * version 1.0
 */
@RestController
@RequestMapping("/api")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@GetMapping(value = "GET/students", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Student>> getStudentsPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
		@RequestParam(defaultValue = "3") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) throws GenericException {
		List<Student> students = new ArrayList<>();
		ResponseEntity<List<Student>> response;
		try {
			students = studentService.getAllStudentPaginated(pageNo, pageSize, sortBy);
			response = new ResponseEntity<>(students, new HttpHeaders(), HttpStatus.OK);
		} catch (Exception e) {
			response = new ResponseEntity<List<Student>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response; 

	}
	
	@GetMapping(value = "GET/students/all", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getStudents() {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(studentService.getAllStudents(), HttpStatus.OK);
		} catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
	}
	
	@GetMapping(value = "GET/students/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public  @ResponseBody ResponseEntity<Object> getStudentById(@PathVariable long id) throws GenericException {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(studentService.getStudent(id), HttpStatus.OK);
		} catch (GenericException error) {
			response = new ResponseEntity<>(error.getMessage(), error.getHttpStatus());
		} catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
	}
	
	@PostMapping(value = "POST/students", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> addStudent(@RequestBody StudentDTO studentDto) throws GenericException {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(studentService.addNewStudent(studentDto), HttpStatus.CREATED);
		}  catch (GenericException error) {
			response = new ResponseEntity<>(error.getMessage(), error.getHttpStatus());
		} catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
	}
	
	@PutMapping(value = "PUT/students/{studentId}", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> updateStudent(@PathVariable("studentId") Long studentId,
			@RequestBody StudentDTO studentDto) throws GenericException {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(studentService.updateStudent(studentId, studentDto), HttpStatus.OK);			
		} catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
	}
	
	@DeleteMapping(value = "DELETE/student/{id}", consumes = MediaType.APPLICATION_JSON_VALUE , 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> deleteStudent(@PathVariable("id") long id) throws GenericException {
		ResponseEntity<Object> response;
		try {
			response = new ResponseEntity<>(studentService.deleteStudent(id), HttpStatus.OK);			
		} catch (Exception e) {
            response = new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
	}

}
