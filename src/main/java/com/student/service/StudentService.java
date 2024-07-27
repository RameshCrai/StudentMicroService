package com.student.service;

import com.student.entity.Student;
import com.student.payloads.StudentDto;

public interface StudentService {
	
	StudentDto createStudent(Student student);
	
	StudentDto getStudentByID(int id);

}
