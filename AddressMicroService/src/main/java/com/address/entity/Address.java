package com.address.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name="address")
public class Address {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="address_id")
	private int addressID;
	@Column(name="country")
	private String country;
	@Column(name="zip_code")
	private String zipCode;
	@Column(name="state")
	private String state;
	@Column(name="street")
	private String street;
	@Column(name="city")
	private String city;
	@Column(name="student_id")
	private String studentID;

}
