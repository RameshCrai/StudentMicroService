package com.student.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.student.payloads.CourseDto;

//@FeignClient(name="Course",url = "http://localhost:8085",path = "/course-app/api")
@FeignClient(name = "COURSE-SERVICE", path = "/course-app/api")
public interface CourseClient {
	@PostMapping("/save-address")
	ResponseEntity<CourseDto> saveCourse(CourseDto course);
	
	@GetMapping("/get-course/{id}")
	ResponseEntity<CourseDto> getCourseByStudentId(@PathVariable("id") int id);
}
