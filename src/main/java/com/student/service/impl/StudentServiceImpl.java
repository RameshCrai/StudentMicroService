package com.student.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import com.student.entity.Student;
import com.student.payloads.AddressDto;
import com.student.payloads.CourseDto;
import com.student.payloads.StudentDto;
import com.student.repository.StudentRepository;
import com.student.service.CourseClient;
import com.student.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService {
	@Autowired
	private StudentRepository studentRepo;

	@Autowired
	private ModelMapper modelMapper;

//	Blocking
//	those are used for RestTemplate
//	@Autowired
//	private RestTemplate restTemplate;
//	@Value("${addressservice.base.url}")
//	private String addressBaseURL;	
//	first of all call bean (constructor) that will initialize first then call the property like addressURI 
//	public StudentServiceImpl(@Value("${addressservice.base.url}") String addressBaseURL, RestTemplateBuilder builder) {
//		System.out.println("rest uri " + addressBaseURL);
//		this.restTemplate = builder.rootUri(addressBaseURL).build();
//	}

//	Non-Blocking
	@Autowired
	private WebClient webClient;

//	for feign client
	@Autowired
	private CourseClient courseClient;

	@Override
	public StudentDto createStudent(Student student) {
		Student studentObj = this.studentRepo.save(student);
		StudentDto studentData = modelMapper.map(studentObj, StudentDto.class);

		return studentData;
	}

	@Override
	public StudentDto getStudentByID(int id) {

		StudentDto studentObj = this.modelMapper.map(this.studentRepo.findById(id), StudentDto.class);
//		Set data by making a rest APi call
//		AddressDto addressDto = this.restTemplate.getForObject("/get-address/{id}", AddressDto.class, id);
		AddressDto addressDto = webClient.get().uri("/get-address/" + id).retrieve().bodyToMono(AddressDto.class)
				.block();
		CourseDto courseDto = courseClient.getCourseByStudentId(id);

		studentObj.setAddressDto(addressDto);
		studentObj.setCourseDto(courseDto);

		return studentObj;
	}

//	private AddressDto callingAddressServiceUsingRESTTemplate(int id) {
//		return restTemplate.getForObject("/get-address/{id}", AddressDto.class, id);
//	}

}
