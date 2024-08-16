package com.student.controller;

import java.util.concurrent.CompletableFuture;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@RestController
public class StudentController {
	private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
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

	@GetMapping("/get-student/{id}")
	@CircuitBreaker(name = "courseAddressBreaker", fallbackMethod = "courseAddressFallback")
	public ResponseEntity<?> getStudent(@PathVariable("id") int id) {
	    try {
	        StudentDto studentObj = this.serviceimpl.getStudentByID(id);
	        if (studentObj.getStudentID() == id) {
	            return ResponseEntity.status(HttpStatus.OK).body(studentObj);
	        } else {
	            String emessage = "This data does not exist";
	            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiExceptionHandler(emessage));
	        }
	    } catch (UserNotFoundException ex) {
	        ex.printStackTrace();
	        String emessage = "An error occurred while retrieving the data";
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiExceptionHandler(emessage));
	    }
	}

	// Fallback method for Circuit Breaker
	public ResponseEntity<StudentDto> courseAddressFallback(int id, Throwable ex) {
	    logger.info("Fallback is executed because the service is down: " + ex.getMessage());

	    StudentDto studentDto = StudentDto.builder()
	                                     .email("dummy@gmail.com")
	                                     .fullName("Dummy K")
	                                     .build();

	    return new ResponseEntity<>(studentDto, HttpStatus.OK);
	}


//	@GetMapping("/get-student/{id}")
//	@CircuitBreaker(name = "courseAddressBreaker", fallbackMethod = "courseAddressFallback")
//	public CompletableFuture<ResponseEntity<?>> getStudentData(@PathVariable("id") int id) {
//		return CompletableFuture.supplyAsync(() -> {
//			try {
//				StudentDto studentObj = this.serviceimpl.getStudentByID(id);
//				if (studentObj.getStudentID() == id) {
//					return ResponseEntity.status(HttpStatus.OK).body(studentObj);
//				} else {
//					String emessage = "This data not Exit in Real";
//					return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiExceptionHandler(emessage));
//				}
//			} catch (UserNotFoundException ex) {
//				ex.printStackTrace();
//				String emessage = "This data not Exit in Real";
//				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiExceptionHandler(emessage));
//			}
//		});
//	}

}
