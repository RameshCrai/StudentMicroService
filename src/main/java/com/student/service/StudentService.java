package com.student.service;

import com.student.payloads.StudentDto;

public interface StudentService {

	StudentDto createStudent(StudentDto student);

	StudentDto getStudentByID(int id);

}
