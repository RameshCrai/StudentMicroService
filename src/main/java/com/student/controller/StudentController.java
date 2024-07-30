package com.student.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.student.entity.Student;
import com.student.exceptions.ApiExceptionHandler;
import com.student.exceptions.UserNotFoundException;
import com.student.payloads.StudentDto;
import com.student.service.impl.StudentServiceImpl;

@RestController
public class StudentController {
	@Autowired
	private StudentServiceImpl serviceimpl;

	@PostMapping("/create-student")
	public ResponseEntity<?> saveStudent(@RequestBody StudentDto student) throws ApiExceptionHandler {
		try {
			StudentDto studentObj = this.serviceimpl.createStudent(student);
			return ResponseEntity.status(HttpStatus.OK).body(studentObj);
		} catch (ApiExceptionHandler e) {
			e.printStackTrace();
			ApiExceptionHandler apiExceptionHandler = new ApiExceptionHandler("This does not exist");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiExceptionHandler);
		}
	}

//	public CompletableFuture<ResponseEntity<StudentDto>> createStudentObj(@RequestBody Student std){
//		return CompletableFuture.supplyAsync(()->{
//			StudentDto stdObj = this.serviceimpl.createStudent(std);
//			
//			return ResponseEntity.status(HttpStatus.OK).body(stdObj);
//		});
//	}

//	@GetMapping("/get-student")
//	public ResponseEntity<StudentDto> getStudent(@PathVariable("id") int id){
//		StudentDto studentObj = this.serviceimpl.getStudentByID(id);
//		return ResponseEntity.status(HttpStatus.OK).body(studentObj);
//	}

	@GetMapping("/get-student/{id}")
	public CompletableFuture<ResponseEntity<?>> getStudentData(@PathVariable("id") int id) {
		return CompletableFuture.supplyAsync(() -> {
			try {
				StudentDto studentObj = this.serviceimpl.getStudentByID(id);
				if (studentObj.getStudentID() == id) {
					return ResponseEntity.status(HttpStatus.OK).body(studentObj);
				} else {
					String emessage = "This data not Exit in Real";
					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiExceptionHandler(emessage));
				}
			} catch ( UserNotFoundException ex) {
				ex.printStackTrace();
				String emessage = "This data not Exit in Real";
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiExceptionHandler(emessage));
			}
		});
	}

}
