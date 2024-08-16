package com.student.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.student.entity.Student;
import com.student.payloads.AddressDto;
import com.student.payloads.CourseDto;
import com.student.payloads.StudentDto;
import com.student.repository.StudentRepository;
import com.student.service.AddressClient;
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
//	@Autowired
//	private WebClient webClient;

//	for feign client
	@Autowired
	private CourseClient courseClient;

	@Autowired
	private AddressClient addressClient;

//	@Autowired
//	private DiscoveryClient discoveryClient;

//	LoadBalancer for RestTemplate
//	@Autowired
//	private LoadBalancerClient loadBalancerClient;

	@Override
	public StudentDto createStudent(StudentDto studentDto) {
		ResponseEntity<AddressDto> addressResponse = this.addressClient.saveAddress(studentDto.getAddressDto());
		AddressDto addressDto = addressResponse.getBody();
		ResponseEntity<CourseDto> courseResponse = this.courseClient.saveCourse(studentDto.getCourseDto());
		CourseDto courseDto = courseResponse.getBody();

		Student student = new Student();
		student.setFullName(studentDto.getFullName());
		student.setEmail(studentDto.getEmail());
		student.setMobile(studentDto.getMobile());

		Student savedStudent = studentRepo.save(student);

		StudentDto savedStudentDto = modelMapper.map(savedStudent, StudentDto.class);
		savedStudentDto.setAddressDto(addressDto);
		savedStudentDto.setCourseDto(courseDto);

		return savedStudentDto;
	}

	@Override
	public StudentDto getStudentByID(int id) {

		StudentDto studentObj = this.modelMapper.map(this.studentRepo.findById(id), StudentDto.class);
//		Set data by making a rest APi call
//		For RestTemplate
//		AddressDto addressDto = callingAddressServiceUsingRESTTemplate(id);
//		For WebClient from spring reactive web
//		AddressDto addressDto = callingAddressServiceUsingWebClient(id)

//		For FeignClient
		ResponseEntity<CourseDto> courseResponse = courseClient.getCourseByStudentId(id);
		CourseDto courseDto = courseResponse.getBody();

		ResponseEntity<AddressDto> addressRespone = addressClient.getAddressByStudentId(id);
		AddressDto addressDto = addressRespone.getBody();
		studentObj.setAddressDto(addressDto);
		studentObj.setCourseDto(courseDto);

		System.out.println("Service pass " + 1);

		return studentObj;
	}

//	WebClient Approach by Reactive web 
//	public AddressDto callingAddressServiceUsingWebClient(int id) {
//  get me the details for the IP and port number for address service 
//	discoveryClient.get
//		return webClient.get().uri("/get-address/" + id).retrieve().bodyToMono(AddressDto.class).block();
//	}

//	RestTemplate Approach
//	private AddressDto callingAddressServiceUsingRESTTemplate(int id) {
//  get me the details for the IP and port number for address service 
//		List<ServiceInstance> instances = discovery.getInstances("address-service");
//		ServiceInstance serviceInstance =instances.get(0);
//		String uri = serviceInstance.getUri().toString()

//		for LoadBalance
//		ServiceInstance serviceInstance = loadBalancerClient.choose("address-service");
//		String uri = serviceInstance.getUri().toString();
//		String contextRoot = serviceInstance.getMetadata().get("configPath");
//		
//		System.out.println("This is uri == "+uri);
//		System.out.println("Meta data  == "+serviceInstance.getMetadata().get("user"));
//		
//		return restTemplate.getForObject(uri+contextRoot+"//get-address/{id}", AddressDto.class, id);
//	}

}
