package com.student.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.student.payloads.AddressDto;

@FeignClient(name = "ADDRESS-SERVICE", path = "/address-app/api")
public interface AddressClient {
	@PostMapping("/save-address")
	ResponseEntity<AddressDto> saveAddress(AddressDto address);
	
	
	@GetMapping("/get-address/{id}")
	ResponseEntity<AddressDto> getAddressByStudentId(@PathVariable("id") int id);

}
