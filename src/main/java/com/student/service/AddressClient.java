package com.student.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.student.payloads.AddressDto;

//http://localhost:9092/address-app/api/get-address/1
@FeignClient(name="Address",url = "http://localhost:9092",path="/address-app/api")
public interface AddressClient {
	@GetMapping("/get-address/{id}")
	AddressDto getAddressByStudentId(@PathVariable("id") int id);

}
