package com.student.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.student.payloads.CourseDto;



@FeignClient(name="Course",url = "http://localhost:8085",path = "/course-app/api")
public interface CourseClient { //proxy class
	@GetMapping("/get-course/{id}")
	public CourseDto getCourseByStudentId(@PathVariable("id") int id);
	
	@GetMapping("/getAll-course")
	public ResponseEntity<List<CourseDto>> getAllCourses();
	
}
