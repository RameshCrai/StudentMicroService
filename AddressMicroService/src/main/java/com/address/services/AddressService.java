package com.address.services;

import com.address.entity.Address;
import com.address.payloads.AddressDto;

public interface AddressService {
	
	AddressDto createAddress(Address address);
	
	AddressDto getAddressByUserID(int id);

}
