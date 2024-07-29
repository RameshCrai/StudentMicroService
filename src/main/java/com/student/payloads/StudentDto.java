package com.student.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

	private int studentID;
	private String fullName;
	private String email;
	private String mobile;
	private AddressDto addressDto;
	private CourseDto courseDto;

}
