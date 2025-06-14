package com.address.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.address.entity.Address;
import com.address.payloads.AddressDto;
import com.address.services.Impl.AddressServiceImpl;

@RestController
public class AddressController {

	@Autowired
	private AddressServiceImpl addressImpl;

	@PostMapping("/save-address")
	public CompletableFuture<ResponseEntity<AddressDto>> saveAddress(@RequestBody Address address) {
		return CompletableFuture.supplyAsync(() -> {
			AddressDto addressObj = this.addressImpl.createAddress(address);
			return ResponseEntity.status(HttpStatus.OK).body(addressObj);
		});
	}

	@GetMapping("/get-address/{id}")
	public CompletableFuture<ResponseEntity<AddressDto>> getAddress(@PathVariable("id") int id) {
		return CompletableFuture.supplyAsync(() -> {
			AddressDto addressData = this.addressImpl.getAddressByUserID(id);
			return ResponseEntity.status(HttpStatus.OK).body(addressData);
		});
	}

}
