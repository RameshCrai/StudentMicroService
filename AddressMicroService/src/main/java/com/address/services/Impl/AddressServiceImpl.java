package com.address.services.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.address.entity.Address;
import com.address.payloads.AddressDto;
import com.address.repository.AddressRepository;
import com.address.services.AddressService;

@Component
public class AddressServiceImpl implements AddressService{
	@Autowired
	private AddressRepository addressRepo;
	
	@Autowired
	private ModelMapper modelMopper;

	@Override
	public AddressDto createAddress(Address address) {		
		AddressDto addressObj = this.modelMopper.map(this.addressRepo.save(address), AddressDto.class);
		
		return addressObj;
	}

	@Override
	public AddressDto getAddressByUserID(int id) {
		AddressDto addressData = this.modelMopper.map(this.addressRepo.findById(id), AddressDto.class);
		return addressData;
	}

}
