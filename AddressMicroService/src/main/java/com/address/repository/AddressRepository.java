package com.address.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.address.entity.Address;


@Repository
public interface AddressRepository extends JpaRepository<Address, Integer> {

}
