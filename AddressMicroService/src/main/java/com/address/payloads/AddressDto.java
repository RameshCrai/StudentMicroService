package com.address.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddressDto {
	private int addressID;
	private String country;
	private String zipCode;
	private String state;
	private String street;
	private String city;
	private String studentID;

}
